package com.benben.carmanager.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.benben.carmanager.R;

import java.util.List;

public class StudyDayAdapter extends BaseRvAdapter<String> {
    private static final String TAG = "jyw";

    private OnItemClickListener mListener;


    public StudyDayAdapter(Context context) {
        super(context);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

    }



    @Override
    protected int getLayoutResourceId(int viewType) {
        return R.layout.study_day_item;
    }


    @Override
    public void setData(List<String> data) {
        super.setData(data);
        notifyDataSetChanged();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }




    public interface OnItemClickListener {
        void onItemClicked(String date);
    }
}
