package com.benben.carmanager.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.benben.carmanager.R;
import com.benben.carmanager.adapter.OrderAdapter;
import com.benben.carmanager.bean.CarPortBean;
import com.benben.carmanager.bean.OrderBean;
import com.benben.carmanager.dao.OrderDao;
import com.benben.carmanager.listener.IDialogBtnClickCallBack;
import com.benben.carmanager.utils.DialogUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderActivity extends AppCompatActivity  {

    private static final String TAG = "SearchCarPortActivity";
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_back)
    ImageView mIvBack;

    @BindView(R.id.rv_orders)
    RecyclerView mRvOrders;


    private List<OrderBean> mOrders;
    private OrderAdapter orderAdapter;
    private OrderDao orderDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);

        orderDao = new OrderDao(this);
        mTvTitle.setText("我的订单");
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        initView();
        initData();
    }

    private void initData() {
        List<OrderBean> all = orderDao.findAll();
        Log.d(TAG, "initData: 订单信息 = "+ new Gson().toJson(all));
        orderAdapter.setData(all);
    }


//    @OnClick({R.id.tv_search})
//    public void onClick(View v) {
//        Intent intent = new Intent();
//        switch (v.getId()) {
//            case R.id.tv_search:
//                break;
//        }
//    }



    private void initView() {

        mRvOrders.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(this);
        mRvOrders.setAdapter(orderAdapter);
        orderAdapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(OrderBean carPortBean) {
                if ("1".equals(carPortBean.getStatus())){
                    Toast.makeText(OrderActivity.this, "您已经支付过该订单了", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(OrderActivity.this,PayActivity.class);
                    intent.putExtra("id",String.valueOf(carPortBean.getId()));
                    intent.putExtra("price",carPortBean.getPrice());
                    startActivityForResult(intent,1);
                }


            }

            @Override
            public void onNav(OrderBean carPortBean) {
                double lat = Double.parseDouble(carPortBean.getLat());
                double lng = Double.parseDouble(carPortBean.getLng());
                Log.d(TAG, "onClick: 导航 == "+lat+","+lng);
                Uri uri = Uri.parse("amapuri://route/plan/?dlat="+lat+"&dlon="+lng+"&dname="+carPortBean.getParkName()+"&dev=0&t=0");
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            initData();
        }
    }
}