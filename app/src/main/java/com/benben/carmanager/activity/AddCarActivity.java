package com.benben.carmanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.benben.carmanager.R;
import com.benben.carmanager.utils.Api;
import com.benben.carmanager.utils.Utils;
import com.orhanobut.hawk.Hawk;

public class AddCarActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvTitle;
    private ImageView mIvBack;
    private EditText mEtText,mEtBrand;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_add_car);

        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        Button mBtnAdd = findViewById(R.id.btn_add);
        mEtText = findViewById(R.id.et_number);
        mEtBrand = findViewById(R.id.et_brand);
        mBtnAdd.setOnClickListener(this);
        mIvBack.setVisibility(View.VISIBLE);


        String carNumber = Hawk.get(Api.CarNumberKey);
        String brand = Hawk.get(Api.CAR_BRAND_KEY);
        mEtText.setText(carNumber);
        mEtBrand.setText(brand);

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTvTitle.setText("添加车辆");
    }

    @Override
    public void onClick(View v) {
        String number = mEtText.getText().toString().trim();
        String brand = mEtBrand.getText().toString().trim();
        if(TextUtils.isEmpty(number) || !Utils.isCarNumber(number)){
            Toast.makeText(this, "输入正确的车牌号", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(brand)){
            Toast.makeText(this, "输入车辆品牌", Toast.LENGTH_SHORT).show();
            return;
        }

        Hawk.put(Api.CarNumberKey,number);
        Hawk.put(Api.CAR_BRAND_KEY,brand);
        Toast.makeText(this, "已添加", Toast.LENGTH_SHORT).show();
        finish();
    }
}