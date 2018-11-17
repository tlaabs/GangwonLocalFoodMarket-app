package com.glfm.ganwonlocalfoodmarket.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.glfm.ganwonlocalfoodmarket.Adapter.FarmAdapter;
import com.glfm.ganwonlocalfoodmarket.Adapter.ProductAdapter;
import com.glfm.ganwonlocalfoodmarket.Object.FarmItem;
import com.glfm.ganwonlocalfoodmarket.Object.ProductItem;
import com.glfm.ganwonlocalfoodmarket.R;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroCallback;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroClient;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailMarketActivity extends AppCompatActivity {

    final static String LOG = "DetailMarketActivity";
    private Context mContext;

    private FarmItem item;
    private ProductItem response;

    private TextView nameView;

    private LinearLayout detailProductBox;
    private LinearLayout nonBox;

    private ImageView prodcutImgView;
    private TextView productNameView;
    private TextView productUnitView;
    private TextView productPriceView;
    private TextView productDetail;
    private EditText productQuantityView;
    private Button buyBtn;

    private RetroClient retroClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_market);
        mContext = this;
        ButterKnife.bind(this);
        retroClient = RetroClient.getInstance(this).createBaseApi();
        init();
        initView();
        isAvailableProduct();

    }

    public void init(){
        Intent i = getIntent();
        item = (FarmItem)i.getSerializableExtra("item");

//        Toast.makeText(getApplicationContext(),item.getName(),Toast.LENGTH_SHORT).show();
    }
    public void initView(){
        nameView = findViewById(R.id.name);
        nameView.setText(item.getName());

        detailProductBox = findViewById(R.id.detail_product_box);
        nonBox = findViewById(R.id.non_box);

        prodcutImgView = findViewById(R.id.product_img);
        productNameView = findViewById(R.id.product_name);
        productUnitView = findViewById(R.id.product_unit);
        productPriceView = findViewById(R.id.product_price);
        productDetail = findViewById(R.id.product_detail);
        productQuantityView = findViewById(R.id.product_quantity);
        buyBtn = findViewById(R.id.buy);

    }

    private void isAvailableProduct(){
        retroClient.readProductBySellerId(item.getId(), new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(LOG, t.toString());
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                response = (ProductItem)receivedData;
                String id = response.getId();
                Log.d(LOG, "현재 상품 아이디 : " + item.getId());
                if(id.equals("devsim")) Log.d(LOG, "성공");
                else{
                    nonBox.setVisibility(View.GONE);
                    detailProductBox.setVisibility(View.VISIBLE);

                    Glide
                            .with(mContext)
                            .load(response.getImg())
                            .into(prodcutImgView);

                    productNameView.setText(response.getName());
                    productDetail.setText(response.getDetail());
                    productUnitView.setText(response.getUnit());
                    productPriceView.setText(response.getPrice());


                }
            }

            @Override
            public void onFailure(int code) {
                Log.d(LOG, "실패");
            }
        });
    }

    @OnClick(R.id.buy)
    public void buyClicked(){
        String quantity_str = productQuantityView.getText().toString();
        Intent i = new Intent(mContext, BuyActivity.class);
        response.setQuantity(quantity_str);
        i.putExtra("item",response);
        startActivity(i);
    }

}
