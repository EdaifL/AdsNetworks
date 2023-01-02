package com.test.adsnetworks;

import android.app.Activity;
import android.app.ProgressDialog;

public class JsonAds  {

    public static String FacebookNativeUnitId;
    public static String FacebookBannerUnitId;
    public static String FacebookInterUnitId;
    public static String AdmobBannerUnitId;
    public static String AdmobInterUnitId;
    public static String AdmobNativeUnitId;
    public static String AdmobOpenUnitId;
    public static String YandexBannerUnitId;
    public static String YandexInterUnitId;
    public static String YandexNativeUnitId;
    public static String PangleAppId;
    public static String PangleInterUniteId;
    public static String PangleBannerUniteId;
    public static String PangleNativeUniteId;
    public static String PangleOpenAppUniteId;
    public static String UnityGameID;
    public static String BannerType;
    public static String InterType;
    public static String NativeType;
    public static Boolean IsAdmobON;
    public static Boolean IsFacebookON;
    public static Boolean IsYandexON;
    public static Boolean IsUnityON;
    public static Boolean IsPangle;
    public static Boolean IsApplovin;
    public static String ApplovinOpenApp;
    public static String ApplovinBanner;
    public static String ApplovinInter;
    public static String ApplovinNative;
    public static boolean IsAdsOn;

    public static ProgressDialog Dialog(Activity activity){
        ProgressDialog dialog = new ProgressDialog(activity,ProgressDialog.THEME_HOLO_LIGHT);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setProgressNumberFormat(null);
        dialog.setProgressPercentFormat(null);
        dialog.setIndeterminate(true);
        dialog.setMessage("Page Loading...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

}
