package com.benben.carmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.carmanager.R;
import com.benben.carmanager.model.Word;

import java.util.List;
/*
单词列表界面列表的适配器
 */


public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder>{
    private List<Word> mData;
    public Context mcon;

    public WordAdapter(List<Word> data, Context con) {
        this.mData = data;
        mcon=con;
    }

    public void updateData(List<Word> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item, parent, false);
        // 实例化viewholder
        final ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 绑定数据
        holder.mheadWord.setText(mData.get(position).getHeadWord());
        holder.mtranCn.setText(mData.get(position).getTranCN());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mheadWord,mtranCn;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            mheadWord= (TextView) itemView.findViewById(R.id.word_list_item_headWord);
            mtranCn=(TextView)itemView.findViewById(R.id.word_list_item_tranCn);
//            cardView=(CardView)itemView.findViewById(R.id.wordcard);
        }
    }
}
