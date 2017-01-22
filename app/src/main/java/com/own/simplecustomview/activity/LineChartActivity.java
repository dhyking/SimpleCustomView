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
    private float[] yPercent = new float[7];
    private float[] amountArr =  new float[]{1.43f,0.5f,23,19999,0.02f,5,213213};
    private List<Float> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        dealData();
        LineChartView mLineChartView = new LineChartView(this,yPercent,amountArr);
        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.layout);
        Log.d("LineChartActivity", "mLinearLayout.getWidth():" + mLinearLayout.getWidth());
        Log.d("LineChartActivity", "mLinearLayout.getHeight():" + mLinearLayout.getHeight());
        mLinearLayout.addView(mLineChartView);

    }

    private void dealData() {
        for (int i = 0; i < amountArr.length; i++) {
            list.add(amountArr[i]);
        }
        float max = Collections.max(list) * 1.8f;
        for (int j = 0; j < yPercent.length; j++) {

                float data = (amountArr[j] / max);
                yPercent[j] = ((float) Math.round(data *10000)) /10000;
            Log.d("LineChartActivity", "yPercent[j]:" + yPercent[j]);
        }

    }
}
