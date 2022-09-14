package com.benben.carmanager.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.benben.carmanager.R;
import com.benben.carmanager.bean.CarPortBean;

import java.util.List;

public class CarPortAdapter extends BaseRvAdapter<CarPortBean> {
    private static final String TAG = "jyw";

    private OnItemClickListener mListener;


    public CarPortAdapter(Context context) {
        super(context);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (data != null && data.size() > 0) {
            CarPortBean carPortBean = data.get(position);
            TextView tvTitle = holder.getView(R.id.tv_title);
            TextView tvAddress = holder.getView(R.id.tv_addr);
            tvTitle.setText(carPortBean.getTitle());
            tvAddress.setText(carPortBean.getAddr());

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
        return R.layout.car_port_item;
    }


    @Override
    public void setData(List<CarPortBean> data) {
        super.setData(data);
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }




    public interface OnItemClickListener {
        void onItemClicked(CarPortBean carPortBean);
    }
}
