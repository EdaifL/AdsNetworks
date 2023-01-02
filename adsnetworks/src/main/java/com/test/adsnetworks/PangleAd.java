package com.test.adsnetworks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bytedance.sdk.openadsdk.api.PAGConstant;
import com.bytedance.sdk.openadsdk.api.banner.PAGBannerAd;
import com.bytedance.sdk.openadsdk.api.banner.PAGBannerAdLoadListener;
import com.bytedance.sdk.openadsdk.api.banner.PAGBannerRequest;
import com.bytedance.sdk.openadsdk.api.banner.PAGBannerSize;
import com.bytedance.sdk.openadsdk.api.init.PAGConfig;
import com.bytedance.sdk.openadsdk.api.init.PAGSdk;
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAd;
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAdInteractionListener;
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAdLoadListener;
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialRequest;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGImageItem;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGNativeAd;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGNativeAdData;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGNativeAdInteractionListener;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGNativeAdLoadListener;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGNativeRequest;
import com.bytedance.sdk.openadsdk.api.open.PAGAppOpenAd;
import com.bytedance.sdk.openadsdk.api.open.PAGAppOpenAdInteractionListener;
import com.bytedance.sdk.openadsdk.api.open.PAGAppOpenAdLoadListener;
import com.bytedance.sdk.openadsdk.api.open.PAGAppOpenRequest;

import java.util.ArrayList;
import java.util.List;

public class PangleAd {
    Activity activity;
    ProgressDialog dialog;
    static boolean openappShow = false;

    public PangleAd(Activity activity) {
        this.activity = activity;
        if (JsonAds.IsPangle){
            init();
        }
        dialog = JsonAds.Dialog(activity);
    }
    private void init(){
        PAGConfig pAGInitConfig = buildNewConfig(activity);
        PAGConfig.setChildDirected(PAGConstant.PAGChildDirectedType.PAG_CHILD_DIRECTED_TYPE_CHILD);//Set the configuration of COPPA
        PAGConfig.getChildDirected();
        PAGConfig.setGDPRConsent(PAGConstant.PAGGDPRConsentType.PAG_GDPR_CONSENT_TYPE_CONSENT);//Set the configuration of GDPR
        PAGConfig.getGDPRConsent();//get the value of GDPR
        PAGConfig.setDoNotSell(PAGConstant.PAGDoNotSellType.PAG_DO_NOT_SELL_TYPE_NOT_SELL);//Set the configuration of CCPA
        PAGConfig.getDoNotSell();
        PAGSdk.init(activity, pAGInitConfig, new PAGSdk.PAGInitCallback() {
            @Override
            public void success() {
                //load pangle ads after this method is triggered.
                Log.i("PangleTAG", "pangle init success: ");
            }

            @Override
            public void fail(int code, String msg) {
                Log.i("PangleTAG", "pangle init fail: " + code);
            }
        });
    }
    private static PAGConfig buildNewConfig(Context context) {
        return new PAGConfig.Builder()
                .appId(JsonAds.PangleAppId)
                .debugLog(true)
                .supportMultiProcess(false)//If your app is a multi-process app, set this value to true
                .build();
    }

    public void ShowOpenApp(){
        if (!openappShow){
        ProgressDialog dialog2 = new ProgressDialog(activity);
        dialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog2.setMessage("Loading  Please wait...");
        dialog2.setIndeterminate(true);
        dialog2.setCanceledOnTouchOutside(false);
        dialog2.show();
        PAGAppOpenRequest request = new PAGAppOpenRequest();
        request.setTimeout(3000);
        PAGAppOpenAd.loadAd(JsonAds.PangleOpenAppUniteId, request, new PAGAppOpenAdLoadListener() {
            @Override
            public void onError(int code, String message) {
                dialog2.dismiss();
            }

            @Override
            public void onAdLoaded(PAGAppOpenAd appOpenAd) {
                appOpenAd.setAdInteractionListener(new PAGAppOpenAdInteractionListener(){

                    @Override
                    public void onAdShowed() {
                        openappShow = true;

                    }

                    @Override
                    public void onAdClicked() {

                    }

                    @Override
                    public void onAdDismissed() {
                        dialog2.dismiss();
                    }
                });
                appOpenAd.show(activity);
            }
        });
        }

    }
    public void Pandle_Inter( Intent intent){
        dialog.show();
        PAGInterstitialRequest request = new PAGInterstitialRequest();
        PAGInterstitialAd.loadAd(JsonAds.PangleInterUniteId,
                request,
                new PAGInterstitialAdLoadListener() {
                    @Override
                    public void onError(int code, String message) {
                        Log.e("LOG",message);
                        if (dialog.isShowing()){ dialog.dismiss();}
                        activity.startActivity(intent);
                    }

                    @Override
                    public void onAdLoaded(PAGInterstitialAd interstitialAd) {
                        if (interstitialAd != null) {
                            interstitialAd.setAdInteractionListener(new PAGInterstitialAdInteractionListener() {
                                @Override
                                public void onAdShowed() {
                                    Log.e("PangleTAG","AdsShowed");
                                }

                                @Override
                                public void onAdClicked() {

                                }

                                @Override
                                public void onAdDismissed() {
                                    Log.e("PangleTAG","Ads Dismissed");
                                    if (dialog.isShowing()){ dialog.dismiss();}
                                    activity.startActivity(intent);

                                }
                            });
                            interstitialAd.show((Activity) activity);

                        }
                    }

                });

    }
    public void Pandle_Banner(FrameLayout layout){
        PAGBannerRequest request = new PAGBannerRequest(PAGBannerSize.BANNER_W_320_H_50);
        PAGBannerAd.loadAd(JsonAds.PangleBannerUniteId, request,
                new PAGBannerAdLoadListener() {
                    @Override
                    public void onError(int i, String s) {
                        Log.e("PangleTAG",s);
                    }

                    @Override
                    public void onAdLoaded(PAGBannerAd pagBannerAd) {
                        if (pagBannerAd != null) {
                            layout.addView(pagBannerAd.getBannerView());
                        }
                    }
                });
    }
    public void Pandle_Native(FrameLayout layout){
        PAGNativeRequest request = new PAGNativeRequest();
        PAGNativeAd.loadAd(JsonAds.PangleNativeUniteId, request, new PAGNativeAdLoadListener() {
            @Override
            public void onError(int code, String message) {
                Log.e("PangleTAG",message);
            }

            @Override
            public void onAdLoaded(PAGNativeAd pagNativeAd) {
                Log.e("PangleTAG","NativeadsLoaded");
                layout.removeAllViews();
                findADView(activity,pagNativeAd.getNativeAdData());


                pagNativeAd.registerViewForInteraction((ViewGroup) nativeAdView, clickViewList, creativeViewList, mDislike, new PAGNativeAdInteractionListener() {
                    @Override
                    public void onAdShowed() {

                    }

                    @Override
                    public void onAdClicked() {

                    }

                    @Override
                    public void onAdDismissed() {

                    }
                });
                layout.addView(nativeAdView);
            }
        });
    }
    static View nativeAdView;
    static List<View> creativeViewList = new ArrayList<>();
    static List<View> clickViewList = new ArrayList<>();
    static ImageView mDislike;
    private static void findADView(Context mContext, PAGNativeAdData adData){

        nativeAdView= LayoutInflater.from(mContext).inflate(R.layout.nativepandle, null);

        TextView mTitle = (TextView) nativeAdView.findViewById(R.id.ad_title);

        TextView mDescription = (TextView) nativeAdView.findViewById(R.id.ad_desc);

        ImageView mIcon = (ImageView) nativeAdView.findViewById(R.id.ad_icon);

        mDislike= (ImageView) nativeAdView.findViewById(R.id.ad_dislike);

        Button mCreativeButton = (Button) nativeAdView.findViewById(R.id.creative_btn);

        RelativeLayout mAdLogoView = (RelativeLayout) nativeAdView.findViewById(R.id.ad_logo);

        FrameLayout mImageOrVideoView = (FrameLayout) nativeAdView.findViewById(R.id.ad_video);

        mTitle.setText(adData.getTitle());

        mDescription.setText(adData.getDescription());
//icon
        PAGImageItem icon = adData.getIcon();
        if (icon != null && icon.getImageUrl() != null) {
            Glide.with(mContext).load(icon.getImageUrl()).into(mIcon);
        }
//set btn text
        mCreativeButton.setText(TextUtils.isEmpty(adData.getButtonText()) ? "Download" : adData.getButtonText());

//set pangle logo
        ImageView imageView = (ImageView) adData.getAdLogoView();
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mAdLogoView.addView(imageView, lp);
        if (adData.getMediaView()!=null){
            mImageOrVideoView.addView(adData.getMediaView());}
        clickViewList.add(mCreativeButton);
        creativeViewList.add(mIcon);
        creativeViewList.add(mTitle);


    }
}
