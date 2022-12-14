package com.benben.carmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.benben.carmanager.R;
import com.benben.carmanager.dao.UserDao;
import com.benben.carmanager.model.User;
import com.benben.carmanager.utils.Utils;
import com.orhanobut.hawk.Hawk;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String USER_INFO = "userInfo";
    private EditText etPhone,etPass,etPass2,etNickName,etId,etName;
    private TextView tvRegister,mTvTag;
    private Toast toast;
    private UserDao userDao;
    private String type;
    private ImageView ivBack;
    private TextView mOldPassDesc;
    private User mUsers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        type = getIntent().getStringExtra("type");
        toast = Toast.makeText(this,"", Toast.LENGTH_SHORT);
        mTvTag = findViewById(R.id.tag1);
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        etPhone = findViewById(R.id.et_phone);
        etPass = findViewById(R.id.et_pass);
        etPass2 = findViewById(R.id.et_pass2);
        etNickName = findViewById(R.id.et_nickName);
        etName = findViewById(R.id.et_name);
        etId = findViewById(R.id.et_id);
        tvRegister = findViewById(R.id.tv_register);
        mOldPassDesc = findViewById(R.id.old_pass_desc);
        tvRegister.setOnClickListener(this);

        MyTextWatcher watcher = new MyTextWatcher();
        etPhone.addTextChangedListener(watcher);
        etPass.addTextChangedListener(watcher);
        etPass2.addTextChangedListener(watcher);
        etName.setFilters(new InputFilter[]{inputFilter});

        userDao = new UserDao(this);

        if("1".equals(type)){
            mTvTag.setText("????????????");
            tvRegister.setText("??????");
            mOldPassDesc.setText("?????????");
            etPass.setHint("??????????????????");
            mUsers = Hawk.get("user");
            mUsers = userDao.find(mUsers.get_id());
            if(!TextUtils.isEmpty(mUsers.getUsername())){

                if(TextUtils.isEmpty(mUsers.getUsername())){
                    return;
                }
                if(TextUtils.isEmpty(mUsers.getPassword())){
                    return;
                }

                etPhone.setText(mUsers.getUsername());
                etNickName.setText(mUsers.getNickName());

            }
        }else {
            mOldPassDesc.setText("??????");
            etPass.setHint("???????????????");
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tv_register:
                String phone = etPhone.getText().toString().trim();
                String pass = etPass.getText().toString().trim();
                String pass2 = etPass2.getText().toString().trim();
                String nickName = etNickName.getText().toString().trim();
                String idNumber = etId.getText().toString().trim();
                String name = etName.getText().toString().trim();
                if(TextUtils.isEmpty(nickName)){
                    showToast(getString(R.string.???????????????));
                    return;
                }

                if(TextUtils.isEmpty(name)){
                    showToast(getString(R.string.???????????????));
                    return;
                }

                if(TextUtils.isEmpty(phone)){
                    showToast(getString(R.string.??????????????????));
                    return;
                }

                if (!Utils.isPhoneNumber(phone)){
                    showToast("???????????????????????????");
                    return;
                }
                if (!Utils.isIDCardNum(idNumber)){
                    showToast("??????????????????????????????");
                    return;
                }

                if ("1".equals(type)){
                    if(!TextUtils.isEmpty(pass)&&!TextUtils.isEmpty(pass2)){
                        if(pass.equals(pass2)){
                            //??????????????????????????????
                            showToast("?????????????????????????????????");
                            return;
                        }

                        if (!(name.equals(mUsers.getUsername()))){
                            //??????????????????????????????
                            showToast("???????????????????????????");
                            return;
                        }
                        if (!(idNumber.equals(mUsers.getIdNumber()))){
                            //??????????????????????????????
                            showToast("????????????????????????????????????");
                            return;
                        }
                        if (!pass.equals(mUsers.getPassword())){
                            //??????????????????????????????
                            showToast("????????????????????????");
                            return;
                        }
                    }else{
                        showToast(getString(R.string.???????????????));
                        return;
                    }
                }else {
                    if(!TextUtils.isEmpty(pass)&&!TextUtils.isEmpty(pass2)){
                        if(!pass.equals(pass2)){
                            //??????????????????????????????
                            showToast(getString(R.string.??????????????????));
                            return;
                        }
                    }else{
                        showToast(getString(R.string.???????????????));
                        return;
                    }
                }



                if("1".equals(type)){
                    requestUpdatePass(mUsers.get_id(),phone,pass,pass2);
                }else{
                    requestRegister(phone,pass,pass2,nickName,idNumber,name);
                }


                break;
        }
    }

    private void requestRegister(String phone, String pass, String pass2, String nickName, String idNumber, String name) {
        User user = new User(phone, pass,nickName,idNumber,name);
        User exitsBean = userDao.findByName(user);
        if(exitsBean!=null){
            showToast(getString(R.string.??????????????????));
            return;
        }

        long rowId = userDao.add(user);
        if(rowId>0){
            showToast(getString(R.string.????????????));
            finish();
        }else{
            showToast(getString(R.string.????????????));
        }

    }

    InputFilter inputFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                if (!isChinese(source.charAt(i))) {
                    showToast("??????????????????");
                    return "";
                }
            }
            return null;
        }
    };



    private boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        // 4E00-9FBF???CJK ??????????????????
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                //F900-FAFF???CJK ??????????????????
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                //3400-4DBF???CJK ???????????????????????? A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                //2000-206F???????????????
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                //3000-303F???CJK ???????????????
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                //FF00-FFEF????????????????????????
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * ????????????
     * @param phone
     * @param pass
     * @param pass2
     */
    private void requestRegister(String phone, String pass, String pass2) {

//
//
        User user = new User(phone, pass);
        User exitsBean = userDao.findByName(user);
        if(exitsBean!=null){
            showToast(getString(R.string.??????????????????));
            return;
        }

        long rowId = userDao.add(user);
        if(rowId>0){
            showToast(getString(R.string.????????????));
            finish();
        }else{
            showToast(getString(R.string.????????????));
        }
    }

    /**
     * ????????????
     * @param phone
     * @param pass
     * @param pass2
     */
    private void requestUpdatePass(int id,String phone, String pass, String pass2) {

//
//

        User user = new User(id,phone, pass2);
        User exitsBean = userDao.findByName(user);
        if(exitsBean==null){
            showToast(getString(R.string.???????????????));
            return;
        }

        user.set_id(exitsBean.get_id());
        long rowId = userDao.update(user);
        if("1".equals(type)){
            if(rowId>0){
                showToast(getString(R.string.??????????????????));
                Intent intent = new Intent();
                intent.setClass(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }else{
                showToast(getString(R.string.??????????????????));
            }
        }else{
            if(rowId>0){
                showToast(getString(R.string.????????????));
                finish();
            }else{
                showToast(getString(R.string.????????????));
            }
        }

    }




    /**
     * ???????????????????????????
     */
    class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String phone = etPhone.getText().toString().trim();
            String pass = etPass.getText().toString().trim();
            String pass2 = etPass2.getText().toString().trim();

            if(!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(pass)&&!TextUtils.isEmpty(pass2)  && phone.length()>=11 && pass.length()>=6){
                tvRegister.setSelected(true);
                tvRegister.setEnabled(true);
            }else{
                tvRegister.setSelected(false);
                tvRegister.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    protected void showToast(String msg){


        if(toast!=null){
            toast.setText(msg);
            toast.setGravity(Gravity.BOTTOM,0,0);
            toast.show();
        }
    }

}
