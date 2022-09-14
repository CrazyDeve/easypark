package com.benben.carmanager.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.benben.carmanager.R;
import com.benben.carmanager.utils.DensityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NoticeDialogFragment extends DialogFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    private NoticeDialogListener listener;

    public interface NoticeDialogListener {
        public void onDialogPositiveClick();

        public void onDialogNegativeClick();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (NoticeDialogListener) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString() + " must implement NoticeDialogListener...");
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.order_print_dialog, null);
        ButterKnife.bind(this,view);
        builder.setView(view);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        setDialogStyle(dialog);

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDialogPositiveClick();
                dialog.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDialogNegativeClick();
                dialog.dismiss();
            }
        });

        return dialog;
    }

    private void setDialogStyle(AlertDialog dialog) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);

        View decorView = window.getDecorView();
        int paddingTop = decorView.getPaddingTop();
        int paddingBottom = decorView.getPaddingBottom();
        int paddingLeft = decorView.getPaddingLeft();
        int paddingRight = decorView.getPaddingRight();

        int width = getDialogWidth() + paddingLeft + paddingRight;
        int height = window.getAttributes().height;

        window.setLayout(width, height);
    }

    private int getDialogWidth() {
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        widthPixels -= DensityUtil.dp2px(getContext(), 50);
        return widthPixels;
    }
}
