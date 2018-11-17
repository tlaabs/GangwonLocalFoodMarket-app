package com.glfm.ganwonlocalfoodmarket.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.glfm.ganwonlocalfoodmarket.Adapter.OrderAdapter;
import com.glfm.ganwonlocalfoodmarket.Object.OrderVO;
import com.glfm.ganwonlocalfoodmarket.R;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroCallback;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroClient;

import java.util.ArrayList;

public class OrderManageActivity extends AppCompatActivity {

    private String id;

    private RetroClient retroClient;


    private RecyclerView list;

    ArrayList<OrderVO> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manage);
        retroClient = RetroClient.getInstance(this).createBaseApi();
        init();
        initView();

        getOrdersFromServer();
    }


    public void init(){
        Intent i = getIntent();
        id = i.getStringExtra("id");
    }

    public void initView(){
        list = findViewById(R.id.list);
    }

    private void initList(ArrayList<OrderVO> arr){
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new OrderAdapter(this,arr));
        list.invalidate();
    }

    private void getOrdersFromServer(){
        retroClient.getOrders(id, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d("kokz", t.toString());
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                orderList = (ArrayList<OrderVO>) receivedData;
                int size = orderList.size();
//                if(size == 0){
//                    Log.d("kokz", size+ "개");
//                    return;
//                }else{
//                    Log.d("kokz", size+ "개");
//                }
                initList(orderList);
            }

            @Override
            public void onFailure(int code) {
                Log.d("kokz", "실패");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrdersFromServer();
    }
}
