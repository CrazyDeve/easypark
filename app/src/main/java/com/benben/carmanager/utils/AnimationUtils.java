package com.benben.carmanager.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class AnimationUtils {

    /**
     * 执行平移和缩放动画
     *
     * @param view
     */
    public static void runTranslateAndScale(View view) {
        Animation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, -1.0f);
        translateAnimation.setDuration(1000);


        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);


        view.startAnimation(translateAnimation);
        view.startAnimation(scaleAnimation);

    }
}
