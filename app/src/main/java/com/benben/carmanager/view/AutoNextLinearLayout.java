package com.benben.carmanager.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.benben.carmanager.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动换行的LinearLayout  横向
 */
public class AutoNextLinearLayout extends LinearLayout {
    private static final String TAG = "AutoNextLinearLayout";

    private List<List<View>> lineLists;
    private List<View> lineViewLists;

    public AutoNextLinearLayout(Context context) {
        this(context, null);
    }

    public AutoNextLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoNextLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

        lineLists = new ArrayList<List<View>>();
        lineViewLists = new ArrayList<View>();

        int withMode = MeasureSpec.getMode(widthMeasureSpec);
        int withSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int with = 0;
        int height = 0;
        int childCount = getChildCount();

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        switch (withMode) {
            case MeasureSpec.EXACTLY:
                with = withSize;
                break;
            case MeasureSpec.AT_MOST:
                for(int i=0;i<childCount;i++){
                    with += getChildAt(i).getMeasuredWidth();
                }

                with += getPaddingLeft() + getPaddingRight()+ DensityUtil.dp2px(getContext(),8);

                with = with > withSize ? withSize : with;

                break;
            case MeasureSpec.UNSPECIFIED:
                for(int i=0;i<childCount;i++){
                    with += getChildAt(i).getMeasuredWidth();
                }

                with += getPaddingLeft() + getPaddingRight();


                break;
                default:
                    with = withSize;
                    break;
        }




        int calculateWidth = 0;
        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            calculateWidth+= child.getMeasuredWidth();

            if(calculateWidth>with){
                lineLists.add(lineViewLists);
                calculateWidth = 0;
                calculateWidth+=child.getMeasuredWidth();
                lineViewLists = new ArrayList<>();
                lineViewLists.add(child);


            }else{
                lineViewLists.add(child);

            }

        }

        lineLists.add(lineViewLists);




        switch (heightMode){
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                for (int i = 0; i < lineLists.size(); i++) {
                    int childMeasuredHeight =0;

                    List<View> views = lineLists.get(i);
                    if(views!=null&&views.size()>0){

                        for(int j=0;j<views.size();j++){
                            View child = views.get(j);
                            childMeasuredHeight = child.getMeasuredHeight();

                        }
                    }

                    height+=childMeasuredHeight+ DensityUtil.dp2px(getContext(),8);

                }
                break;
            case MeasureSpec.UNSPECIFIED:
                for(int i=0;i<childCount;i++){
                    with += getChildAt(i).getMeasuredWidth();
                }

                with += getPaddingLeft() + getPaddingRight();


                break;
            default:
                with = withSize;
                break;
        }


        setMeasuredDimension(with,height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {


        for (int i = 0; i < lineLists.size(); i++) {

            int lengthX = 0;
            int lengthY = 0;

            List<View> views = lineLists.get(i);
            if(views!=null&&views.size()>0){

                for(int j=0;j<views.size();j++){
                    View child = views.get(j);
                    int width = child.getMeasuredWidth();
                    int height = child.getMeasuredHeight();
                    if(j!=0){
                        lengthX += width+ DensityUtil.dp2px(getContext(),8);
                    }else{
                        lengthX += width;
                    }

                    if(i!=0){
                        lengthY = i * height + height + i*DensityUtil.dp2px(getContext(),8);
                    }else{
                        lengthY = i * height + height;
                    }



                    child.layout(lengthX - width, lengthY - height, lengthX, lengthY);
                }
            }



        }

    }
}
