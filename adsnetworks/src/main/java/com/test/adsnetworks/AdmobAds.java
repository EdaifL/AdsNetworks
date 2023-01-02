/*
 * Copyright (c) 2021.  Hurricane Development Studios
 */

package com.test.adsnetworks;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

public class AdmobAds {
    private static final String TAG = "Admob";
    ProgressDialog dialog ;
    private AppOpenAd mAppOpenAd = null;

    private AppOpenAd.AppOpenAdLoadCallback loadCallback;


    private final Activity mActivity;
    private InterstitialAd mInterstitialAd;

    public AdmobAds(Activity Activity) {
        this.mActivity = Activity;
        dialog = JsonAds.Dialog(mActivity);
        if (JsonAds.IsAdmobON){
        MobileAds.initialize(Activity);}
    }

    public void showOpenAd(Intent intent) {
        loadCallback =
                new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                        super.onAdLoaded(appOpenAd);
                        mAppOpenAd = appOpenAd;
                        mAppOpenAd.show(mActivity);
                        mActivity.startActivity(intent);
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        mActivity.startActivity(intent);
                    }


                };
        AppOpenAd.load(mActivity, JsonAds.AdmobOpenUnitId, new AdRequest.Builder().build(), AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);

    }

    public void showBanner(FrameLayout adContainer) {
        AdView adView = new AdView(mActivity);
        adView.setAdUnitId(JsonAds.AdmobBannerUnitId);
        adView.setAdSize(AdSize.BANNER);
        adContainer.addView(adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    public void showInterstitial(Intent intent) {
        dialog.show();
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(mActivity, JsonAds.AdmobInterUnitId, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                    mInterstitialAd.show(mActivity);
                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            mActivity.startActivity(intent);
                            if (dialog.isShowing()){
                                dialog.dismiss();
                            }

                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent( AdError adError) {
                            mActivity.startActivity(intent);
                            if (dialog.isShowing()){
                                dialog.dismiss();
                            }

                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            mInterstitialAd = null;

                        }
                    });
                }


            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
               mActivity.startActivity(intent);
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });

    }






    private void populateUnifiedNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        adView.setMediaView((com.google.android.gms.ads.nativead.MediaView) adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        adView.setNativeAd(nativeAd);


    }


    public void AdmobNative(FrameLayout frameLayout) {
        AdLoader.Builder builder = new AdLoader.Builder(mActivity, JsonAds.AdmobNativeUnitId);
        builder.forNativeAd(nativeAd ->  {

                frameLayout.setVisibility(View.VISIBLE);
                NativeAdView adView = (NativeAdView) mActivity.getLayoutInflater()
                        .inflate(R.layout.admob_native_ad_layout, null);
                populateUnifiedNativeAdView(nativeAd, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);
        });

        AdLoader adLoader = builder.withAdListener(new AdListener() {

        }).build();

        adLoader.loadAd(new AdManagerAdRequest.Builder().build());


    }


}
