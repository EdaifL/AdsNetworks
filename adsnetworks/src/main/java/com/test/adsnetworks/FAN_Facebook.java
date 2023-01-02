package com.test.adsnetworks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;

import java.util.ArrayList;
import java.util.List;


public class FAN_Facebook {
    private AdView adView = null;
    private AdListener adListener;
    private InterstitialAd interstitialAd;
    private Activity activity = null;
    private NativeAdLayout nativeAdLayout;
    private LinearLayout adViewNative;
    private NativeAd nativeAd;
    private FrameLayout adChoicesContainer;
    private ProgressDialog dialog;

    public FAN_Facebook(Activity p_activity) {
        activity = p_activity;
        dialog = JsonAds.Dialog(activity);
        if (JsonAds.IsFacebookON){
        AudienceNetworkAds.initialize(activity);}

    }

    public void ShowBanner(FrameLayout adLayout) {
        adView = new AdView(activity, JsonAds.FacebookBannerUnitId, AdSize.BANNER_HEIGHT_50);
        adListener = new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
            }

            @Override
            public void onAdLoaded(Ad ad) {
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }
        };
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());

        adLayout.addView(adView);

    }

    public void createNative(FrameLayout layout) {
        adChoicesContainer = layout;
        nativeAd = new NativeAd(activity, JsonAds.FacebookNativeUnitId);

        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Race condition, load() called again before last ad was displayed
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                // Inflate Native Ad into Container
                inflateAd(nativeAd);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };

        // Request an ad
        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());
    }

    private void inflateAd(NativeAd nativeAd) {
        nativeAd.unregisterView();

        // Add the Ad view into the ad container.
        // nativeAdLayout = findViewById(R.id.native_ad_container);
        nativeAdLayout = new NativeAdLayout(activity);
        LayoutInflater inflater = LayoutInflater.from(activity);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        adViewNative = (LinearLayout) inflater.inflate(R.layout.fb_native_ad_layout, nativeAdLayout, false);
        nativeAdLayout.addView(adViewNative);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(nativeAdLayout);

        // Add the AdOptionsView
        LinearLayout adChoices = activity.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(activity, nativeAd, nativeAdLayout);
        if (adChoices != null) {
            adChoices.removeAllViews();
            adChoices.addView(adOptionsView, 0);
        }


        // Create native UI using the ad metadata.
        MediaView nativeAdIcon = adViewNative.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adViewNative.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = adViewNative.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adViewNative.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adViewNative.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adViewNative.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = adViewNative.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                adViewNative,
                nativeAdMedia,
                nativeAdIcon,
                clickableViews);

    }





    public void ShowInter(Intent intent) {
        dialog.show();
        interstitialAd = new InterstitialAd(activity, JsonAds.FacebookInterUnitId);
        interstitialAd.loadAd(interstitialAd.buildLoadAdConfig()
                        .withAdListener(new InterstitialAdListener() {
                            @Override
                            public void onInterstitialDisplayed(Ad ad) {

                            }

                            @Override
                            public void onInterstitialDismissed(Ad ad) {
                                if (dialog.isShowing()){
                                    dialog.dismiss();
                                }
                                activity.startActivity(intent);
                            }

                            @Override
                            public void onError(Ad ad, AdError adError) {
                                if (dialog.isShowing()){
                                    dialog.dismiss();
                                }
                                activity.startActivity(intent);
                            }

                            @Override
                            public void onAdLoaded(Ad ad) {
                                interstitialAd.show();
                            }

                            @Override
                            public void onAdClicked(Ad ad) {

                            }

                            @Override
                            public void onLoggingImpression(Ad ad) {

                            }
                        })
                        .build());
    }

    public void onMainDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        if (nativeAd != null) {
            nativeAd.destroy();
        }
    }


}
