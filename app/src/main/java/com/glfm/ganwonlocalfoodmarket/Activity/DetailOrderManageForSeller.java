package com.glfm.ganwonlocalfoodmarket.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.glfm.ganwonlocalfoodmarket.Object.OrderVO;
import com.glfm.ganwonlocalfoodmarket.Object.ProductItem;
import com.glfm.ganwonlocalfoodmarket.R;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroCallback;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroClient;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.HashMap;

public class DetailOrderManageForSeller extends AppCompatActivity {
    final static String LOG = "DetailOrderManageFor";
    private OrderVO item;
    public TextView orderIdView;
    public TextView productNameView;
    public TextView quantityView;
    public TextView totalView;
    public TextView phoneView;
    public TextView emailView;
    public TextView addressView;
    public Button submitBtn;

    private MaterialSpinner spinner;

    private RetroClient retroClient;

    private String state = "배송 준비 중"; //"배송 준비 중", "배송 중", "배송 완료"   *

    private HashMap<String, Integer> stateMap = new HashMap<String, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order_manage_for_seller);
        retroClient = RetroClient.getInstance(this).createBaseApi();

        stateMap.put("배송 준비 중",0);
        stateMap.put("배송 중",1);
        stateMap.put("배송 완료",2);

        init();
    }

    private void updateState(){
        retroClient.updateState(item.getOrder_id(),state, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(LOG, t.toString());
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Log.d(LOG, "성공");
                finish();
            }

            @Override
            public void onFailure(int code) {
                Log.d(LOG, "실패");
            }
        });
    }


    private void init(){
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        item = (OrderVO)bundle.get("item");

        orderIdView = findViewById(R.id.order_id);
        productNameView= findViewById(R.id.product_name);
        quantityView= findViewById(R.id.quantity);
        totalView= findViewById(R.id.total);
        phoneView= findViewById(R.id.phone);
        emailView= findViewById(R.id.email);
        addressView= findViewById(R.id.address);
        submitBtn= findViewById(R.id.submit);

        spinner = findViewById(R.id.spinner);

        orderIdView.setText(item.getUser_id());
        productNameView.setText(item.getProduct_name());
        quantityView.setText(item.getQuantity());
        totalView.setText(item.getTotal());
        phoneView.setText(item.getPhone());
        emailView.setText(item.getEmail());
        addressView.setText(item.getAddress());

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateState();
            }
        });

        state = item.getState();
        int stateIdx = stateMap.get(state).intValue();
        spinner.setItems("배송 준비 중", "배송 중", "배송 완료");
        spinner.setSelectedIndex(stateIdx);
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                state = item.toString();
            }

        });
    }
}
