package com.benben.carmanager.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.benben.carmanager.R;
import com.benben.carmanager.bean.ShareParkBean;
import com.benben.carmanager.dao.ShareParkDao;
import com.benben.carmanager.utils.Api;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.orhanobut.hawk.Hawk;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddShareParkActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvTitle;
    private ImageView mIvBack;
    private EditText mEtText;

    @BindView(R.id.btn_add)
    TextView btnAdd;

    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.et_addr)
    EditText etAddr;
    @BindView(R.id.et_price)
    EditText etPrice;
    private ShareParkDao parkDao;

    @BindView(R.id.tv_startTime)
    TextView tv_startTime;
    @BindView(R.id.tv_endTime)
    TextView tv_endTime;

    private TimePickerView pvTime;
    //开始的时间和结束时间
    private String startTime, endTime;
    private DateFormat df;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_add_sharepark);
        ButterKnife.bind(this);

        parkDao = new ShareParkDao(this);
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mTvTitle = findViewById(R.id.tv_title);
        mIvBack = findViewById(R.id.iv_back);
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTvTitle.setText("添加车位信息");

        Calendar instance = Calendar.getInstance();
        Date time = instance.getTime();
        startTime = df.format(time);
        Calendar clone = (Calendar) instance.clone();
        clone.add(Calendar.DAY_OF_MONTH, 1);
        endTime = df.format(clone.getTime());

        tv_startTime.setText(startTime);
        tv_endTime.setText(endTime);

    }

    @OnClick({R.id.btn_add, R.id.tv_startTime, R.id.tv_endTime})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                String number = etNumber.getText().toString().trim();
                if(TextUtils.isEmpty(number)){
                    Toast.makeText(this, "输入车位号", Toast.LENGTH_SHORT).show();
                    return;
                }
                String address = etAddr.getText().toString().trim();
                if(TextUtils.isEmpty(address)){
                    Toast.makeText(this, "输入车位地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                String price = etPrice.getText().toString().trim();
                if(TextUtils.isEmpty(price)){
                    Toast.makeText(this, "输入每小时的价格", Toast.LENGTH_SHORT).show();
                    return;
                }

                ShareParkBean parkBean = new ShareParkBean(number,address,price,startTime,endTime);
                long rowsId = parkDao.add(parkBean);
                if(rowsId>0){
                    Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(this, "发布失败", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.tv_startTime:
                //时间选择器
                pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        startTime = df.format(date);
                        tv_startTime.setText(startTime);
                    }
                })
                        .setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
                        .build();
                pvTime.show();
                break;
            case R.id.tv_endTime:
                //时间选择器
                pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        endTime = df.format(date);
                        tv_endTime.setText(endTime);
                    }
                })
                        .setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
                        .build();
                pvTime.show();
                break;
        }

    }
}