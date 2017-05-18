package com.tracyis.ijkplayerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.tracyis.ijkplayerdemo.ui.BiliFragment;
import com.tracyis.ijkplayerdemo.ui.YinkeFragment;

public class MainActivity extends AppCompatActivity {

    private TextView mTv_yinke;
    private TextView mTv_bili;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTv_yinke = (TextView) findViewById(R.id.tv_yinke);
        mTv_bili = (TextView) findViewById(R.id.tv_bili);

        init();
        initListener();
    }

    private void initListener() {
        mTv_bili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fl,new BiliFragment()).commit();

            }
        });
        mTv_yinke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fl,new YinkeFragment()).commit();

            }
        });

    }

    private void init() {
        getSupportFragmentManager().beginTransaction().add(R.id.fl,new YinkeFragment()).commit();

    }

}
