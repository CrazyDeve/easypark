package com.benben.carmanager.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.benben.carmanager.R;
import com.benben.carmanager.bean.CarPortBean;
import com.benben.carmanager.bean.OrderBean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderAdapter extends BaseRvAdapter<OrderBean> {
    private static final String TAG = "jyw";
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private OnItemClickListener mListener;


    public OrderAdapter(Context context) {
        super(context);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (data != null && data.size() > 0) {
            OrderBean orderBean = data.get(position);
            TextView tvTitle = holder.getView(R.id.tv_title);
            TextView tvAddress = holder.getView(R.id.tv_addr);
            TextView tvStartTime = holder.getView(R.id.tv_startTime);
            TextView tvEndTime = holder.getView(R.id.tv_endTime);
            TextView tvCarNumber = holder.getView(R.id.tv_carNumber);
            TextView tvPrice = holder.getView(R.id.tv_price);
            ImageView ivNav = holder.getView(R.id.iv_nav);
            ImageView ivStatus = holder.getView(R.id.iv_status);
            tvTitle.setText(orderBean.getParkName());
            tvAddress.setText(orderBean.getParkAddr());
            tvStartTime.setText(orderBean.getStartTime());
            tvEndTime.setText(orderBean.getEndTime());
            tvCarNumber.setText(orderBean.getCarNumber());

            String startTime = orderBean.getStartTime();
            String endTime = orderBean.getEndTime();
            if ("1".equals(orderBean.getStatus())){
                ivStatus.setVisibility(View.VISIBLE);
            }else {
                ivStatus.setVisibility(View.GONE);
            }
            try {
                Date start = df.parse(startTime);
                Date end = df.parse(endTime);
                long startLong = start.getTime();
                long endLong = end.getTime();
                Log.d(TAG, "onBindViewHolder: startLong= "+startLong + " endLong= "+endLong);
                long chatime = ((endLong-startLong)/1000)/1000/60/60;
                int price = (int) (chatime * 5);
                if(chatime<=0){
                    price = 5;
                }
                orderBean.setPrice(price+"");
                tvPrice.setText(orderBean.getPrice()+"元");

            } catch (ParseException e) {
                e.printStackTrace();
            }

            ivNav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        mListener.onNav(data.get(position));
                    }
                }
            });
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
        return R.layout.carpark_order_item;
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
