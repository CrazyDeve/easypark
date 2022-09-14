package com.benben.carmanager.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.benben.carmanager.R;
import com.benben.carmanager.adapter.MyViewPagerAdapter;
import com.benben.carmanager.utils.AuthUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    List<ImageView> list;
    private TextView tvStart;

    @Override
    protected void initView() {
        fullScreen(this, false);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        tvStart = findViewById(R.id.tvStart);
        list = new ArrayList<ImageView>();
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.guideone);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        list.add(imageView);
        ImageView imageView1 = new ImageView(this);
        imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView1.setImageResource(R.mipmap.guidetwo);
        list.add(imageView1);

        ImageView imageView2 = new ImageView(this);
        imageView2.setImageResource(R.mipmap.guidethree);
        imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        list.add(imageView2);
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(list);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==2){
                    tvStart.setVisibility(View.VISIBLE);
                    tvStart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(!AuthUtils.hasLogin()){
                                Intent intent = new Intent(GuideActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(GuideActivity.this,MainActivity.class);
                                startActivity(intent);
                            }

                            finish();
                        }
                    });
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
    }
}
