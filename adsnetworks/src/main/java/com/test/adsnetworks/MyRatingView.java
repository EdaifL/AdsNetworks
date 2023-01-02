package com.test.adsnetworks;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatRatingBar;

import com.yandex.mobile.ads.nativeads.Rating;

public class MyRatingView extends AppCompatRatingBar implements Rating {

    public MyRatingView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyRatingView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRatingView(final Context context) {
        super(context);
    }

    @Override
    public void setRating(final float rating) {
        super.setRating(rating);
    }

    @Override
    public float getRating() {
        return super.getRating();
    }
}