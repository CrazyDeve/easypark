package com.benben.carmanager.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.carmanager.R;
import com.benben.carmanager.adapter.WordBookAdapter;
import com.benben.carmanager.dao.SettingDao;
import com.benben.carmanager.dao.WordDao;
import com.benben.carmanager.dao.WordTypeDao;
import com.benben.carmanager.listener.IDialogBtnClickCallBack;
import com.benben.carmanager.model.WordBookEntity;
import com.benben.carmanager.model.WordType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DialogUtils {

    private static final String TAG = "DialogUtils";


    public static void showEditSubCateDialog(Context context, String title, String message, String positiveButtonText, String negativeButtonText, ISubCateDialogClickListener callBack){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_subcate_edit,null);
        SettingDao settingDao = new SettingDao(context);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

        TextView tvCancel = view.findViewById(R.id.tvCancel);
        TextView tvConfirm = view.findViewById(R.id.tvConfirm);
        TextView tvTitle = view.findViewById(R.id.title);
        RecyclerView mRvDifficult = view.findViewById(R.id.rv_difficult);

        GridLayoutManager layoutManager = new GridLayoutManager(context, 3);//设置为3列
        mRvDifficult.setLayoutManager(layoutManager);
        ArrayList<WordBookEntity> bookList = getData(context);


        WordBookAdapter wordBookAdapter = new WordBookAdapter(context, bookList);
        wordBookAdapter.setOnItemListenerListener(new WordBookAdapter.OnItemListener() {
            @Override
            public void OnItemClickListener(View view, int position) {
                String title;
                title = bookList.get(position).title;
                Log.d(TAG, "OnItemClickListener: 选择困难度 == "+title);
                settingDao.updateDifficulty(title);
                dialog.dismiss();
                callBack.onPositiveButtonClicked();
            }

            @Override
            public void OnItemLongClickListener(View view, final int position) {

            }


        });
        mRvDifficult.setAdapter(wordBookAdapter);


        if(!TextUtils.isEmpty(title)){
            tvTitle.setText(title);
        }
        tvCancel.setText(negativeButtonText);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(callBack!=null){
                    callBack.onNegativeButtonClicked();
                }
                if(dialog!=null && dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });

        tvConfirm.setText(positiveButtonText);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(callBack!=null){
                    callBack.onPositiveButtonClicked();
                }
                if(dialog!=null && dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });



        dialog.show();


    }

    public static ArrayList<WordBookEntity> getData(Context context) {
        ArrayList<WordBookEntity> mData = new ArrayList<>(0);
        WordTypeDao wordTypeDao = new WordTypeDao(context);
        WordDao wordDao = new WordDao(context);
        List<WordType> title = wordTypeDao.getAllType();
        Iterator<WordType> iterator = title.iterator();
        while (iterator.hasNext()) {
            String wordType = iterator.next().getWordType();
            mData.add(new WordBookEntity(R.mipmap.icon_book, wordType, wordDao.getTypeCount(wordType)));
        }
        return mData;

    }


    /**
     * 弹出普通对话框
     */
    public static void showDialog(Context context,String title, String message,String positiveButtonText,String negativeButtonText, IDialogBtnClickCallBack callBack){
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.onPositiveButtonClicked();
                    }
                })
                .setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.onNegativeButtonClicked();

                    }
                })
                .setTitle(title)
                .setMessage(message)
                .create();
        dialog.show();


    }


    public interface ISubCateDialogClickListener {
        void onPositiveButtonClicked();
        void onNegativeButtonClicked();
    }




    /**
     * 弹出App升级对话框
     */
    public static void showAppUpdateDialog(Context context,String title,String version, String content,String isForce,String positiveButtonText, IDialogBtnClickCallBack callBack){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_app_update,null);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(false)
                .create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);

        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvVersion = view.findViewById(R.id.tv_version);
        TextView tvContent = view.findViewById(R.id.tv_content);
        TextView tvUpdate = view.findViewById(R.id.tv_update);
        ImageView ivClose = view.findViewById(R.id.iv_close);
        if(!TextUtils.isEmpty(title)){
            tvTitle.setText(title);
        }
        if(!TextUtils.isEmpty(version)){
            tvVersion.setText(version);
        }
        if(!TextUtils.isEmpty(content)){
            tvContent.setText(content);
        }
        if(!TextUtils.isEmpty(positiveButtonText)){
            tvUpdate.setText(positiveButtonText);
        }

        if(!TextUtils.isEmpty(isForce) && isForce.equals("1")){
            ivClose.setVisibility(View.GONE);
        }else{
            ivClose.setVisibility(View.VISIBLE);
        }

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(callBack!=null){
                    callBack.onNegativeButtonClicked();
                }
                if(dialog!=null && dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });


        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(callBack!=null){
                    callBack.onPositiveButtonClicked();
                }
                if(dialog!=null && dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });


        dialog.show();


    }



}
