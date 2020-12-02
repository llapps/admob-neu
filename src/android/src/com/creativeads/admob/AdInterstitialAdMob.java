package com.creativeads.admob;

import android.content.Context;
import android.os.Bundle;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import com.creativeads.AbstractAdInterstitial;

public class AdInterstitialAdMob extends AbstractAdInterstitial {
    private InterstitialAd _interstitial;
    private boolean adsConsent;
    private boolean isTest;
    private String testDeviceId;
    private int gender;
    private int underAgeOfConsent;

    AdInterstitialAdMob(Context ctx, String adUnit, boolean personalizedAdsConsent, String testDeviceId, boolean isTest, int gender, int underAgeOfConsent) {
        adsConsent = personalizedAdsConsent;
        this.isTest = isTest;
        this.testDeviceId = testDeviceId;
        this.gender = gender;
        this.underAgeOfConsent = underAgeOfConsent;

        _interstitial = new InterstitialAd(ctx);
        _interstitial.setAdUnitId(adUnit);
        _interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                notifyOnDismissed();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Error error = new Error();
                error.code = errorCode;
                error.message = "Error with code: " + errorCode;
                notifyOnFailed(error);
            }

            @Override
            public void onAdLeftApplication() {
                notifyOnClicked();
            }

            @Override
            public void onAdOpened() {
                notifyOnShown();
            }

            @Override
            public void onAdLoaded() {
                notifyOnLoaded();
            }
        });

    }

    @Override
    public void loadAd() {
        //AdRequest adRequest = AdMobUtils.getAdRequest(adsConsent, isTest, testDeviceId, gender, underAgeOfConsent);
        //_interstitial.loadAd(adRequest);
        _interstitial.loadAd(new AdRequest.Builder().build());

    }

    @Override
    public void show() {
        if (_interstitial.isLoaded()) {
            _interstitial.show();
        } else {
            loadAd();
        }

    }

    @Override
    public void destroy() {

    }

}
