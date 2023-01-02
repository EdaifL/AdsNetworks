package com.test.adsnetworks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yandex.mobile.ads.banner.AdSize;
import com.yandex.mobile.ads.banner.BannerAdEventListener;
import com.yandex.mobile.ads.banner.BannerAdView;
import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;
import com.yandex.mobile.ads.common.MobileAds;
import com.yandex.mobile.ads.interstitial.InterstitialAd;
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener;
import com.yandex.mobile.ads.nativeads.NativeAd;
import com.yandex.mobile.ads.nativeads.NativeAdLoadListener;
import com.yandex.mobile.ads.nativeads.NativeAdLoader;
import com.yandex.mobile.ads.nativeads.NativeAdRequestConfiguration;
import com.yandex.mobile.ads.nativeads.NativeAdView;
import com.yandex.mobile.ads.nativeads.template.NativeBannerView;
import com.yandex.mobile.ads.rewarded.RewardedAd;


public class Yandex {
    private String TAG = "Yandex";
    private Activity activity;
    private InterstitialAd interstitialAd;
    private RewardedAd mRewardedAd;
    private NativeAdView mNativeAdView;
    private ProgressDialog dialog;


    public Yandex(Activity activity) {
        if (JsonAds.IsYandexON){
        MobileAds.initialize(activity, () -> Log.d(TAG, "SDK initialized"));
        MobileAds.enableDebugErrorIndicator(true);}
        this.activity = activity;
        dialog = JsonAds.Dialog(activity);
    }

    public void Banner(FrameLayout banner) {
        BannerAdView mBannerAdView = new BannerAdView(activity);
        mBannerAdView.setAdUnitId(JsonAds.YandexBannerUnitId);
        mBannerAdView.setAdSize(AdSize.stickySize(AdSize.FULL_SCREEN.getWidth()));
        final AdRequest adRequest = new AdRequest.Builder().build();
        mBannerAdView.setBannerAdEventListener(new BannerAdEventListener() {
            @Override
            public void onAdLoaded() {
                Log.d(TAG, "onAdLoaded: ");
            }

            @Override
            public void onAdFailedToLoad(AdRequestError adRequestError) {
                Log.d(TAG, "onAdFailedToLoad: " + adRequestError.getDescription());
            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onLeftApplication() {
            }

            @Override
            public void onReturnedToApplication() {
            }

            @Override
            public void onImpression(@Nullable ImpressionData impressionData) {

            }
        });
        // Loading the ad.
        banner.addView(mBannerAdView);
        mBannerAdView.loadAd(adRequest);
    }



    public void ShowInter(Intent intent) {
        dialog.show();
        interstitialAd = new InterstitialAd(activity);
        interstitialAd.setAdUnitId(JsonAds.YandexInterUnitId);
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);
        interstitialAd.setInterstitialAdEventListener(new InterstitialAdEventListener() {
            @Override
            public void onAdLoaded() {
                interstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {

                activity.startActivity(intent);
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
            }

            @Override
            public void onAdShown() {

            }

            @Override
            public void onAdDismissed() {
                activity.startActivity(intent);
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onLeftApplication() {

            }

            @Override
            public void onReturnedToApplication() {

            }

            @Override
            public void onImpression(@Nullable ImpressionData impressionData) {

            }
        });
    }

    private NativeAdLoader mNativeAdLoader;

    public void LoadNative(FrameLayout layout) {
        mNativeAdLoader = new NativeAdLoader(activity);
        mNativeAdLoader.setNativeAdLoadListener(new NativeAdLoadListener() {
            @Override
            public void onAdLoaded(@NonNull final NativeAd nativeAd) {
                layout.removeAllViews();
                bind_NativeAd(nativeAd, layout);
            }

            @Override
            public void onAdFailedToLoad(@NonNull final AdRequestError adRequestError) {
                Log.d("SAMPLE_TAG", adRequestError.getDescription());
            }
        });

        final NativeAdRequestConfiguration nativeAdRequestConfiguration =
                new NativeAdRequestConfiguration.Builder(JsonAds.YandexNativeUnitId)
                        .setShouldLoadImagesAutomatically(true)
                        .build();
        mNativeAdLoader.loadAd(nativeAdRequestConfiguration);


    }

    private void bind_NativeAd(@NonNull final NativeAd nativeAd, FrameLayout layout) {
        NativeBannerView mNativeBannerView = new NativeBannerView(activity);
        mNativeBannerView.setAd(nativeAd);
        layout.removeAllViews();
        layout.addView(mNativeBannerView);
    }



    public void destroy() {
        if (mRewardedAd != null) {
            mRewardedAd.setRewardedAdEventListener(null);
            mRewardedAd.destroy();
        }
    }
}
