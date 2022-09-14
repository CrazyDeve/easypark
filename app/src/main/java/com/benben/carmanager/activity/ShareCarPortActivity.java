package com.benben.carmanager.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.benben.carmanager.R;
import com.benben.carmanager.adapter.CarPortAdapter;
import com.benben.carmanager.adapter.ShareParkAdapter;
import com.benben.carmanager.bean.CarPortBean;
import com.benben.carmanager.bean.ShareParkBean;
import com.benben.carmanager.dao.ShareParkDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareCarPortActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.rv_carpark)
    RecyclerView mRvPark;
    private ShareParkAdapter shareParkAdapter;
    private ShareParkDao parkDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_car_port);
        ButterKnife.bind(this);

        parkDao = new ShareParkDao(this);

        mTvTitle.setText("共享车位");
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        initView();
    }

    private void initView() {
        mRvPark.setLayoutManager(new LinearLayoutManager(this));
        shareParkAdapter = new ShareParkAdapter(this);
        shareParkAdapter.setOnItemClickListener(new ShareParkAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(ShareParkBean parkBean) {
                boolean delete = parkDao.delete(parkBean.getId());
                if (delete){
                    Toast.makeText(ShareCarPortActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    refreshData();
                }
            }
        });
        mRvPark.setAdapter(shareParkAdapter);

        refreshData();
    }

    private void refreshData() {
        List<ShareParkBean> all = parkDao.findAll();
        shareParkAdapter.setData(all);
    }

    @OnClick({R.id.iv_add})
    public void onClicked(View view){
        switch (view.getId()){
            case R.id.iv_add:
                Intent intent = new Intent(this,AddShareParkActivity.class);
                startActivityForResult(intent,100);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refreshData();
    }
}