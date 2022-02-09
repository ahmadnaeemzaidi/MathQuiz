package com.techgeeks.notalone.number.huawei;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.huawei.hms.ads.AppDownloadButton;
import com.huawei.hms.ads.AppDownloadButtonStyle;
import com.huawei.hms.ads.nativead.MediaView;
import com.huawei.hms.ads.nativead.NativeAd;
import com.huawei.hms.ads.nativead.NativeView;

public class NativeViewFactory {
    private static final String TAG = NativeViewFactory.class.getSimpleName();

    public static View createAppDownloadButtonAdView(NativeAd nativeAd, final ViewGroup parentView) {
        LayoutInflater inflater = LayoutInflater.from(parentView.getContext());
        View adRootView = inflater.inflate(R.layout.native_ad_with_app_download_btn_template, null);

        final NativeView nativeView = adRootView.findViewById(R.id.native_app_download_button_view);


        nativeView.setTitleView(adRootView.findViewById(R.id.ad_title));
        nativeView.setMediaView((MediaView) adRootView.findViewById(R.id.ad_media));
        nativeView.setAdSourceView(adRootView.findViewById(R.id.ad_source));
        nativeView.setCallToActionView(adRootView.findViewById(R.id.ad_call_to_action));

        // Populate a native ad material view.
        ((TextView) nativeView.getTitleView()).setText(nativeAd.getTitle());
        nativeView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        if (null != nativeAd.getAdSource()) {
            ((TextView) nativeView.getAdSourceView()).setText(nativeAd.getAdSource());
        }

        nativeView.getAdSourceView()
                .setVisibility(null != nativeAd.getAdSource() ? View.VISIBLE : View.INVISIBLE);

        if (null != nativeAd.getCallToAction()) {
            ((Button) nativeView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        // Register a native ad object.
        nativeView.setNativeAd(nativeAd);

        AppDownloadButton appDownloadButton = nativeView.findViewById(R.id.app_download_btn);
        appDownloadButton.setAppDownloadButtonStyle(new MyAppDownloadStyle(parentView.getContext()));
        if (nativeView.register(appDownloadButton)) {
            appDownloadButton.setVisibility(View.VISIBLE);
            appDownloadButton.refreshAppStatus();
            nativeView.getCallToActionView().setVisibility(View.GONE);
        } else {
            appDownloadButton.setVisibility(View.GONE);
            nativeView.getCallToActionView().setVisibility(View.VISIBLE);
        }

        return nativeView;
    }

    /**
     * Custom AppDownloadButton Style
     */
    private static class MyAppDownloadStyle extends AppDownloadButtonStyle {

        public MyAppDownloadStyle(Context context) {
            super(context);
            normalStyle.setTextColor(context.getResources().getColor(R.color.background_Light));
            normalStyle.setBackground(ContextCompat.getDrawable(context,R.drawable.native_button_rounded_corners_shape));
            processingStyle.setTextColor(context.getResources().getColor(R.color.background_Night));
        }
    }
}


