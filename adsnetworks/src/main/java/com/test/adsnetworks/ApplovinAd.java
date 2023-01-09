package com.test.adsnetworks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxAppOpenAd;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.mediation.nativeAds.MaxNativeAdViewBinder;
import com.applovin.sdk.AppLovinSdk;



public class ApplovinAd {
    Activity context;
    private MaxAppOpenAd appOpenAd;
    private static boolean IsOpenshowed = false;
    private MaxAdView adView;
    MaxInterstitialAd interstitialAd;
    ProgressDialog dialog;
    private String LogTag = "Applovin";
    private MaxNativeAdLoader nativeAdLoader;
    private MaxAd             nativeAd;

    public ApplovinAd(Activity activity) {
        this.context = activity;
        dialog = JsonAds.Dialog(context);
        if (JsonAds.IsApplovin){
            init();
        }
    }

    private void init(){
        AppLovinSdk.getInstance( context ).setMediationProvider( "max" );
        AppLovinSdk.initializeSdk( context);

    }
    public void OpenApp(){
        if (!IsOpenshowed && !JsonAds.ApplovinOpenApp.isEmpty()){
            dialog.show();
        appOpenAd = new MaxAppOpenAd( JsonAds.ApplovinOpenApp, context);
        appOpenAd.setListener(new MaxAdListener() {
            @Override
            public void onAdLoaded(MaxAd ad) {
                if ( appOpenAd == null || !AppLovinSdk.getInstance( context ).isInitialized() ) return;

                if ( appOpenAd.isReady() )
                {
                    appOpenAd.showAd( JsonAds.ApplovinOpenApp );
                }
                else
                {
                    appOpenAd.loadAd();
                }
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {
                if (dialog.isShowing()){dialog.dismiss();}
                IsOpenshowed =true;
            }

            @Override
            public void onAdHidden(MaxAd ad) {

            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                appOpenAd.loadAd();
                if (dialog.isShowing()){dialog.dismiss();}
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                appOpenAd.loadAd();
                if (dialog.isShowing()){dialog.dismiss();}
            }
        });
        appOpenAd.loadAd();
    }}
    public void showBanner(FrameLayout layout){
        if (!JsonAds.ApplovinBanner.isEmpty()){
        adView = new MaxAdView( JsonAds.ApplovinBanner, context );
        adView.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd ad) {

            }

            @Override
            public void onAdCollapsed(MaxAd ad) {

            }

            @Override
            public void onAdLoaded(MaxAd ad) {
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {

            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {

            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {

            }
        });
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int heightPx = context.getResources().getDimensionPixelSize( R.dimen.banner_height );
        adView.setLayoutParams( new ViewGroup.LayoutParams( width, heightPx ) );
        adView.setBackgroundColor(Color.WHITE);
        adView.startAutoRefresh();
         adView.loadAd();
        layout.addView(adView);

        }

    }
    public void Show_Inter(Intent intent){
        if (!JsonAds.ApplovinInter.isEmpty()){
        dialog.show();
        interstitialAd =new MaxInterstitialAd(JsonAds.ApplovinInter, context);
        interstitialAd.setListener(new MaxAdListener() {
            @Override
            public void onAdLoaded(MaxAd ad) {
                interstitialAd.showAd(JsonAds.ApplovinInter);
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {

                if (dialog.isShowing()){dialog.dismiss();}
                context.startActivity(intent);
            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                if (dialog.isShowing()){dialog.dismiss();}
                context.startActivity(intent);
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                if (dialog.isShowing()){dialog.dismiss();}
                context.startActivity(intent);
            }
        });
            interstitialAd.loadAd();}
        else {
            context.startActivity(intent);
        }

    }
    public void Show_Native(FrameLayout layout){
        if (!JsonAds.ApplovinNative.isEmpty()) {
            nativeAdLoader = new MaxNativeAdLoader(JsonAds.ApplovinNative, context);
            nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                @Override
                public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad) {
                    // Clean up any pre-existing native ad to prevent memory leaks.
                    nativeAd = ad;

                    layout.removeAllViews();
                    layout.addView(nativeAdView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 900));
                }

                @Override
                public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                    // We recommend retrying with exponentially higher delays up to a maximum delay
                }

                @Override
                public void onNativeAdClicked(final MaxAd ad) {
                    // Optional click callback
                }
            });

            nativeAdLoader.loadAd();

        }
    }

}
