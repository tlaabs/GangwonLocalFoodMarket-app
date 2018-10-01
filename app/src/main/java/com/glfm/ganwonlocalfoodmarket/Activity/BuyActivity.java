package com.glfm.ganwonlocalfoodmarket.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.glfm.ganwonlocalfoodmarket.Object.BuyVO;
import com.glfm.ganwonlocalfoodmarket.Object.ProductItem;
import com.glfm.ganwonlocalfoodmarket.R;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroCallback;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroClient;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

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
    @BindView(R.id.address)
    EditText addressView;
    @BindView(R.id.who)
    EditText whoView;
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

    @OnClick(R.id.submit)
    public void submit() {
        BuyVO orderItem = new BuyVO();
        orderItem.setProduct_id(item.getId());
        orderItem.setProduct_name(item.getName());
        orderItem.setOrder_name(whoView.getText().toString());
        orderItem.setQuantity(item.getQuantity());
        orderItem.setAddress(addressView.getText().toString());
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
                BuyVO res = (BuyVO) receivedData;
                insertDataToDB(res);
                Log.d(LOG, "성공" + res.getId());
                finish();
            }

            @Override
            public void onFailure(int code) {
                Log.d(LOG, "실패");
            }
        });
    }


    private void insertDataToDB(BuyVO vo) {
        SQLiteDatabase db = openOrCreateDatabase(getString(R.string.db_name), MODE_PRIVATE, null);
        ContentValues recordValues;
        try {

            recordValues = new ContentValues();

            recordValues.put("ID", vo.getId());
            recordValues.put("PRODUCT", vo.getProduct_name());
            recordValues.put("QUANTITY", vo.getQuantity());
            recordValues.put("PRICE", vo.getTotal());

            db.insert("tbl_order", null, recordValues);
        } catch (Exception e) {
            e.printStackTrace();
        } finally
        {
            db.close();
        }
    }

}
