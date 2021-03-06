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

import com.glfm.ganwonlocalfoodmarket.Adapter.MyDiaryAdapterForCustomer;
import com.glfm.ganwonlocalfoodmarket.Adapter.MyDiaryAdapterForSeller;
import com.glfm.ganwonlocalfoodmarket.Object.DiaryVO;
import com.glfm.ganwonlocalfoodmarket.Object.FarmItem;
import com.glfm.ganwonlocalfoodmarket.R;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroCallback;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroClient;
import com.glfm.ganwonlocalfoodmarket.Util.UserLoginSession;

import java.util.ArrayList;

public class DiaryActivityForCustomer extends AppCompatActivity {
    final static String LOG = "DiaryActivityForCus";

    private String farmId;

    ArrayList<DiaryVO> diaryList;

    private RecyclerView list;

    private RetroClient retroClient;

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_for_customer);
        mContext = this;
        retroClient = RetroClient.getInstance(this).createBaseApi();
        init();
        initView();
    }

    private void init(){
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        farmId = ((FarmItem)bundle.get("item")).getId();
        Log.d(LOG,"farmId : " + farmId);
    }
    @Override
    protected void onResume() {
        super.onResume();
        retroClient.getDiarysForCustomer(farmId, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(LOG, t.toString());
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                try {
                    ArrayList<DiaryVO> orders = ((ArrayList<DiaryVO>) receivedData);
                    Log.d(LOG, "주문 사이즈 : " + orders.size());

                    diaryList = orders;

                    initList(diaryList);
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(int code) {
                Log.d(LOG, "실패");
            }
        });
    }

    public void initView() {
        list = findViewById(R.id.list);
    }

    private void initList(ArrayList<DiaryVO> arr) {
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new MyDiaryAdapterForCustomer(this, arr));
        list.invalidate();
    }
}
