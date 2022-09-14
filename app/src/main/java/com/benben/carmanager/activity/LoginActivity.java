package com.benben.carmanager.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.benben.carmanager.dao.UserDao;

import com.benben.carmanager.R;
import com.benben.carmanager.model.User;
import com.orhanobut.hawk.Hawk;

public class LoginActivity extends BaseActivity implements  View.OnClickListener{

    public static final String USER_INFO = "userInfo";


    private UserDao userDao;
    private TextView mTvRegister, mTvLogin;
    private EditText etPhone, etPass;
    private Toast toast;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_login);
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        etPhone = findViewById(R.id.et_phone);
        etPass = findViewById(R.id.et_pass);

        mTvRegister = findViewById(R.id.tv_register);
        mTvLogin = findViewById(R.id.tv_login);
        mTvRegister.setOnClickListener(this);
        mTvLogin.setOnClickListener(this);

        MyTextWatcher watcher = new MyTextWatcher();
        etPhone.addTextChangedListener(watcher);
        etPass.addTextChangedListener(watcher);

        userDao = new UserDao(this);


    }

    @Override
    protected void initData() {
        super.initData();

    }



    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.tv_register:
                intent.setClass(this, RegisterActivity.class);
                intent.putExtra("type","0");
                startActivity(intent);
                break;
            case R.id.tv_login:
                String phone = etPhone.getText().toString().trim();
                String pass = etPass.getText().toString().trim();

                if(TextUtils.isEmpty(phone)){
                    showToast(getString(R.string.请输入手机号));
                    return;
                }

                if(TextUtils.isEmpty(pass)){
                    showToast(getString(R.string.请输入密码));
                    return;
                }

                login(phone,pass);
                break;
        }


    }

    private void login(String phone, String pass) {
        User user = new User(phone, pass);
        User login = userDao.login(user);
        if(login!=null){
            Hawk.put("user",login);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            showToast(getString(R.string.账户名不存在或密码错误));
        }
    }

    protected void showToast(String msg){


        if(toast!=null){
            toast.setText(msg);
            toast.setGravity(Gravity.BOTTOM,0,0);
            toast.show();
        }
    }


    /**
     * 监听输入文字的状态
     */
    class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String phone = etPhone.getText().toString().trim();
            String pass = etPass.getText().toString().trim();

            if(!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(pass)  && phone.length()>=11 && pass.length()>=6){
                mTvLogin.setSelected(true);
                mTvLogin.setEnabled(true);
            }else{
                mTvLogin.setSelected(false);
                mTvLogin.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
