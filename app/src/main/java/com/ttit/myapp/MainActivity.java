package com.ttit.myapp;

import android.animation.ObjectAnimator;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ttit.myapp.activity.BaseActivity;
import com.ttit.myapp.activity.HomeActivity;
import com.ttit.myapp.activity.LoginActivity;
import com.ttit.myapp.activity.RegisterActivity;
import com.ttit.myapp.util.StringUtils;


public class MainActivity extends BaseActivity {
    private Button btn_login,btn_register;
    private TextView CoverName,coverName2;
    boolean handler;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        CoverName = findViewById(R.id.coverName);
        coverName2 = findViewById(R.id.coverName_2);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/cover.otf");
        CoverName.setTypeface(typeface);
        coverName2.setTypeface(typeface);
    }

    @Override
    protected void initData() {
        if (!StringUtils.isEmpty(getStringFromSp("token"))){
            navigateTo(HomeActivity.class);
            finish();
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(CoverName,"alpha",0,1).setDuration(1000);
        animator.start();
        handler = new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                coverName2.setVisibility(View.VISIBLE);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(coverName2,"alpha",0,1);
                animator2.setDuration(1000);
                animator2.start();
            }
        },800);


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