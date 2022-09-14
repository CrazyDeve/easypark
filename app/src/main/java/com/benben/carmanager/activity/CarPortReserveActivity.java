package com.benben.carmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.benben.carmanager.R;
import com.benben.carmanager.bean.CarPortBean;
import com.benben.carmanager.bean.OrderBean;
import com.benben.carmanager.dao.OrderDao;
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

public class CarPortReserveActivity extends AppCompatActivity {

    private static final String TAG = "SearchCarPortActivity";
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tvReserve)
    TextView tvReserve;
    @BindView(R.id.tv_carport_title)
    TextView tvCarPortTitle;
    @BindView(R.id.tv_addr)
    TextView tvAddr;
    @BindView(R.id.tv_startTime)
    TextView tv_startTime;
    @BindView(R.id.tv_endTime)
    TextView tv_endTime;
    @BindView(R.id.iv_nav)
    ImageView ivNav;
    @BindView(R.id.tv_carNumber)
    EditText tv_carNumber;

    //开始的时间和结束时间
    private String startTime, endTime;

    private CarPortBean carPortInfo;
    private DateFormat df;
    private TimePickerView pvTime;
    private String carNumber;
    private OrderDao orderDao;

    public void initData() {

        try {
            Calendar instance = Calendar.getInstance();
            Date time = instance.getTime();
            startTime = df.format(time);
            Calendar clone = (Calendar) instance.clone();
            clone.add(Calendar.DAY_OF_MONTH, 1);
            endTime = df.format(clone.getTime());

            tv_startTime.setText(startTime);
            tv_endTime.setText(endTime);

            carNumber = Hawk.get(Api.CarNumberKey);
            tv_carNumber.setText(carNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carport_reserve);
        ButterKnife.bind(this);

        orderDao = new OrderDao(this);
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mTvTitle.setText("车位预约");
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();

        try {
            carPortInfo = (CarPortBean) intent.getSerializableExtra("carport_info");
        } catch (Exception e) {
            e.printStackTrace();
        }

        initView();
        initData();

        tvReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: 点击了预约...");
                Log.d(TAG, "onClick: 点击了预定...");
                if(TextUtils.isEmpty(carNumber)){
                    Toast.makeText(CarPortReserveActivity.this, "请输入车牌号码！", Toast.LENGTH_SHORT).show();
                    return;
                }
                OrderBean orderBean = new OrderBean(carPortInfo.getTitle(),carPortInfo.getAddr(),
                        carPortInfo.getLat()+"",carPortInfo.getLng()+"",carNumber,startTime,endTime);
                long rowsId = orderDao.add(orderBean);
                if(rowsId>0){
                    Toast.makeText(CarPortReserveActivity.this, "创建预定单成功！", Toast.LENGTH_SHORT).show();
                    intent.setClass(CarPortReserveActivity.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(CarPortReserveActivity.this, "创建预定单失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @OnClick({R.id.tvReserve, R.id.tv_startTime, R.id.tv_endTime})
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
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


    private void initView() {
        if (carPortInfo != null) {
            tvCarPortTitle.setText(carPortInfo.getTitle());
            tvAddr.setText(carPortInfo.getAddr());
            ivNav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    double lat = carPortInfo.getLat();
                    double lng = carPortInfo.getLng();
                    Log.d(TAG, "onClick: 导航 == " + lat + "," + lng);
                }
            });
            tvReserve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }
}