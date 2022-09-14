package com.benben.carmanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.benben.carmanager.R;
import com.benben.carmanager.adapter.CarPortAdapter;
import com.benben.carmanager.bean.CarPortBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarPortDetailActivity extends AppCompatActivity  {

    private static final String TAG = "SearchCarPortActivity";
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.map)
    MapView mMap;
    @BindView(R.id.tv_reserve)
    TextView tvReserve;
    @BindView(R.id.tv_carport_title)
    TextView tvCarPortTitle;
    @BindView(R.id.tv_addr)
    TextView tvAddr;
    @BindView(R.id.iv_nav)
    ImageView ivNav;

    private AMap map;
    private CarPortBean carPortInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carport_detail);
        ButterKnife.bind(this);
        mMap.onCreate(savedInstanceState);
        map = mMap.getMap();
        mTvTitle.setText("车位详情");
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
    }




    @OnClick({R.id.tv_reserve})
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.tv_reserve:
                intent.setClass(CarPortDetailActivity.this,CarPortReserveActivity.class);
                intent.putExtra("carport_info",carPortInfo);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMap.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMap.onDestroy();
    }

    private void initView() {
        if(carPortInfo!=null){

            LatLng latLng = new LatLng(carPortInfo.getLat(),carPortInfo.getLng());
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(latLng);
            markerOption.title(carPortInfo.getTitle()).snippet(carPortInfo.getAddr());

            markerOption.draggable(true);//设置Marker可拖动
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(),R.mipmap.icon_map_carport)));
            // 将Marker设置为贴地显示，可以双指下拉地图查看效果
            markerOption.setFlat(true);//设置marker平贴地图效果

            map.addMarker(new MarkerOptions().position(latLng).title(carPortInfo.getTitle()).snippet(carPortInfo.getAddr()));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.5f), 2000, null);

            tvCarPortTitle.setText(carPortInfo.getTitle());
            tvAddr.setText(carPortInfo.getAddr());
            ivNav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    double lat = carPortInfo.getLat();
                    double lng = carPortInfo.getLng();
                    Log.d(TAG, "onClick: 导航 == "+lat+","+lng);
                    Uri uri = Uri.parse("amapuri://route/plan/?dlat="+lat+"&dlon="+lng+"&dname="+carPortInfo.getTitle()+"&dev=0&t=0");
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));

                }
            });
            tvReserve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CarPortDetailActivity.this,CarPortReserveActivity.class);
                    intent.putExtra("carport_info",carPortInfo);
                    startActivity(intent);
                }
            });
        }

    }
}