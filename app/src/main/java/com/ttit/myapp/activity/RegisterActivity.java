package com.ttit.myapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.ttit.myapp.R;
import com.ttit.myapp.api.Api;
import com.ttit.myapp.api.ApiConfig;
import com.ttit.myapp.api.TtitCallback;
import com.ttit.myapp.entity.LoginResponse;
import com.ttit.myapp.util.StringUtils;

import java.util.HashMap;

public class RegisterActivity extends BaseActivity {
    private EditText etUserName,etPassword;
    private Button btnResiter;

    @Override
    protected int initLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        etUserName = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnResiter = findViewById(R.id.btn_register);
    }

    @Override
    protected void initData() {
        btnResiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = etUserName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                register(account,password);
            }
        });
    }

    private void register(String account, String password) {
        if (StringUtils.isEmpty(account)){
            showToast("请输入账号");
            return;
        }
        if (StringUtils.isEmpty(password)){
            showToast("请输入密码");
            return;
        }

        HashMap<String, Object> params = new HashMap<>();
        params.put("mobile",account);
        params.put("password",password);
        Api.config(ApiConfig.REGISTER,params).postRequest(new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                Gson gson = new Gson();
                LoginResponse loginResponse = gson.fromJson(res, LoginResponse.class);
                if (loginResponse.getCode() == 500){
                    showToastSync("账号已存在，请勿多次注册");
                }
                if (loginResponse.getCode() == 0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialog.Builder(RegisterActivity.this).setTitle("注册成功")
                                    .setMessage("是否前往登录界面?").setNegativeButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    navigateTo(LoginActivity.class);
                                }
                            }).setPositiveButton("否",null).show();
                        }
                    });

                }
            }

            @Override
            public void onFailure(Exception e) {
                showToastSync("网络异常，请检查你的网络连接");
            }
        });
    }
}