package com.own.simplecustomview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.own.simplecustomview.R;
import com.own.simplecustomview.view.LineChartView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LineChartActivity extends AppCompatActivity {
    private float[] xAlis = new float[]{20,50,80,110,140,170,200};
    private float[] yAlis = new float[]{1,4,10,0,0,30,0.01f};
    private float[] yPercent = new float[7];
    private List<Float> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        dealData();
        LineChartView mLineChartView = new LineChartView(this,xAlis,yPercent);
        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.layout);
        Log.d("LineChartActivity", "mLinearLayout.getWidth():" + mLinearLayout.getWidth());
        Log.d("LineChartActivity", "mLinearLayout.getHeight():" + mLinearLayout.getHeight());
        mLinearLayout.addView(mLineChartView);

    }

    private void dealData() {
        for (int i = 0; i < yAlis.length; i++) {
            list.add(yAlis[i]);
        }
        float max = Collections.max(list) * 1.5f;
        for (int j = 0; j < yPercent.length; j++) {
            float data = (yAlis[j] / max);
            if (yAlis[j] != 0) {
                yPercent[j] = ((float) Math.round(data *10000)) /10000;
            } else {
                yPercent[j] = 0;
            }
        }

    }
}
