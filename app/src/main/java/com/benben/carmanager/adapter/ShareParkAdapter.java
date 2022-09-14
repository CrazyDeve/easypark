package com.benben.carmanager.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.benben.carmanager.R;
import com.benben.carmanager.bean.CarPortBean;
import com.benben.carmanager.bean.ShareParkBean;

import java.util.List;

public class ShareParkAdapter extends BaseRvAdapter<ShareParkBean> {
    private static final String TAG = "jyw";

    private OnItemClickListener mListener;


    public ShareParkAdapter(Context context) {
        super(context);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (data != null && data.size() > 0) {
            ShareParkBean parkBean = data.get(position);
            TextView tvTitle = holder.getView(R.id.tv_title);
            TextView tvAddress = holder.getView(R.id.tv_addr);
            TextView tvPrice = holder.getView(R.id.tv_price);
            TextView tvstart = holder.getView(R.id.tv_start);
            TextView tvend = holder.getView(R.id.tv_end);
            TextView tvDel = holder.getView(R.id.tv_delete);
            tvTitle.setText(parkBean.getParkName());
            tvAddress.setText(parkBean.getParkAddr());
            tvPrice.setText(parkBean.getPrice()+"元/小时");
            tvstart.setText("开始时间："+parkBean.getStartTime());
            tvend.setText("结束时间："+parkBean.getEndTime());

            tvDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener!=null){
                        mListener.onItemClicked(parkBean);
                    }
                }
            });
        }
    }



    @Override
    protected int getLayoutResourceId(int viewType) {
        return R.layout.sharepark_item;
    }


    @Override
    public void setData(List<ShareParkBean> data) {
        super.setData(data);
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }




    public interface OnItemClickListener {
        void onItemClicked(ShareParkBean parkBean);
    }
}
