package com.benben.carmanager.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.benben.carmanager.R;


public class WaitDialogFragment extends DialogFragment {


    private static WaitDialogFragment waitDialog;

    public static WaitDialogFragment getInstance() {
        if (waitDialog == null) {
            waitDialog = new WaitDialogFragment();
        }

        return waitDialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        //设置背景半透明
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
        getDialog().getWindow().setDimAmount(0.0f);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        getDialog().setCancelable(false);//这个会屏蔽掉返回键
        getDialog().setCanceledOnTouchOutside(false);


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_wait_layout, container, false);
        return view;
    }
}
