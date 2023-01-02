package com.test.adsnetworks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;
import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;



public class UnityAd {
    static final String TAG = "UnityAds";
    Activity activity;
    private BannerView bottomBanner;
    private final Boolean testMode = false;
    private ProgressDialog dialog;



    public UnityAd(Activity activity) {
        this.activity = activity;
        dialog = JsonAds.Dialog(activity);
        if (JsonAds.IsUnityON){
        UnityAds.initialize(activity, JsonAds.UnityGameID, testMode, new IUnityAdsInitializationListener() {
            @Override
            public void onInitializationComplete() {
                Log.e("UnityAdsExample", "Unity Ads initialization complete ");
                LoadInter();
            }

            @Override
            public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
                Log.e("UnityAdsExample", "Unity Ads initialization failed with error: [" + error + "] " + message);

            }
        });}


    }

    public void ShowBanner(FrameLayout layout) {
        Log.d(TAG, "ShowBanner: ");
        bottomBanner = new BannerView(activity, "Banner_Android", new UnityBannerSize(320, 50));
        // Set the listener for banner lifecycle events:
        bottomBanner.setListener(new BannerView.Listener() {
            @Override
            public void onBannerLoaded(BannerView bannerAdView) {
                super.onBannerLoaded(bannerAdView);
                Log.d(TAG, "onBannerLoaded: " + bannerAdView.getPlacementId());
            }

            @Override
            public void onBannerFailedToLoad(BannerView bannerAdView, BannerErrorInfo errorInfo) {
                super.onBannerFailedToLoad(bannerAdView, errorInfo);
                Log.d(TAG, "onBannerFailedToLoad: " + errorInfo.errorMessage);
            }

            @Override
            public void onBannerClick(BannerView bannerAdView) {
                super.onBannerClick(bannerAdView);
            }

            @Override
            public void onBannerLeftApplication(BannerView bannerAdView) {
                super.onBannerLeftApplication(bannerAdView);
            }
        });
        bottomBanner.load();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        bottomBanner.setLayoutParams(params);
        layout.addView(bottomBanner);
    }

    public void ShowInter(Intent intent) {
        dialog.show();
            UnityAds.load("Rewarded_Android", new IUnityAdsLoadListener() {
                @Override
                public void onUnityAdsAdLoaded(String s) {
                    UnityAds.show((Activity) activity, "Rewarded_Android", new UnityAdsShowOptions(), new IUnityAdsShowListener() {
                        @Override
                        public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                            activity.startActivity(intent);

                        }

                        @Override
                        public void onUnityAdsShowStart(String placementId) {

                        }

                        @Override
                        public void onUnityAdsShowClick(String placementId) {

                        }

                        @Override
                        public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                            activity.startActivity(intent);
                            if (dialog.isShowing()){dialog.dismiss(); }

                        }
                    });
                }

                @Override
                public void onUnityAdsFailedToLoad(String s, UnityAds.UnityAdsLoadError unityAdsLoadError, String s1) {
                    activity.startActivity(intent);
                    if (dialog.isShowing()){dialog.dismiss(); }
                }
            });




    }

    private IUnityAdsLoadListener loadListener = new IUnityAdsLoadListener() {
        @Override
        public void onUnityAdsAdLoaded(String placementId) {

        }

        @Override
        public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {

            Log.e("UnityAdsExample", "Unity Ads failed to load ad for " + placementId + " with error: [" + error + "] " + message);
            //  LoadInter();

        }
    };

    public void LoadInter() {
        UnityAds.load(JsonAds.UnityGameID, loadListener);
    }


}
