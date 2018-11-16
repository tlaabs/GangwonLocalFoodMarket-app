package com.glfm.ganwonlocalfoodmarket.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.glfm.ganwonlocalfoodmarket.Object.OnlyIdDTO;
import com.glfm.ganwonlocalfoodmarket.Object.UserVO;
import com.glfm.ganwonlocalfoodmarket.R;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroCallback;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroClient;
import com.glfm.ganwonlocalfoodmarket.Util.UserLoginSession;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class UpdateMyProfileActivity extends AppCompatActivity {
    final static String LOG = "UpdateMyProfileActivity";

    @BindView(R.id.idBox) EditText idBox;
    @BindView(R.id.pwdBox) EditText pwdBox;
    @BindView(R.id.nameBox) EditText nameBox;
    @BindView(R.id.phoneBox) EditText phoneBox;
    @BindView(R.id.emailBox) EditText emailBox;
    @BindView(R.id.addressBox) EditText addressBox;
    @BindView(R.id.update) Button updateBtn;

    private Context mContext;
    private RetroClient retroClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_my_profile);
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
                    UserVO user = ((UserVO) receivedData);
                    idBox.setText(user.getId());
                    pwdBox.setText(user.getPwd());
                    nameBox.setText(user.getName());
                    phoneBox.setText(user.getPhone());
                    emailBox.setText(user.getEmail());
                    addressBox.setText(user.getAddress());
            }

            @Override
            public void onFailure(int code) {
                Log.d(LOG, "실패");
            }
        });
    }

    @OnClick(R.id.update)
    public void updateClick(){
        UserVO user = new UserVO();
        user.setId(idBox.getText().toString());
        user.setPwd(pwdBox.getText().toString());
        user.setName(nameBox.getText().toString());
        user.setPhone(phoneBox.getText().toString());
        user.setEmail(emailBox.getText().toString());
        user.setAddress(addressBox.getText().toString());

        retroClient.updateProfile(user, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(LOG, t.toString());
            }

            @Override
            public void onSuccess(int code, Object receivedData){
                Toast.makeText(getApplicationContext(),"업데이트 성공!",Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(int code) {
                Log.d(LOG, "실패");
            }
        });
    }
}
