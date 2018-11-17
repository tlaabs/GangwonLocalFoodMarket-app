package com.glfm.ganwonlocalfoodmarket.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.glfm.ganwonlocalfoodmarket.Object.OrderVO;
import com.glfm.ganwonlocalfoodmarket.Object.OnlyIdDTO;
import com.glfm.ganwonlocalfoodmarket.Object.ProductItem;
import com.glfm.ganwonlocalfoodmarket.Object.UserVO;
import com.glfm.ganwonlocalfoodmarket.R;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroCallback;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroClient;
import com.glfm.ganwonlocalfoodmarket.Util.UserLoginSession;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BuyActivity extends AppCompatActivity {

    final static String LOG = "BuyActivity";

    private ProductItem item;

    @BindView(R.id.product_name)
    TextView productNameView;
    @BindView(R.id.product_quantity)
    TextView productQuantitiyView;
    @BindView(R.id.phone)
    EditText phoneView;
    @BindView(R.id.email)
    EditText emailView;
    @BindView(R.id.address)
    EditText addressView;
    @BindView(R.id.card)
    TextView cardView;
    @BindView(R.id.total)
    TextView totalView;
    @BindView(R.id.submit)
    Button submitView;

    private RetroClient retroClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        ButterKnife.bind(this);
        retroClient = RetroClient.getInstance(this).createBaseApi();

        init();
        initView();

        loadUserData();
    }

    public void init() {
        Intent i = getIntent();
        item = (ProductItem) i.getSerializableExtra("item");
    }

    public void initView() {
        productNameView.setText("품목 : " + item.getName());
        productQuantitiyView.setText("수량 : " + item.getQuantity());

        int quantity_int = Integer.parseInt(item.getQuantity());
        int price_int = Integer.parseInt(item.getPrice());
        totalView.setText("총 금액 : " + quantity_int * price_int + "원");
    }

    private void loadUserData(){
        OnlyIdDTO dto = new OnlyIdDTO();
        dto.setId(UserLoginSession.getSession().getUser());
        retroClient.getProfile(dto, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(LOG, t.toString());
            }

            @Override
            public void onSuccess(int code, Object receivedData){
                UserVO gUser = ((UserVO) receivedData);
                addressView.setText(gUser.getAddress());
                phoneView.setText(gUser.getPhone());
                emailView.setText(gUser.getEmail());
                addressView.setText(gUser.getAddress());
                cardView.setText(gUser.getCard());
            }

            @Override
            public void onFailure(int code) {
                Log.d(LOG, "실패");
            }
        });
    }

    @OnClick(R.id.submit)
    public void submit() {
        OrderVO orderItem = new OrderVO();
        orderItem.setUser_id(UserLoginSession.getSession().getUser());
        orderItem.setProduct_id(item.getId());
        orderItem.setProduct_name(item.getName());
        orderItem.setQuantity(item.getQuantity());
        orderItem.setEmail(emailView.getText().toString());
        orderItem.setPhone(phoneView.getText().toString());
        orderItem.setAddress(addressView.getText().toString());
        orderItem.setCard(cardView.getText().toString());
        int quantity_int = Integer.parseInt(item.getQuantity());
        int price_int = Integer.parseInt(item.getPrice());
        orderItem.setTotal(quantity_int * price_int + "");

        retroClient.order(orderItem, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(LOG, t.toString());
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Toast.makeText(getApplicationContext(),"주문 완료!",Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(int code) {
                Log.d(LOG, "실패");
            }
        });
    }
}
