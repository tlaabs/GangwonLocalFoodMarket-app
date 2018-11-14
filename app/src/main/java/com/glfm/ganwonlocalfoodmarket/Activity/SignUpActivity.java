package com.glfm.ganwonlocalfoodmarket.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.glfm.ganwonlocalfoodmarket.Object.SignUp;
import com.glfm.ganwonlocalfoodmarket.R;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroCallback;
import com.glfm.ganwonlocalfoodmarket.Retrofit.RetroClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {

    final static String LOG = "SignUpActivity";

    @BindView(R.id.idBox) EditText idBox;
    @BindView(R.id.pwdBox) EditText pwdBox;
    @BindView(R.id.nameBox) EditText nameBox;
    @BindView(R.id.back) Button backBtn;
    @BindView(R.id.signup) Button signupBtn;

    private Context mContext;
    private SignUp signUpObj;

    private RetroClient retroClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mContext = this;
        ButterKnife.bind(this);
        retroClient = RetroClient.getInstance(this).createBaseApi();
        init();
    }

    private void init(){
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpObj = checkSignupValidation();
                if(signUpObj == null){
                    Toast.makeText(getApplicationContext(),"다시 확인!",Toast.LENGTH_SHORT).show();
                }else{ //검사 통과
                    requestSignUp(signUpObj);
                }
            }
        });
    }

    private SignUp checkSignupValidation(){
        String id = idBox.getText().toString();
        String pwd = pwdBox.getText().toString();
        String name = nameBox.getText().toString();

        if(id == null || id.equals(""))return null;
        if(pwd == null || pwd.equals(""))return null;
        if(name == null || name.equals(""))return null;

        SignUp result = new SignUp(id,pwd,name);
        return result;


    }

    private void requestSignUp(SignUp obj){
        retroClient.signup(obj, new RetroCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d(LOG, t.toString());
            }

            @Override
            public void onSuccess(int code, Object receivedData) {
                Log.d(LOG, "성공");
                Toast.makeText(getApplicationContext(),"통과!",Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(int code) {
                Log.d(LOG, "실패");
            }
        });
    }
}
