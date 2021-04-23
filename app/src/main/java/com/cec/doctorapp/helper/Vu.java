package com.cec.doctorapp.helper;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;

import androidx.core.content.ContextCompat;

public class Vu{
    public static void setTransparentBG(View view){
        view.post(()->{
            view.setBackgroundColor(ContextCompat.getColor(view.getContext(), android.R.color.transparent));
        });
    }
    public static void show(final View view) {
        if (view==null)return;
        view.post(()->view.setVisibility(View.VISIBLE));
    }

    public static void gone(View view) {
        view.post(()->view.setVisibility(View.GONE));
    }

    public static boolean isVisible(View view) {
        return view.getVisibility()==View.VISIBLE;
    }
    public static boolean isNotVisible(View view) {
        return !isVisible(view);
    }
    public static void fabStyleGone(View view) {
        view.post(() -> {
            view.setPivotX(0.5f);
            view.setPivotY(0.5f);
            view.animate()
                    .setDuration(500)
                    .scaleX(0f)
                    .scaleY(0f)
                    .withEndAction(() -> {
                        gone(view);
                        view.setScaleX(1);
                        view.setScaleY(1);
                    });
        });
    }

    public static void fabStyleShow(View view) {
        view.post(() -> {
            ScaleAnimation anim=new ScaleAnimation(
                    0,
                    1.0f,
                    0,
                    1.0f,
                    Animation.RELATIVE_TO_SELF,
                    .5f,
                    Animation.RELATIVE_TO_SELF,
                    .5f
            );
            show(view);
            anim.setInterpolator(new OvershootInterpolator(2.5f));
            anim.setDuration(600);
            view.startAnimation(anim);
        });
    }

}
