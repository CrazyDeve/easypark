package com.benben.carmanager.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.benben.carmanager.R;
import com.benben.carmanager.utils.DensityUtil;

public class EditTextWithClearView extends LinearLayout {

    private AttributeSet attrs;
    private Context context;
    private float textSize;
    private int textColor;
    private int textHintColor;
    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBottom;
    private int marginLeft;
    private int marginRight;
    private int marginTop;
    private int marginBottom;
    private String hintText;
    private EditText editText;

    public EditTextWithClearView(Context context) {
        super(context);
        this.context = context;
        init();
    }


    public EditTextWithClearView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;
        this.context = context;
        init();
    }

    public EditTextWithClearView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.attrs = attrs;
        init();
    }


    private void getDefineAttributeSet() {
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.EditTextWithClearView);
        textSize = array.getDimension(R.styleable.EditTextWithClearView_textSize, DensityUtil.sp2px(context, 16));
        textColor = array.getColor(R.styleable.EditTextWithClearView_textColor, ContextCompat.getColor(context, R.color.color_333333));
        hintText = array.getString(array.getIndex(R.styleable.EditTextWithClearView_hint));
        textHintColor = array.getColor(R.styleable.EditTextWithClearView_textHintColor, ContextCompat.getColor(context, R.color.color_cccccc));
        paddingLeft = (int) array.getDimension(R.styleable.EditTextWithClearView_paddingLeft, DensityUtil.dp2px(context, 12));
        paddingRight = (int) array.getDimension(R.styleable.EditTextWithClearView_paddingLeft, DensityUtil.dp2px(context, 12));
        paddingTop = (int) array.getDimension(R.styleable.EditTextWithClearView_paddingLeft, DensityUtil.dp2px(context, 8));
        paddingBottom = (int) array.getDimension(R.styleable.EditTextWithClearView_paddingLeft, DensityUtil.dp2px(context, 8));
        marginLeft = (int) array.getDimension(R.styleable.EditTextWithClearView_marginLeft, DensityUtil.dp2px(context, 12));
        marginRight = (int) array.getDimension(R.styleable.EditTextWithClearView_marginRight, DensityUtil.dp2px(context, 12));
        marginTop = (int) array.getDimension(R.styleable.EditTextWithClearView_marginTop, DensityUtil.dp2px(context, 8));
        marginBottom = (int) array.getDimension(R.styleable.EditTextWithClearView_marginBottom, DensityUtil.dp2px(context, 8));
        array.recycle();

    }

    private void init() {

        getDefineAttributeSet();

        setOrientation(HORIZONTAL);

        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayoutParams.setMargins(marginLeft, marginTop, marginRight, marginBottom);
        setLayoutParams(linearLayoutParams);
        setPadding(paddingLeft,paddingTop,paddingRight,paddingBottom);

        editText = new EditText(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        editText.setPadding(DensityUtil.dp2px(context, 10), DensityUtil.dp2px(context, 4), DensityUtil.dp2px(context, 10), DensityUtil.dp2px(context, 4));
        editText.setHint(hintText);
        editText.setBackground(null);
        addView(editText, params);

        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.mipmap.ic_delete);
        imageView.setPadding(DensityUtil.dp2px(context, 5), DensityUtil.dp2px(context, 5), DensityUtil.dp2px(context, 5), DensityUtil.dp2px(context, 5));
        LayoutParams imageParams = new LayoutParams(DensityUtil.dp2px(context, 24), DensityUtil.dp2px(context, 24));
        imageView.setVisibility(INVISIBLE);
        addView(imageView, imageParams);

        setGravity(Gravity.CENTER);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    imageView.setVisibility(INVISIBLE);
                } else {
                    imageView.setVisibility(VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果控件可用时获取上面显示的文字
                editText.getEditableText().clear();
            }
        });

    }

    public void setText(String etTextValue) {
        editText.setText(etTextValue);
    }

    public String getText(){
        return editText.getText().toString().trim();
    }
}
