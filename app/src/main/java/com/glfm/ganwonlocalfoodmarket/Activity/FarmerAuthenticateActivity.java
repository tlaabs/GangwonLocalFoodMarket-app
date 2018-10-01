package com.glfm.ganwonlocalfoodmarket.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.glfm.ganwonlocalfoodmarket.Object.FarmItem;
import com.glfm.ganwonlocalfoodmarket.R;

public class FarmerAuthenticateActivity extends AppCompatActivity {
    private EditText idInput;
    private EditText pwdInput;
    private Button submit;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_authenticate);
        mContext = this;
        initView();
    }

    public void initView(){
        idInput = findViewById(R.id.id_input);
        pwdInput = findViewById(R.id.pwd_input);

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = idInput.getText().toString();
                String pwd = pwdInput.getText().toString();

                if(id.equals(pwd)){
                    Intent i = new Intent(mContext,ManageChoiceActivity.class);
                    i.putExtra("id",id);
                    startActivity(i);
                }

            }
        });
    }
}
