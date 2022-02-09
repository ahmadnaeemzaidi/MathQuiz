package com.techgeeks.notalone.number.huawei;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.huawei.hms.ads.AdListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.InterstitialAd;
import com.huawei.hms.ads.VideoConfiguration;
import com.huawei.hms.ads.banner.BannerView;
import com.huawei.hms.ads.nativead.DislikeAdListener;
import com.huawei.hms.ads.nativead.NativeAd;
import com.huawei.hms.ads.nativead.NativeAdConfiguration;
import com.huawei.hms.ads.nativead.NativeAdLoader;

public class Ads {

    InterstitialAd interstitialAd;

//    final String interstitialId = "testb4znbuh3n2";
//    final String bannerId = "testw6vs28auh3";
//    final String nativeId = "testy63txaom86";


    // Real ids
    // app id 105176907
    final String interstitialId = "d7xiqj7nk6";
    final String bannerId = "q5tch0v488";
    final String nativeId = "p0s1p738zz";


    public void interstitialLoad(Context context) {

        if(interstitialAd == null) {
            HwAds.init(context);
            interstitialAd = new InterstitialAd(context);
            interstitialAd.setAdId(interstitialId);

            AdParam adParam = new AdParam.Builder().build();
            interstitialAd.loadAd(adParam);
        }
    }

    public void showInd(Activity context, final Adclick adclick) {

        if ((interstitialAd == null) || (!interstitialAd.isLoaded())) {
            adclick.onclicl();
            return;
        }
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                adclick.onclicl();
                AdParam adParam = new AdParam.Builder().build();
                interstitialAd.loadAd(adParam);
            }

            @Override
            public void onAdLoaded() {
                //add
            }

            @Override
            public void onAdOpened() {
                //add
            }
        });
        interstitialAd.show(context);

    }


    public void BannerAd(Activity context, LinearLayout adContainer) {
        BannerView bannerView = new BannerView(context);
        bannerView.setAdId(bannerId);
        bannerView.setBannerAdSize(BannerAdSize.BANNER_SIZE_360_57);
        // Set the refresh interval to 60 seconds.
        bannerView.setBannerRefresh(60);
        // Create an ad request to load an ad.
        AdParam adParam = new AdParam.Builder().build();
        bannerView.setAdListener(new com.huawei.hms.ads.AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d("AdvertActivityTag=", "add loaded");
            }

            @Override
            public void onAdFailed(int i) {
                super.onAdFailed(i);
                Log.d("AdvertActivityTag=", "add fail =" + i);
            }
        });
        bannerView.loadAd(adParam);
//        LinearLayout.LayoutParams params =
//                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                        LinearLayout.LayoutParams.MATCH_PARENT);
        adContainer.addView(bannerView);
//        adContainer.invalidate();
    }



    public Ads() {
    }

    public  void loadNativeAd(final Activity context, final FrameLayout nativeAdLayout) {
        refreshAd(context, nativeAdLayout);
    }

    private void refreshAd(final Activity context, final FrameLayout frameLayout) {

        NativeAdLoader.Builder builder = new NativeAdLoader.Builder(context, nativeId);
        builder.setNativeAdLoadedListener(new NativeAd.NativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(NativeAd nativeAd) {
                // Call this method when an ad is successfully loaded.
                //updateStatus(getString(R.string.status_load_ad_success), true);

                // Display native ad.
                //showNativeAd(nativeAd);
                final View nativeView = NativeViewFactory.createAppDownloadButtonAdView(nativeAd, frameLayout);
                nativeAd.setDislikeAdListener(new DislikeAdListener() {
                    @Override
                    public void onAdDisliked() {
                        // Call this method when an ad is closed.
                        //updateStatus(getString(R.string.ad_is_closed), true);
                        frameLayout.removeView(nativeView);
                    }
                });

                // Add NativeView to the app UI.
                frameLayout.removeAllViews();
                frameLayout.addView(nativeView);
            }
        }).setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
               // updateStatus(getString(R.string.status_load_ad_finish), true);
            }

            @Override
            public void onAdFailed(int errorCode) {
                // Call this method when an ad fails to be loaded.
                //updateStatus(getString(R.string.status_load_ad_fail) + errorCode, true);
            }
        });

        VideoConfiguration videoConfiguration = new VideoConfiguration.Builder()
                .setStartMuted(true)
                .build();

        NativeAdConfiguration adConfiguration = new NativeAdConfiguration.Builder()
                .setChoicesPosition(NativeAdConfiguration.ChoicesPosition.BOTTOM_RIGHT) // Set custom attributes.
                .setVideoConfiguration(videoConfiguration)
                .setRequestMultiImages(true)
                .build();

        NativeAdLoader nativeAdLoader = builder.setNativeAdOptions(adConfiguration).build();
        nativeAdLoader.loadAd(new AdParam.Builder().build());

        //updateStatus(getString(R.string.status_ad_loading), false);

    }
}
