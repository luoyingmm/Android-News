package com.ttit.myapp.fragment;

import android.view.View;
import android.widget.RelativeLayout;

import com.ttit.myapp.MainActivity;
import com.ttit.myapp.R;
import com.ttit.myapp.util.DialogUtil;


public class MyFragment extends BaseFragment {
    private RelativeLayout rl_logout;

    public MyFragment() {
        // Required empty public constructor
    }

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView() {
        rl_logout = mRootView.findViewById(R.id.rl_logout);
    }

    @Override
    protected void initData() {
        rl_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.showAlertDialog(getActivity(), R.drawable.restart, "退出提示", "你确定要退出吗？",
                        "确定", "取消", true, new DialogUtil.AlertDialogBtnClickListener() {
                    @Override
                    public void clickPositive() {
                        saveStringToSp("token","");
                        navigateTo(MainActivity.class);
                        getActivity().finish();
                    }
                    @Override
                    public void clickNegative() {
                        //negative
                    }
                });
            }
        });
    }
}