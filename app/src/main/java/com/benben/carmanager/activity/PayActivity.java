package com.benben.carmanager.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.benben.carmanager.R;
import com.benben.carmanager.adapter.PayAdapter;
import com.benben.carmanager.dao.OrderDao;
import com.benben.carmanager.listener.IDialogBtnClickCallBack;
import com.benben.carmanager.utils.DialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayActivity extends AppCompatActivity {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_back)
    ImageView mIvBack;

    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.btn_pay)
    View btnPay;
    @BindView(R.id.iv_zfb)
    ImageView ivZfb;
    @BindView(R.id.iv_wx)
    ImageView ivWx;

    private String price;

    private boolean isSelectZFB = true;
    private OrderDao orderDao;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        orderDao = new OrderDao(this);
        mTvTitle.setText("支付");
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        price = getIntent().getStringExtra("price");
        id = getIntent().getStringExtra("id");
        tvPrice.setText(price + " rmb");

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showDialog(PayActivity.this, "确认支付该订单吗？", "您的停车位总共 " + price + " 元，确认支付吗？", "支付"
                        , "取消", new IDialogBtnClickCallBack() {
                            @Override
                            public void onPositiveButtonClicked() {
                                int rows = orderDao.update(id, "1");
                                if (rows>0){
                                    Toast.makeText(PayActivity.this, "您已成功支付 " +price + " 元，欢迎您下次光临！", Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                                }

                                setResult(Activity.RESULT_OK);
                                finish();

                            }

                            @Override
                            public void onNegativeButtonClicked() {

                            }
                        });
            }
        });
        ((View) ivZfb.getParent()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelectZFB = true;
                refreshPay();
            }
        });
        ((View) ivWx.getParent()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelectZFB = false;
                refreshPay();
            }
        });

        refreshPay();


    }

    private void refreshPay() {
        if (isSelectZFB){
            ivZfb.setSelected(true);
            ivWx.setSelected(false);
        }else {
            ivZfb.setSelected(false);
            ivWx.setSelected(true);
        }
    }
}