package com.theeclipsio.floppyneon;

import android.os.Bundle;
import com.getcapacitor.BridgeActivity;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.LoadAdError;

public class MainActivity extends BridgeActivity {

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MobileAds.initialize(this, initializationStatus -> {});

        loadInterstitialAd();
    }

    private void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,
            "ca-app-pub-1235003260250332/3993727693",
            adRequest,
            new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(InterstitialAd interstitialAd) {
                    mInterstitialAd = interstitialAd;
                    mInterstitialAd.show(MainActivity.this);
                }

                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    mInterstitialAd = null;
                }
            });
    }
}
