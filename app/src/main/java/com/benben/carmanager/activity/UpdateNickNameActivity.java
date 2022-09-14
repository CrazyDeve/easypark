package com.benben.carmanager.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.benben.carmanager.R;
import com.benben.carmanager.dao.UserDao;
import com.benben.carmanager.databinding.ActivityUpdateNickNameBinding;
import com.benben.carmanager.model.User;
import com.orhanobut.hawk.Hawk;

public class UpdateNickNameActivity extends AppCompatActivity {
    private ActivityUpdateNickNameBinding mBinding;
    private User mUsers;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_update_nick_name);
        userDao = new UserDao(this);
        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getUserInfo();
        mBinding.tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 提交
                String nickName = mBinding.etNickName.getText().toString().trim();
                if (TextUtils.isEmpty(nickName)){
                    Toast.makeText(UpdateNickNameActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = new User(mUsers.get_id(),mUsers.getUsername(),mUsers.getPassword(),nickName,mUsers.getIdNumber());
                int rows = userDao.update(user);
                if (rows>0){
                    Toast.makeText(UpdateNickNameActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(UpdateNickNameActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void getUserInfo() {
        mUsers = Hawk.get("user");
        if (!TextUtils.isEmpty(mUsers.getUsername())) {

            if (TextUtils.isEmpty(mUsers.getUsername())) {
                return;
            }
            if (TextUtils.isEmpty(mUsers.getPassword())) {
                return;
            }

            mBinding.etNickName.setText(mUsers.getNickName());

        }
    }
}