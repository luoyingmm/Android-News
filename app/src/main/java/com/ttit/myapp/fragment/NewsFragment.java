package com.ttit.myapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ttit.myapp.R;
import com.ttit.myapp.activity.LoginActivity;
import com.ttit.myapp.activity.WebActivity;
import com.ttit.myapp.adapter.NewsAdapter;
import com.ttit.myapp.api.Api;
import com.ttit.myapp.api.ApiConfig;
import com.ttit.myapp.api.TtitCallback;
import com.ttit.myapp.entity.NewsEntity;
import com.ttit.myapp.entity.NewsListResponse;
import com.ttit.myapp.util.StringUtils;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private int pageNum=1;
    private RefreshLayout refreshLayout;
    private NewsAdapter newsAdapter;
    private List<NewsEntity> data = new ArrayList<>();
    private  LinearLayoutManager manager;
    public NewsFragment() {

    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull @NotNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    newsAdapter.setData(data);
                    newsAdapter.notifyDataSetChanged();
                    break;
            }

        }
    };


    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }
    @Override
    protected int initLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView() {
        recyclerView = mRootView.findViewById(R.id.recyclerView);
        refreshLayout = mRootView.findViewById(R.id.refreshLayout);
    }

    @Override
    protected void initData() {
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        newsAdapter  = new NewsAdapter(getActivity());

        recyclerView.setAdapter(newsAdapter);
        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Serializable obj) {
//                showToast("点击");
                NewsEntity newsEntity = (NewsEntity) obj;
//                String url = "http://121.4.129.51:8089/newsDetail?title=" + newsEntity.getAuthorName();
                String url = "https://www.luoyingmm.com";
                Bundle bundle = new Bundle();
                bundle.putString("url",url);
                navigateToWithBundle(WebActivity.class,bundle);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 1;
                getNewsList(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageNum++;
                getNewsList(false);
            }
        });
        getNewsList(true);
    }

    private void getNewsList(boolean isRefresh){
        String token = getStringFromSp("token");
        if (!StringUtils.isEmpty(token)){
            HashMap<String, Object> params = new HashMap<>();
            params.put("token",token);
            params.put("page",pageNum);
            params.put("limit", ApiConfig.PAGE_SIZE);

            Api.config(ApiConfig.NEWS_LIST,params).getRequest(new TtitCallback() {
                @Override
                public void onSuccess(String res) {
                    if (isRefresh){
                        refreshLayout.finishRefresh(true);
                    }else {
                        refreshLayout.finishLoadMore(true);
                    }
                    NewsListResponse response = new Gson().fromJson(res, NewsListResponse.class);
                    if (response != null &&response.getCode() == 0){
                        List<NewsEntity> list = response.getPage().getList();
                        if (list != null && list.size()> 0 ){
                            if (isRefresh){
                                data = list;
                            }else {
                                data.addAll(list);
                            }
                            mHandler.sendEmptyMessage(0);
                        }else {
                            if (isRefresh){
                                showToastSync("暂时无数据");
                            }else {
                                showToastSync("没有更多数据");
                            }

                        }

                    }

                }
                @Override
                public void onFailure(Exception e) {
                    if (isRefresh){
                        refreshLayout.finishRefresh(true);
                    }else {
                        refreshLayout.finishLoadMore(true);
                    }
                }
            });
        }else {
            navigateTo(LoginActivity.class);
        }


    }
}