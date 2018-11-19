package com.glfm.ganwonlocalfoodmarket.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.glfm.ganwonlocalfoodmarket.Object.OnlyIdDTO;
import com.glfm.ganwonlocalfoodmarket.Object.UserVO;
import com.glfm.ganwonlocalfoodmarket.R;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroCallback;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroClient;
import com.glfm.ganwonlocalfoodmarket.Util.UserLoginSession;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CardManageActivity extends AppCompatActivity {
    final static String LOG = "CardManageActivity";

    @BindView(R.id.pin1)EditText pin1View;
    @BindView(R.id.pin2)EditText pin2View;
    @BindView(R.id.pin3)EditText pin3View;
    @BindView(R.id.pin4)EditText pin4View;
    @BindView(R.id.add)Button addBtn;

    private Context mContext;
    private RetroClient retroClient;

    private UserVO recevUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_manage);

        mContext = this;
        ButterKnife.bind(this);
        retroClient = RetroClient.getInstance(this).createBaseApi();
        init();
    }

    private void init(){
        loadUserData();
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
                recevUser = ((UserVO) receivedData);
                String pin = recevUser.getCard();
                if(pin == null || pin.length() != 16){
                    return;
                }
                pin1View.setText(pin.substring(0,4));
                pin2View.setText(pin.substring(4,8));
                pin3View.setText(pin.substring(8,12));
                pin4View.setText(pin.substring(12,16));

            }

            @Override
            public void onFailure(int code) {
                Log.d(LOG, "실패");
            }
        });
    }

    @OnClick(R.id.add)
    public void addClick(){
        String pin1 = pin1View.getText().toString();
        String pin2 = pin2View.getText().toString();
        String pin3 = pin3View.getText().toString();
        String pin4 = pin4View.getText().toString();

        //검증 나중에

        StringBuffer sb = new StringBuffer();
        sb.append(pin1);
        sb.append(pin2);
        sb.append(pin3);
        sb.append(pin4);

        String pin = sb.toString();
        if(pin == null || pin.length() != 16) return;

        recevUser.setCard(pin);

        retroClient.updateProfile(recevUser, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(LOG, t.toString());
            }

            @Override
            public void onSuccess(int code, Object receivedData){
                Toast.makeText(getApplicationContext(),"카드 등록 완료.",Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(int code) {
                Log.d(LOG, "실패");
            }
        });
    }
}
