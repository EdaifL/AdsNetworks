package com.test.adsnetworks;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class getAds {
    String Url;
    Context context;
    public getAds(String url, Context context) {
        Url = url;
        this.context = context;

    }

    public void getFromJson(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject ads = response.optJSONObject("AdsUnits");
                JsonAds.AdmobBannerUnitId = ads.optString("AdmobBanner");
                JsonAds.AdmobInterUnitId = ads.optString("AdmobInter");
                JsonAds.AdmobNativeUnitId = ads.optString("AdmobNative");
                JsonAds.YandexBannerUnitId = ads.optString("YandexBanner");
                JsonAds.YandexInterUnitId = ads.optString("YandexInter");
                JsonAds.YandexNativeUnitId = ads.optString("YandexNative");
                JsonAds.FacebookBannerUnitId = ads.optString("FacebookBanner");
                JsonAds.FacebookInterUnitId = ads.optString("FacebookInter");
                JsonAds.FacebookNativeUnitId = ads.optString("FacebookNative");
                JsonAds.PangleBannerUniteId = ads.optString("PangleBanner");
                JsonAds.PangleInterUniteId = ads.optString("PangleInter");
                JsonAds.PangleNativeUniteId = ads.optString("PangleNative");
                JsonAds.PangleOpenAppUniteId = ads.optString("PangleOpenApp");
                JsonAds.PangleAppId = ads.optString("PangleAppId");
                JsonAds.UnityGameID = ads.optString("UnityGameID");
                JsonAds.ApplovinBanner = ads.optString("ApplovinBanner");
                JsonAds.ApplovinInter = ads.optString("ApplovinInter");
                JsonAds.ApplovinNative = ads.optString("ApplovinNative");
                JsonAds.ApplovinOpenApp = ads.optString("ApplovinOpenApp");
                ///////////////////////////////////////////////////////////

                JSONObject AdsSettings = response.optJSONObject("AdsConfig");
                /////////////////////////////////////////////////////////////
                assert AdsSettings != null;
                JsonAds.InterType = AdsSettings.optString("InterType");
                JsonAds.BannerType = AdsSettings.optString("BannerType");
                JsonAds.NativeType = AdsSettings.optString("NativeType");
                JsonAds.IsAdmobON =AdsSettings.optBoolean("IsAdmobON");
                JsonAds.IsFacebookON = AdsSettings.optBoolean("IsFacebookON");
                JsonAds.IsYandexON = AdsSettings.optBoolean("IsYandexON");
                JsonAds.IsUnityON = AdsSettings.optBoolean("IsUnityON");
                JsonAds.IsPangle = AdsSettings.optBoolean("IsPangle");
                JsonAds.IsApplovin = AdsSettings.optBoolean("IsApplovin");


                if (JsonAds.InterType.isEmpty()) {
                    if (JsonAds.IsPangle) {
                        JsonAds.InterType = "pangle";
                    } else if (JsonAds.IsYandexON) {
                        JsonAds.InterType = "yandex";
                    } else if (JsonAds.IsFacebookON) {
                        JsonAds.InterType = "facebook";
                    } else if (JsonAds.IsUnityON) {
                        JsonAds.InterType = "unity";
                    }  else if (JsonAds.IsAdmobON) {
                        JsonAds.InterType = "admob";
                    }else if (JsonAds.IsApplovin){
                        JsonAds.InterType = "applovin";
                    }
                }
                if (JsonAds.NativeType.isEmpty()) {
                    if (JsonAds.IsPangle) {
                        JsonAds.NativeType = "pangle";
                    } else if (JsonAds.IsYandexON) {
                        JsonAds.NativeType = "yandex";
                    } else if (JsonAds.IsFacebookON) {
                        JsonAds.NativeType = "facebook";
                    } else if (JsonAds.IsAdmobON) {
                        JsonAds.NativeType = "admob";
                    }else if (JsonAds.IsApplovin) {
                        JsonAds.NativeType = "applovin";
                    }
                }
                if (JsonAds.BannerType.isEmpty()) {
                    if (JsonAds.IsPangle) {
                        JsonAds.BannerType = "pangle";
                    } else if (JsonAds.IsYandexON) {
                        JsonAds.BannerType = "yandex";
                    } else if (JsonAds.IsFacebookON) {
                        JsonAds.BannerType = "facebook";
                    } else if (JsonAds.IsUnityON) {
                        JsonAds.BannerType = "unity";
                    } else if (JsonAds.IsAdmobON) {
                        JsonAds.BannerType = "admob";
                    } else if(JsonAds.IsApplovin){
                        JsonAds.BannerType = "applovin";
                    }

                }
                //////////////////////////////////////////////////////////////
                JSONArray GuideArray = response.optJSONArray("Guide");
                ////////////////////////////////////////////////////////////
                if (GuideArray != null){
                    for (int i = 0; i < GuideArray.length(); i++) {
                        String Title = GuideArray.optJSONObject(i).optString("Title");
                        String Content = GuideArray.optJSONObject(i).optString("Content");
                        String ImgUrl = GuideArray.optJSONObject(i).optString("Img");
                        JsonGuide.Guide  guide = new JsonGuide.Guide(Title,Content,ImgUrl);
                        JsonGuide.GuideList.add(guide);

                    }}
                JsonAds.IsAdsOn  =  true;
            }
        },  error -> {
            JsonAds.IsAdsOn  =  false;
            new AlertDialog.Builder(context).setMessage(error.getMessage()).show();
        });
        Volley.newRequestQueue(context).add(request);
    }
}
