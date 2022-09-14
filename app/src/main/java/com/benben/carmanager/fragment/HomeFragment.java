package com.benben.carmanager.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.benben.carmanager.R;
import com.benben.carmanager.activity.AboutActivity;
import com.benben.carmanager.activity.AddCarActivity;
import com.benben.carmanager.activity.MainActivity;
import com.benben.carmanager.activity.MemoryWordMainActivity;
import com.benben.carmanager.activity.SearchCarPortActivity;
import com.benben.carmanager.activity.ShareCarPortActivity;
import com.benben.carmanager.activity.WordReviewActivity;
import com.benben.carmanager.dao.SettingDao;
import com.benben.carmanager.dao.StudyRecordDao;
import com.benben.carmanager.dao.WordDao;
import com.benben.carmanager.dao.WordRecordDao;
import com.benben.carmanager.listener.IDialogBtnClickCallBack;
import com.benben.carmanager.model.DataBean;
import com.benben.carmanager.model.StudyRecord;
import com.benben.carmanager.utils.Api;
import com.benben.carmanager.utils.DialogUtils;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "HomeFragment";

    private Context context;
    private SettingDao settingDao;
    private TextView mTvStartTime, mTvEndTime, mBtnStart;

    private Banner mBanner;
    private TimePickerView pvTime;
    private LinearLayout llStart;
    private LinearLayout llEnd;
    private DateFormat df;
    private String defStartTime;
    private String tomorowTime;

    @BindView(R.id.map)
    MapView mMap;
    @BindView(R.id.add_Car)
    TextView mAddCar;
    @BindView(R.id.ll_search_p)
    LinearLayout ll_search_p;
    @BindView(R.id.ll_share_p)
    LinearLayout ll_share_p;
    @BindView(R.id.ll_about)
    LinearLayout ll_about;

    Bundle savedInstanceState;
    private AMap map;

    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        this.savedInstanceState = savedInstanceState;
        df = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, view);

        mMap.onCreate(this.savedInstanceState);
        mTvStartTime = view.findViewById(R.id.tv_startTime);

        mTvEndTime = view.findViewById(R.id.tv_endTime);


        mBanner = view.findViewById(R.id.banner);
        mBanner.setAdapter(new BannerImageAdapter<DataBean>(DataBean.getTestData()) {
            @Override
            public void onBindView(BannerImageHolder holder, DataBean data, int position, int size) {
                //图片加载自己实现
                Glide.with(holder.itemView)
                        .load(data.imageUrl)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(holder.imageView);
            }
        })
                .addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(getContext()));

        initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMap.onResume();

        map = mMap.getMap();
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(60000*60*24); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        map.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        map.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
    }

    @Override
    public void onPause() {
        super.onPause();
        mMap.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMap.onDestroy();
    }

    @Override
    protected View initView() {
        return null;
    }

    public void initData() {

        Calendar instance = Calendar.getInstance();
        Date time = instance.getTime();
        defStartTime = df.format(time);
        Calendar clone = (Calendar) instance.clone();
        clone.add(Calendar.DAY_OF_MONTH, 1);
        tomorowTime = df.format(clone.getTime());


    }


    @OnClick({R.id.add_Car,R.id.ll_search_p,R.id.ll_share_p,R.id.ll_about})
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.add_Car:
                intent.setClass(getContext(), AddCarActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_search_p:
                intent.setClass(getContext(), SearchCarPortActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_share_p:
                intent.setClass(getContext(), ShareCarPortActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_about:
                intent.setClass(getContext(), AboutActivity.class);
                startActivity(intent);
                break;

        }
    }




}
