package com.glfm.ganwonlocalfoodmarket.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.glfm.ganwonlocalfoodmarket.Util.UserLoginSession;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

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

    //자동로그인
    @Override
    protected void onResume() {
        super.onResume();
        //pref 로드
        SharedPreferences prefs = getSharedPreferences("glfm", MODE_PRIVATE);
        String id = prefs.getString("id", "");
        String type = prefs.getString("type", "");

        if(id == null || id.equals("")){//이전 로그인한 적이 없음
        }else{
            UserLoginSession.getSession().setUser(id);//세션 저장
            UserLoginSession.getSession().setType(type);//세션 저장
            
            Log.d("LoginActivity",UserLoginSession.getUser());
            Intent i = new Intent(mContext, MainActivity.class);
            startActivity(i);
            finish();
        }
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

    private void requestLogin(final Login obj){
        retroClient.login(obj, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(LOG, t.toString());
            }

            @Override
            public void onSuccess(int code, Object receivedData){
                try {
                    ResponseBody body = ((ResponseBody) receivedData);
                    String responseJSON = body.string();

                    JSONObject jObj1 = new JSONObject(responseJSON);
                    JSONObject jObj2 = jObj1.getJSONObject("results");
                    String userId = jObj2.getString("id");
                    String userType = jObj2.getString("type");

                    Log.d(LOG, "성공");
                    Toast.makeText(getApplicationContext(), "로그인!", Toast.LENGTH_SHORT).show();
                    //Preference 작업 (첫 로그인시, 한번 로그인하면 자동로그인됨)
                    SharedPreferences prefs = getSharedPreferences("glfm", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("id", userId);
                    editor.putString("type", userType);

                    editor.commit();
                    //넘겨줄때 아이디값 넘겨주자. 자동로그인도 구현해두자.(이건 onResume? oncreate에 넣어야할듯)
                    //세션에 저장
                    UserLoginSession.getSession().setUser(userId);
                    UserLoginSession.getSession().setType(userType);

                    Intent i = new Intent(mContext, MainActivity.class);
                    startActivity(i);
                    finish();
                }catch(Exception e){}
            }

            @Override
            public void onFailure(int code) {
                Log.d(LOG, "실패");
            }
        });
    }
}
