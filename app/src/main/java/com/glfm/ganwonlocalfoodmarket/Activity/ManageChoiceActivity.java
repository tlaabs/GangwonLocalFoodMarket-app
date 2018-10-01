package com.glfm.ganwonlocalfoodmarket.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.glfm.ganwonlocalfoodmarket.R;

public class ManageChoiceActivity extends AppCompatActivity {

    private String id;

    private Context mContext;

    private LinearLayout productBtn;
    private LinearLayout orderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_choice);
        mContext = this;
        init();
        initView();
    }

    public void init(){
        Intent i = getIntent();
        id = i.getStringExtra("id");
    }

    public void initView(){
        productBtn = findViewById(R.id.product_go);
        orderBtn = findViewById(R.id.order_go);

        productBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, ProductManageActivity.class);
                i.putExtra("id",id);
                startActivity(i);
            }
        });

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, OrderManageActivity.class);
                i.putExtra("id",id);
                startActivity(i);
            }
        });
    }
}
