package com.test.adsnetworks;

import android.app.Activity;
import android.content.Intent;
import android.widget.FrameLayout;




public class AdsController {
    Activity activity;
    AdmobAds admobAds;
    ApplovinAd applovin;
    UnityAd unityAd;
    Yandex yandex;
    FAN_Facebook fan_facebook;
    PangleAd pangle;

    public AdsController(Activity activity) {
        this.activity = activity;
        admobAds = new AdmobAds(activity);
        applovin = new ApplovinAd(activity);
        unityAd = new UnityAd(activity);
        yandex = new Yandex(activity);
        fan_facebook = new FAN_Facebook(activity);
        pangle = new PangleAd(activity);
    }

    public void ShowBanner(FrameLayout layout) {
        switch (JsonAds.BannerType.toLowerCase()) {
            case "admob":
                if (JsonAds.IsAdmobON) {
                    admobAds.showBanner(layout);
                }
                break;
            case "unity":
                if (JsonAds.IsUnityON) {
                    unityAd.ShowBanner(layout);
                }
                break;
            case "pangle":
                if (JsonAds.IsPangle) {
                    pangle.Pandle_Banner(layout);
                }
                break;
            case "yandex":
                if (JsonAds.IsYandexON) {
                    yandex.Banner(layout);
                }
                break;
            case "facebook":
                if (JsonAds.IsFacebookON) {
                    fan_facebook.ShowBanner(layout);
                }
                break;
            case "applovin" :
                if (JsonAds.IsApplovin){
                  applovin.showBanner(layout);
                }
                break;

        }
    }

    public void ShowOPenAds() {
        if (JsonAds.IsPangle){
            pangle.ShowOpenApp();}
        else if (JsonAds.IsApplovin){
            applovin.OpenApp();
        }
    }

    public void Show_InterStetial(Intent intent) {
        switch (JsonAds.InterType.toLowerCase()) {
            case "admob":
                if (JsonAds.IsAdmobON) {
                    admobAds.showInterstitial(intent);
                }
                break;

            case "unity":
                if (JsonAds.IsUnityON) {
                    unityAd.ShowInter(intent);
                }
                break;

            case "pangle":
                if (JsonAds.IsPangle) {
                    pangle.Pandle_Inter(intent);
                }
                break;
            case "yandex":
                if (JsonAds.IsYandexON) {
                    yandex.ShowInter(intent);
                }
                break;

            case "facebook":
                if (JsonAds.IsFacebookON) {
                    fan_facebook.ShowInter(intent);
                }
                break;
            case "applovin" :
                if (JsonAds.IsApplovin){
                    applovin.Show_Inter(intent);
                }
                break;


        }
    }



    public void Load_NativeAds(FrameLayout layout) {
        switch (JsonAds.NativeType.toLowerCase()) {
            case "admob":
                if (JsonAds.IsAdmobON) {
                    admobAds.AdmobNative(layout);
                }
                break;
            case "yandex":
                if (JsonAds.IsYandexON) {
                    yandex.LoadNative(layout);
                }
                break;
            case "pangle":
                if (JsonAds.IsPangle) {
                    pangle.Pandle_Native(layout);
                }
                break;
            case "facebook":
                if (JsonAds.IsFacebookON) {
                    fan_facebook.createNative(layout);
                }
                break;
            case "applovin" :
                if (JsonAds.IsApplovin){
                    applovin.Show_Native(layout);
                }
                break;


        }
    }


    public void Destroy() throws Exception {
        yandex.destroy();
        fan_facebook.onMainDestroy();
    }
}
