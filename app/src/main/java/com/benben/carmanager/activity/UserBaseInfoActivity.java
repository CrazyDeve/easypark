package com.benben.carmanager.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.content.FileProvider;

import com.benben.carmanager.MyApplication;
import com.benben.carmanager.R;
import com.benben.carmanager.bean.ResponseBean.DataBean.UserInfoBean.InfoBean;

import com.benben.carmanager.dao.UserDao;
import com.benben.carmanager.model.User;
import com.benben.carmanager.utils.Api;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.orhanobut.hawk.Hawk;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class UserBaseInfoActivity extends BaseActivity {
    private static final String TAG = "jyw";

    private static final int REQUEST_EDIT = 0x10;
    private static final int REQUEST_IMAGE = 0x103;
    private static final int REQUEST_VISITE_PHOTO = 0x104;
    private static final int REQUEST_PHOTO_CUT = 0x105;

    public static String photo_path = MyApplication.getContext().getExternalCacheDir() + File.separator + "餐厅助手" + File.separator + "photos";
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.line0)
    Guideline line0;
    @BindView(R.id.line1)
    Guideline line1;
    @BindView(R.id.line2)
    Guideline line2;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_shopName)
    TextView tvShopName;
    @BindView(R.id.tv_shopPhone)
    TextView tvShopPhone;
    @BindView(R.id.tv_userName)
    TextView tvUserName;
    @BindView(R.id.tv_shopdesc)
    TextView tvShopdesc;
    @BindView(R.id.tv_rightBtn)
    TextView tvRightBtn;
    @BindView(R.id.ll_update_pass)
    LinearLayout llShopName;
    @BindView(R.id.ll_update_nickName)
    LinearLayout llUpdateNickName;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.ll_desc)
    LinearLayout llDesc;
    @BindView(R.id.ll_icon)
    LinearLayout llIcon;
    private InfoBean infoBean;
    private UserDao userDao;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_user_base_info);
        ButterKnife.bind(this);
        tvTitle.setText(getString(R.string.基本信息));
        userDao = new UserDao(this);
        infoBean = new InfoBean();


        getUserInfo();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
    }

    /**
     * 获取基本信息
     */
    private void getUserInfo() {
        User user = Hawk.get("user");
        user = userDao.find(user.get_id());
        if (!TextUtils.isEmpty(user.getUsername())) {

            if (TextUtils.isEmpty(user.getUsername())) {
                return;
            }
            if (TextUtils.isEmpty(user.getPassword())) {
                return;
            }

            tvUserName.setText(user.getNickName());

        }
    }

    /**
     * 显示基本资料
     *
     * @param info
     */
    private void showBaseInfo(InfoBean info) {
        infoBean = info;
        String iconUrl = info.getIconUrl();
        String shopName = info.getShopName();
        String phone = info.getPhone();
        String desc = info.getDesc();
        if (isNoEmptyString(iconUrl)) {
            Log.d(TAG, "showBaseInfo: 图片路径：== " + Api.baseUrl + iconUrl);
            //加载圆形头像
            Glide.with(this)
                    .load(Api.baseUrl + iconUrl)
                    .placeholder(R.drawable.ic_user_placeholder)
                    .error(R.drawable.ic_user_placeholder)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(ivIcon);
        }
        tvShopName.setText(shopName);
        tvShopPhone.setText(phone);
        tvShopdesc.setText(desc);
    }


    @OnClick({R.id.ivBack, R.id.ll_icon, R.id.ll_update_pass, R.id.ll_phone, R.id.ll_desc, R.id.ll_update_nickName})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ivBack:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.ll_update_pass:
                intent = new Intent(this, RegisterActivity.class);
                intent.putExtra("type","1");
                startActivity(intent);
                break;
            case R.id.ll_update_nickName:
                intent = new Intent(this, UpdateNickNameActivity.class);
                startActivity(intent);
                break;


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_VISITE_PHOTO) {
            if (grantResults != null && grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    MultiImageSelector.create()
                            .showCamera(false)
                            .single()
                            .count(1)
                            .start(this, REQUEST_IMAGE);
                }
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_EDIT:
                    initData();
                    break;
                case Api.REQUEST_IMAGE_CAPTURE:
                    Log.d(TAG, "onActivityResult: 拍照返回..." + data);
                    File takePhotofile = new File(photo_path, "product.png");
                    requestUploadPhoto(Api.REQUEST_IMAGE_CAPTURE, takePhotofile);
                    break;

                case REQUEST_IMAGE:
                    //选择图片后的操作
                    // 获取返回的图片列表
                    List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    // 处理你自己的逻辑 ....
                    Log.d(TAG, "onActivityResult: 获取的图片 == " + path);

                    String phonePath = path.get(0);
                    if (!TextUtils.isEmpty(phonePath)) {
                        File photoFile = new File(phonePath);
                        requestUploadPhoto(REQUEST_IMAGE, photoFile);
                    }
                    break;
                case REQUEST_PHOTO_CUT:

                    break;

            }
        }
    }


    /**
     * 请求上传图片
     *
     * @param file
     */
    private void requestUploadPhoto(int actionType, File file) {

        if (file != null) {


        } else {
            showToast("上传图片失败");
        }

    }

    /**
     * 设置用户头像
     *
     * @param infoBean
     */
    private void setUserIcon(InfoBean infoBean) {
        String json = gson.toJson(infoBean);
        Log.d(TAG, "requestEditData: 修改基本信息 == " + json);
        Map<String, String> map = new HashMap<String, String>();
        map.put("userInfo", json);

    }

    @Override
    protected void initData() {
        super.initData();
        Log.d(TAG, "initData: UserBaseInfoActivity初始化数据了...");

        getUserInfo();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();

    }


    /**
     * 拍照上传图片
     */
    private void takePhoto() {
        File dir = new File(photo_path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File targetFile = new File(dir, "product.png");
        try {
            targetFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent takeCaptureIntent = new Intent();
        takeCaptureIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", targetFile);
        } else {
            uri = Uri.fromFile(targetFile);
        }
        takeCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        if (takeCaptureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeCaptureIntent, Api.REQUEST_IMAGE_CAPTURE);
        }
    }
}
