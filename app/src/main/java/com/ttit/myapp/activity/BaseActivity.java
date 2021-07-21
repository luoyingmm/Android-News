package com.ttit.myapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    private Context mcContext;
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        mcContext = this;
        initLayout();
        initView();
        initData();
    }

    protected abstract int initLayout();
    protected abstract void initView();
    protected abstract void initData();

    public void showToast(String msg){
        Toast.makeText(mcContext, msg, Toast.LENGTH_SHORT).show();
    }

    public void showToastSync(String msg){
        Looper.prepare();
        Toast.makeText(mcContext, msg, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
    public void navigateTo(Class cls){
        Intent intent = new Intent(mcContext,cls);
        startActivity(intent);
    }
    protected void saveStringToSp(String key,String val){
        SharedPreferences sp = getSharedPreferences("sp_ttit", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key,val);
        edit.commit();
    }

    protected String getStringFromSp(String key){
        SharedPreferences sp = getSharedPreferences("sp_ttit", MODE_PRIVATE);
        return sp.getString(key,"");
    }
    public void navigateToWithFlag(Class cls,int flag){
        Intent intent = new Intent(mcContext, cls);
        intent.setFlags(flag);
        startActivity(intent);
    }
}
