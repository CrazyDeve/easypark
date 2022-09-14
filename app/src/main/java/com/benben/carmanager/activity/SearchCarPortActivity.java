package com.benben.carmanager.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.benben.carmanager.R;
import com.benben.carmanager.adapter.CarPortAdapter;
import com.benben.carmanager.bean.CarPortBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchCarPortActivity extends AppCompatActivity implements PoiSearch.OnPoiSearchListener {

    private static final String TAG = "SearchCarPortActivity";
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.map)
    MapView mMap;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.rv_carport)
    RecyclerView mRvCarPort;


    private AMap map;
    private PoiSearch.Query query;
    private PoiSearch poiSearch;

    private List<CarPortBean> mAllCarPorts;
    private CarPortAdapter carPortAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_car_port);
        ButterKnife.bind(this);
        mMap.onCreate(savedInstanceState);
        mRvCarPort.setVisibility(View.GONE);
        mTvTitle.setText("找车位");
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        initView();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int index) {
        Log.d(TAG, "onPoiSearched: "+poiResult.getPois().toString());
        mAllCarPorts = new ArrayList<>();
        ArrayList<PoiItem> pois = poiResult.getPois();
        if(pois!=null && pois.size()>0){
            for (int i=0;i<pois.size();i++){
                PoiItem poiItem = pois.get(i);
                Log.d(TAG, "onPoiSearched: 搜索到的结果信息 == "+poiItem);
                String title = poiItem.getTitle();
                String address = poiItem.getCityName() + poiItem.getAdName() + poiItem.getSnippet();
                LatLonPoint latLonPoint = poiItem.getLatLonPoint();
                CarPortBean carPortBean = new CarPortBean(title,address,latLonPoint.getLatitude(),latLonPoint.getLongitude(),poiItem.getCityName(),poiItem.getAdName());
                mAllCarPorts.add(carPortBean);
            }

            mRvCarPort.setVisibility(View.VISIBLE);
            carPortAdapter.setData(mAllCarPorts);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
        Log.d(TAG, "onPoiItemSearched: ");
    }

    @Override
    protected void onResume() {
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


    @OnClick({R.id.tv_search})
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.tv_search:
//                Toast.makeText(this, "搜素...", Toast.LENGTH_SHORT).show();
                String keyword = etSearch.getText().toString().trim();
                if(TextUtils.isEmpty(keyword)){
                    return;
                }
                query = new PoiSearch.Query(keyword, "150900", "");
//keyWord表示搜索字符串，
//第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
//cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
                query.setPageSize(10);// 设置每页最多返回多少条poiitem
                query.setPageNum(1);//设置查询页码
                poiSearch = new PoiSearch(this, query);
                poiSearch.setOnPoiSearchListener(this);
                poiSearch.searchPOIAsyn();
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

        mRvCarPort.setLayoutManager(new LinearLayoutManager(this));
        carPortAdapter = new CarPortAdapter(this);
        mRvCarPort.setAdapter(carPortAdapter);
        carPortAdapter.setOnItemClickListener(new CarPortAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(CarPortBean carPortBean) {
                Intent intent = new Intent(SearchCarPortActivity.this,CarPortDetailActivity.class);
                intent.putExtra("carport_info",carPortBean);
                startActivity(intent);
            }
        });
    }
}