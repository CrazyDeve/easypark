package com.benben.carmanager.fragment;


import android.app.Activity;
import android.content.Intent;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.benben.carmanager.R;
import com.benben.carmanager.activity.AboutActivity;
import com.benben.carmanager.activity.AddCarActivity;
import com.benben.carmanager.activity.AddShareParkActivity;
import com.benben.carmanager.activity.LoginActivity;
import com.benben.carmanager.activity.MainActivity;
import com.benben.carmanager.activity.OrderActivity;
import com.benben.carmanager.activity.SearchCarPortActivity;
import com.benben.carmanager.activity.ShareCarPortActivity;
import com.benben.carmanager.activity.UserBaseInfoActivity;

import com.benben.carmanager.dao.UserDao;
import com.benben.carmanager.model.User;
import com.benben.carmanager.utils.AuthUtils;

import com.orhanobut.hawk.Hawk;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment {
    private static final String TAG = "jyw";
    private static final int REQUEST_USER_LOGIN = 0x10;
    private static final int REQUESTCODE_SHOULD_REFRESHPAGE = 0x12;//需要刷新本页面的请求码


    private static MineFragment mineFragment;

    @BindView(R.id.rl_yuyue)
    LinearLayout rl_yuyue;
    @BindView(R.id.rl_stopcarNote)
    LinearLayout rl_stopcarNote;
    @BindView(R.id.rl_mysend)
    LinearLayout rl_mysend;
    @BindView(R.id.ll_helper)
    LinearLayout ll_helper;
    @BindView(R.id.ll_setting)
    LinearLayout ll_setting;
    @BindView(R.id.rl_goodNote)
    LinearLayout rlGoodNote;
    @BindView(R.id.rl_payout)
    LinearLayout rlPayout;

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tv_shopName)
    TextView tvShopName;
    @BindView(R.id.tv_shopPhone)
    TextView tvShopPhone;
    @BindView(R.id.ll_userInfo)
    LinearLayout llUserInfo;
    @BindView(R.id.tv_loginOut)
    TextView tvLoginOut;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.ll_mycar)
    LinearLayout ll_mycar;
    @BindView(R.id.ll_order)
    LinearLayout ll_order;
    @BindView(R.id.ll_mypackage)
    LinearLayout llMypackage;


    private Handler mHandler;
    private User mUsers;
    private UserDao userDao;


    public MineFragment() {
        // Required empty public constructor
        //查询用户并创建分数

    }

    public static MineFragment newInstance() {
        if (mineFragment == null) {
            mineFragment = new MineFragment();
        }
        return mineFragment;
    }


    @Override
    protected View initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_mine, null);
        ButterKnife.bind(this, view);

        mHandler = new Handler(Looper.getMainLooper());
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "onRefresh: 刷新控件刷新了...");
                initData();

            }
        });


        if (AuthUtils.hasLogin()) {
            tvLoginOut.setVisibility(View.VISIBLE);
        } else {
            tvLoginOut.setVisibility(View.GONE);
        }

        userDao = new UserDao(getActivity());

        return view;
    }

    @OnClick({R.id.rl_yuyue, R.id.ll_mycar, R.id.ll_mypackage, R.id.ll_order, R.id.rl_stopcarNote, R.id.rl_mysend, R.id.ll_helper, R.id.ll_setting, R.id.rl_goodNote, R.id.rl_payout, R.id.ll_userInfo, R.id.tv_loginOut})
    public void onClick(View view) {

        //判断用户登录
        Intent intent = new Intent();
        if (!AuthUtils.hasLogin()) {
            intent = new Intent(context, LoginActivity.class);
            startActivityForResult(intent, REQUEST_USER_LOGIN);
            return;
        }


        switch (view.getId()) {
            case R.id.ll_setting:
            case R.id.ll_userInfo:
                if (AuthUtils.hasLogin()) {

                    intent.setClass(context, UserBaseInfoActivity.class);
                    startActivityForResult(intent, REQUESTCODE_SHOULD_REFRESHPAGE);
                } else {
                    intent.setClass(context, LoginActivity.class);
                    startActivity(intent);
                }

                break;

            case R.id.ll_mycar:
                intent.setClass(getContext(), AddCarActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_mypackage:
                Toast.makeText(getContext(), "暂未开启钱包功能", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_yuyue:
                intent.setClass(getContext(), SearchCarPortActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_mysend:
                intent.setClass(getContext(), ShareCarPortActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_stopcarNote:
            case R.id.ll_order:
                intent.setClass(getContext(), OrderActivity.class);
                startActivity(intent);
                break;

            case R.id.ll_helper:
                intent.setClass(getContext(), AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_payout:
                break;
//            case R.id.rl_setting:
//                break;
//            case R.id.rl_screen:
//                break;
            case R.id.tv_loginOut:

                requestLoginOut();
                break;
            case R.id.tv_protocol:


        }
    }

    private void requestLoginOut() {


        Hawk.put("userToken", "");

        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();


    }


    @Override
    protected void initData() {
        Log.d(TAG, "initData: MineFragment 初始化数据了...");
        requestUserInfo();
    }



    private void getUserInfo() {

        mUsers = Hawk.get("user");
        mUsers = userDao.find(mUsers.get_id());
        Log.d(TAG, "getUserInfo: 我得界面获取到的user = "+ mUsers.toString());
        if (!TextUtils.isEmpty(mUsers.getUsername())) {

            if (TextUtils.isEmpty(mUsers.getUsername())) {
                return;
            }
            if (TextUtils.isEmpty(mUsers.getPassword())) {
                return;
            }

            tvShopName.setText(mUsers.getNickName());
            tvShopPhone.setVisibility(View.GONE);
            tvLoginOut.setVisibility(View.VISIBLE);

        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 2000);


    }


    /**
     * 请求用户信息
     */
    private void requestUserInfo() {

        getUserInfo();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_USER_LOGIN:
                case REQUESTCODE_SHOULD_REFRESHPAGE:
                    //需要刷新本页面
                    refreshLayout.measure(0, 0);
                    refreshLayout.setRefreshing(true);
                    Log.d(TAG, "onActivityResult: 正在刷新....");
                    initData();
                    break;
            }
        }
    }
}
