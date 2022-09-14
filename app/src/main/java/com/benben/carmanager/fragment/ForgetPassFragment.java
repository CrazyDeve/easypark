package com.benben.carmanager.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.benben.carmanager.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgetPassFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.tv_internationalCode)
    TextView tvInternationalCode;
    @BindView(R.id.et_phone)
    TextInputEditText etPhone;
    @BindView(R.id.tl_phone)
    TextInputLayout tlPhone;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.et_validate)
    TextInputEditText etValidate;
    @BindView(R.id.tl_validate)
    TextInputLayout tlValidate;
    @BindView(R.id.line3)
    View line3;
    @BindView(R.id.et_pass)
    TextInputEditText etPass;
    @BindView(R.id.tl_pass)
    TextInputLayout tlPass;
    @BindView(R.id.iv_showPassword)
    ImageView ivShowPassword;
    @BindView(R.id.line4)
    View line4;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_forget_password, null);
        ButterKnife.bind(this, view);
        tvTitle.setText(getString(R.string.忘记密码));
        ivBack.setOnClickListener(this);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivBack:
                Navigation.findNavController(v).navigateUp();
                break;
        }
    }
}
