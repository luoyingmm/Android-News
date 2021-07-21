package com.ttit.myapp.activity;

import android.content.Intent;
import android.util.Log;
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

public class LoginActivity extends BaseActivity {
    private EditText etUserName,etPassword;
    private Button btnLogin;


    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        etUserName = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
    }

    @Override
    protected void initData() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = etUserName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                login(account,password);
            }
        });
    }


    private void login(String account, String password) {
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
        Api.config(ApiConfig.LOGIN,params).postRequest(new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                Log.e("onSuccess", res);
                Gson gson = new Gson();
                LoginResponse loginResponse = gson.fromJson(res, LoginResponse.class);
                if (loginResponse.getCode() == 0){
                    String token = loginResponse.getToken();
                    saveStringToSp("token",token);
                    navigateToWithFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    showToastSync("登陆成功");
                }else {
                    showToastSync("账号或密码错误");
                }

            }

            @Override
            public void onFailure(Exception e) {
                showToastSync("网络异常，请检查你的网络连接");
            }
        });
    }
    }



