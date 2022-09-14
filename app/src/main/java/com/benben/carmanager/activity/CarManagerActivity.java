package com.benben.carmanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.benben.carmanager.R;

public class CarManagerActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvTitle;
    private ImageView mIvBack;
    private LinearLayout mLLAddBrand;
    private LinearLayout mLLAddCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_car_manager);

        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mLLAddBrand = findViewById(R.id.ll_addbrand);
        mLLAddCar = findViewById(R.id.ll_addcar);
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTvTitle.setText("车辆管理");
        mLLAddBrand.setOnClickListener(this);
        mLLAddCar.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.ll_addbrand:
                intent.setClass(this,AddBrandActivity.class);
                break;
            case R.id.ll_addcar:
                intent.setClass(this,AddCarActivity.class);
                break;
        }
        startActivity(intent);
    }
}