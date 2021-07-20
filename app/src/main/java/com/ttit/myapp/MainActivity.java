package com.ttit.myapp;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ttit.myapp.activity.BaseActivity;
import com.ttit.myapp.activity.LoginActivity;
import com.ttit.myapp.activity.RegisterActivity;

public class MainActivity extends BaseActivity {
    private Button btn_login,btn_register;
    private TextView CoverName;
    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        CoverName = findViewById(R.id.coverName);
    }

    @Override
    protected void initData() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(LoginActivity.class);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(RegisterActivity.class);
            }
        });
    }
}