package com.example.coco.refreshview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.refreshview.CustomRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.mRv)
    CustomRefreshView mRv;
    private List<String> data;
    private MyAdapter adapter;
    private int pagerSize = 10;
    private int mm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        data = new ArrayList<>();
        adapter = new MyAdapter(data, this);
        mRv.setAdapter(adapter);


        mRv.setOnLoadListener(new CustomRefreshView.OnLoadListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        data.clear();
                        for (int i = 0; i < pagerSize; i++) {
                            if (mm >= 2) {
                                data.add(String.valueOf(i));
                            }
                        }
                        ++mm;
                        //模拟无数据界面
                        if (mm == 1) {

                            mRv.setEmptyView("暂无数据");
                            mRv.complete();
                            return;
                        }
                        //模拟网络出错界面
                        if (mm == 2) {
                            mRv.setErrorView();
                            mRv.complete();
                            return;
                        }

                        mRv.complete();
                        adapter.notifyDataSetChanged();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < pagerSize; i++) {
                            data.add(String.valueOf(i));
                        }
                        if (data.size() > 20 && data.size() < 50) {
                            mRv.onError();
                        } else {
                            if (data.size() < 70) {
                                mRv.stopLoadingMore();
                            }
                        }
                        if (data.size() >= 70) {
                            mRv.onNoMore();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, 1000);
            }
        });

        //设置自动下拉刷新，切记要在recyclerView.setOnLoadListener()之后调用
        //因为在没有设置监听接口的情况下，setRefreshing(true),调用不到OnLoadListener
        mRv.setRefreshing(true);
    }


}
