package com.own.simplecustomview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.own.simplecustomview.activity.BezierActivity;
import com.own.simplecustomview.activity.CustomTextActivity;
import com.own.simplecustomview.activity.RoundImageActivity;
import com.own.simplecustomview.activity.ValidCodeActivity;
import com.own.simplecustomview.adapter.MyListAdapter;
import com.own.simplecustomview.listener.OnIemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycle_list)
    RecyclerView mRecycleList;
    private List<String> dataList = new ArrayList<>();
    private final static String[] dataArr = {"自定义的TextView","自定义的验证码View","自定义圆角ImageView",
            "简单自定义贝塞尔曲线"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 初始数据
     */
    private void init() {
        for (String mS : dataArr) {
            dataList.add(mS);
        }
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecycleList.setLayoutManager(mLinearLayoutManager);
        MyListAdapter mMyListAdapter = new MyListAdapter(dataList,this);
        mRecycleList.setAdapter(mMyListAdapter);
        mMyListAdapter.setOnIemClickListener(new OnIemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent mIntent = new Intent();
                switch (position) {
                    case 0:
                        mIntent.setClass(MainActivity.this,CustomTextActivity.class);
                        break;
                    case 1:
                        mIntent.setClass(MainActivity.this,ValidCodeActivity.class);
                        break;
                    case 2:
                        mIntent.setClass(MainActivity.this, RoundImageActivity.class);
                        break;
                    case 3:
                        mIntent.setClass(MainActivity.this, BezierActivity.class);
                        break;
                }
                startActivity(mIntent);
            }
        });
    }
}
