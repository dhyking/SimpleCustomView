package com.own.simplecustomview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.own.simplecustomview.R;
import com.own.simplecustomview.listener.OnTextChangeListener;
import com.own.simplecustomview.view.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomTextActivity extends AppCompatActivity {

    @BindView(R.id.tv_custom)
    CustomTextView mTvCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_text);
        ButterKnife.bind(this);

    }
}
