package com.glfm.ganwonlocalfoodmarket.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.glfm.ganwonlocalfoodmarket.Object.BuyVO;
import com.glfm.ganwonlocalfoodmarket.R;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroCallback;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroClient;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateStateActivity extends AppCompatActivity {

    final static String LOG = "UpdateStateActivity";

    private BuyVO item;

    private MaterialSpinner spinner;
    private Button submit;

    private String state = "입금 대기 중"; //의심,위험

    private RetroClient retroClient;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_state);
        init();
        mContext = this;
        ButterKnife.bind(this);
        retroClient = RetroClient.getInstance(this).createBaseApi();
        initView();
    }

    public void init(){
        Intent i = getIntent();
        item = (BuyVO)i.getSerializableExtra("item");
    }

    public void initView(){
        spinner = findViewById(R.id.spinner);
        submit = findViewById(R.id.submit);

        spinner.setItems("입금 대기 중", "배송 준비 중", "배송 중", "배송 완료");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                state = item.toString();
            }
        });
    }

    @OnClick(R.id.submit)
    public void submit(){

        retroClient.updateState(item.getId(),state, new RetroCallback() {
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
}
