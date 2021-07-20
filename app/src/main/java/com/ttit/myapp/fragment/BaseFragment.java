package com.ttit.myapp.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dueeeke.videoplayer.player.VideoViewManager;

import org.jetbrains.annotations.NotNull;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

public abstract class BaseFragment extends Fragment {
    protected View mRootView;
    private Unbinder unbinder;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        if (mRootView == null){
            mRootView = inflater.inflate(initLayout(),container,false);
            initView();
        }
        unbinder = ButterKnife.bind(this,mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected abstract int initLayout();
    protected abstract void initView();
    protected abstract void initData();

    public void showToast(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public void showToastSync(String msg){
        Looper.prepare();
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
    public void navigateTo(Class cls){
        Intent intent = new Intent(getActivity(),cls);
        startActivity(intent);
    }

    public void navigateToWithBundle(Class cls,Bundle bundle){
        Intent intent = new Intent(getActivity(),cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void saveStringToSp(String key,String val){
        SharedPreferences sp = getActivity().getSharedPreferences("sp_ttit", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key,val);
        edit.commit();
    }

    protected String getStringFromSp(String key){
        SharedPreferences sp = getActivity().getSharedPreferences("sp_ttit", MODE_PRIVATE);
        return sp.getString(key,"");
    }

    protected VideoViewManager getVideoViewManager() {
        return VideoViewManager.instance();
    }
}
