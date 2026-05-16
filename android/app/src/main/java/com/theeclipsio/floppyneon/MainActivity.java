package com.theeclipsio.floppyneon;

import android.os.Bundle;
import com.getcapacitor.BridgeActivity;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.AdError;
import android.util.Log;

public class MainActivity extends BridgeActivity {

    private RewardedAd mRewardedAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobileAds.initialize(this, initializationStatus -> {
            loadRewardedAd();
        });
    }

    public void loadRewardedAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this,
            "ca-app-pub-1235003260250332/4788768765",
            adRequest,
            new RewardedAdLoadCallback() {
                @Override
                public void onAdLoaded(RewardedAd rewardedAd) {
                    mRewardedAd = rewardedAd;
                    Log.d("AdMob", "Rewarded ad loaded");
                }
                @Override
                public void onAdFailedToLoad(com.google.android.gms.ads.LoadAdError loadAdError) {
                    mRewardedAd = null;
                    Log.d("AdMob", "Rewarded ad failed: " + loadAdError.getMessage());
                }
            });
    }

    public void showRewardedAd(final com.getcapacitor.PluginCall call) {
        runOnUiThread(() -> {
            if (mRewardedAd != null) {
                mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        mRewardedAd = null;
                        loadRewardedAd();
                    }
                });
                mRewardedAd.show(this, rewardItem -> {
                    getBridge().triggerJSEvent("rewardEarned", "window", "{}");
                });
            } else {
                getBridge().triggerJSEvent("rewardFailed", "window", "{}");
                loadRewardedAd();
            }
        });
    }
}
