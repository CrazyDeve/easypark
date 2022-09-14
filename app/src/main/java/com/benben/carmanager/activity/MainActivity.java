package com.benben.carmanager.activity;

import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.benben.carmanager.MyApplication;
import com.benben.carmanager.R;
import com.benben.carmanager.bean.MessageEvent;
import com.benben.carmanager.dao.ScoreDao;
import com.benben.carmanager.dao.SettingDao;
import com.benben.carmanager.dao.UserDao;
import com.benben.carmanager.fragment.HomeFragment;
import com.benben.carmanager.model.User;
import com.benben.carmanager.utils.Api;
import com.benben.carmanager.utils.DialogUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.navigation.NavigationView;
import com.orhanobut.hawk.Hawk;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;
    private List<Fragment> fragments;
    //当前主页显示的Fragment
    private Fragment mCurrentFragment;
    private FragmentManager fragmentManager;

    private TextView wordType;

    private TextView mTvSelect;
    private TextView mTvWordType;

    private UserDao userDao;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: 被执行了");
    }
    
    
    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        //取消bottomNavigation的图标覆盖颜色
        bottomNavigation.setItemIconTintList(null);
        //设置bottomNavigation的字体显示模式 大于3个也是全部显示
        bottomNavigation.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        //设置底部导航标签的点击监听
        bottomNavigation.setOnNavigationItemSelectedListener(mNavigationListener);



        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch(menuItem.getItemId()){
                    case R.id.loginout:
                        Hawk.put("userToken","");
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.updatePass:
                        intent = new Intent(MainActivity.this, RegisterActivity.class);
                        intent.putExtra("type","1");
                        startActivity(intent);
                        break;
                    case R.id.car_mgr:
                        intent = new Intent(MainActivity.this, CarManagerActivity.class);
                        startActivity(intent);
                        break;

                }
                return true;
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);

        wordType = findViewById(R.id.wordType);

        mTvSelect = findViewById(R.id.select);
        mTvWordType = findViewById(R.id.wordType);
        mTvSelect.setOnClickListener(this);
        mTvWordType.setOnClickListener(this);

        initApp();



        //查询用户并创建分数
        userDao = new UserDao(this);


    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select:
            case R.id.wordType:
                DialogUtils.showEditSubCateDialog(this,"选择难度","",
                        "确定","取消",new DialogUtils.ISubCateDialogClickListener(){
                            @Override
                            public void onPositiveButtonClicked() {
                                Log.d(TAG, "onPositiveButtonClicked: 确定...");

                                Fragment fragment = fragments.get(0);
                                if(fragment instanceof HomeFragment){
                                    HomeFragment homeFragment = (HomeFragment) fragment;
                                    homeFragment.initData();
                                }

                            }

                            @Override
                            public void onNegativeButtonClicked() {
                                Log.d(TAG, "onNegativeButtonClicked: 取消...");


                            }
                        });
                break;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: EventBus...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
    

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
        if(event!=null){
            switch (event.getType()){
                case "login_success":


                    Hawk.put(Api.USER_TOKEN_KEY, Api.TOKEN);
                    break;
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.d(TAG, "onDestroy: EventBus...");

    }

    /**
     * 初始化app
     */
    private void initApp() {

        fragmentManager = getSupportFragmentManager();

        Application application = getApplication();
        if(application instanceof MyApplication){
            MyApplication app = (MyApplication) application;
            fragments = app.getFragments();
        }

        if(fragments!=null && fragments.size()>0){
            Fragment mHomeFragment = fragments.get(0);
            mCurrentFragment = mHomeFragment;

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if(!mCurrentFragment.isAdded()){
                transaction.add(R.id.fl_content, mCurrentFragment);
            }else{
                transaction.show(mCurrentFragment);
            }

            transaction.commit();

        }


    }


    @Override
    protected void initData() {
        super.initData();
        Log.d(TAG, "initData: MainActivity 数据初始化了");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged:  === "+newConfig.getLayoutDirection());
    }

    /**
     * 选择或切换页面显示
     * @param fromFragment
     * @param toFragment
     */
    private boolean switchFragment(Fragment fromFragment, Fragment toFragment) {
        if(fromFragment!=toFragment){
            if(fragmentManager==null){
                return false;
            }

            if(toFragment==null){
                return false;
            }

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if(!toFragment.isAdded()){
                transaction.add(R.id.fl_content, toFragment);
            }else{
                transaction.show(toFragment);
            }

            if(fromFragment!=null){
                transaction.hide(fromFragment);
            }

            transaction.commit();
            mCurrentFragment = toFragment;
            return true;

        }

        return false;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mNavigationListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment toFragment = null;

            switch (item.getItemId()) {
                case R.id.menu_diancan:

                    if(fragments!=null){
                        toFragment = fragments.get(0);
                    }
                    break;

                case R.id.menu_order:

                    toFragment = fragments.get(1);
                    break;



//                case R.id.menu_me:
//                    Log.d(TAG, "onNavigationItemSelected: 我");
//                    toFragment = fragments.get(3);
//                    break;
            }

            boolean success = switchFragment(mCurrentFragment, toFragment);

            return success;
        }
    };


}
