package com.glfm.ganwonlocalfoodmarket.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.glfm.ganwonlocalfoodmarket.Object.Login;
import com.glfm.ganwonlocalfoodmarket.R;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroCallback;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    final static String LOG = "LoginActivity";

    @BindView(R.id.logo) ImageView logoImg;
    @BindView(R.id.signup) TextView signup;
    @BindView(R.id.idBox) EditText idBox;
    @BindView(R.id.pwdBox) EditText pwdBox;
    @BindView(R.id.login) Button loginBtn;

    private Context mContext;
    private RetroClient retroClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        ButterKnife.bind(this);
        retroClient = RetroClient.getInstance(this).createBaseApi();

        init();
    }

    private void init(){
        //로고 이미지
        Glide.with(mContext)
                .load(R.drawable.carrot_main)
                .into(logoImg);
        //가입
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, SignUpActivity.class);
                startActivity(i);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = idBox.getText().toString();
                String pwd = pwdBox.getText().toString();
                Login dto = new Login(id,pwd);
                requestLogin(dto);
            }
        });
    }

    private void requestLogin(Login obj){
        retroClient.login(obj, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(LOG, t.toString());
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Log.d(LOG, "성공");
                Toast.makeText(getApplicationContext(),"로그인!",Toast.LENGTH_SHORT).show();
                //Preference 작업
            }

            @Override
            public void onFailure(int code) {
                Log.d(LOG, "실패");
            }
        });
    }
}
