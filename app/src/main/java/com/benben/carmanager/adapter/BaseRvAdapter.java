package com.benben.carmanager.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {


    protected final Context context;
    protected List<T> data;
    private final LayoutInflater inflater;
    protected static final int viewType_emptyview = 0x100;
    private Toast toast;


    public BaseRvAdapter(Context context) {
        super();
        inflater = LayoutInflater.from(context);
        this.context = context;
        data = new ArrayList<>();
        toast = Toast.makeText(context,"",Toast.LENGTH_SHORT);

    }

    protected void addData(List<T> data){
        if(data==null || data.isEmpty()){
            return;
        }
        this.data.addAll(data);
    }

    public void clearData(){
        if(data!=null){
            data.clear();
        }
    }

    protected void setData(List<T> data){
        this.data = data;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int resourceId = getLayoutResourceId(viewType);
        View view = inflater.inflate(resourceId, parent, false);
        return new BaseViewHolder(view);
    }

    protected abstract int getLayoutResourceId(int viewType);


    @Override
    public int getItemCount() {
        return data.size();
    }


    /**
     * 判断字符串是否为非空
     * @param str
     * @return
     */
    protected boolean isNoEmptyString(String str){
        return !TextUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    protected boolean isEmptyString(String str){
        return TextUtils.isEmpty(str);
    }


    protected void showToast(String msg){
        if(toast!=null){
            toast.setText(msg);
            toast.setGravity(Gravity.BOTTOM,0,0);
            toast.show();
        }
    }

}
