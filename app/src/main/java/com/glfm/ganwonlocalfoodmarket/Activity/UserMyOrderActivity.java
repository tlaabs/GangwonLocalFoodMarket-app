package com.glfm.ganwonlocalfoodmarket.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.glfm.ganwonlocalfoodmarket.Adapter.MyOrderAdapter;
import com.glfm.ganwonlocalfoodmarket.Object.OrderVO;
import com.glfm.ganwonlocalfoodmarket.R;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroCallback;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroClient;
import com.glfm.ganwonlocalfoodmarket.Util.UserLoginSession;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;

public class UserMyOrderActivity extends AppCompatActivity {
    final static String LOG = "UserMyOrderActivity";
    private SQLiteDatabase db;

    ArrayList<OrderVO> orderList;

    private RecyclerView list;

    private RetroClient retroClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_my_order);

        retroClient = RetroClient.getInstance(this).createBaseApi();
        init();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        retroClient.getOrdersByCustomer(UserLoginSession.getSession().getUser(), new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(LOG, t.toString());
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                try {
                    ArrayList<OrderVO> orders = ((ArrayList<OrderVO>) receivedData);
                    Log.d(LOG, "주문 사이즈 : " + orders.size());

                    orderList = orders;

                    initList(orderList);
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(int code) {
                Log.d(LOG, "실패");
            }
        });
    }

    public void init() {

    }

    public void initView() {
        list = findViewById(R.id.list);
    }

    private void initList(ArrayList<OrderVO> arr) {
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new MyOrderAdapter(this, arr));
        list.invalidate();
    }

}
