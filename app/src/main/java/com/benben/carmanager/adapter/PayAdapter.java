package com.benben.carmanager.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.benben.carmanager.R;
import com.benben.carmanager.bean.OrderBean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PayAdapter extends BaseRvAdapter<OrderBean> {
    private static final String TAG = "jyw";
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private OnItemClickListener mListener;


    public PayAdapter(Context context) {
        super(context);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (data != null && data.size() > 0) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        mListener.onItemClicked(data.get(position));
                    }
                }
            });
        }
    }



    @Override
    protected int getLayoutResourceId(int viewType) {
        return R.layout.pay_layout;
    }


    @Override
    public void setData(List<OrderBean> data) {
        super.setData(data);
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }




    public interface OnItemClickListener {
        void onItemClicked(OrderBean carPortBean);
        void onNav(OrderBean carPortBean);
    }
}
