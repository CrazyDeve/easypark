package com.benben.carmanager.adapter;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> views = new SparseArray<>();

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public <T extends View> T getView(@IdRes int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            if (view == null){
                throw new IllegalArgumentException("找不到view:"+viewId);
            }
            views.put(viewId,view);
        }
        return (T)view;
    }
    public void setText(String text,@IdRes int viewId){
        this.<TextView>getView(viewId).setText(text);
    }
    public void setText(@StringRes int text, @IdRes int viewId){
        this.<TextView>getView(viewId).setText(text);
    }
    public void setText(CharSequence text,@IdRes int viewId){
        this.<TextView>getView(viewId).setText(text);
    }
    public void setOnClickListener(@IdRes int viewId, View.OnClickListener listener){
        getView(viewId).setOnClickListener(listener);
    }
    public void setVisibility(int visibility,@IdRes int viewId){
        getView(viewId).setVisibility(visibility);
    }
}
