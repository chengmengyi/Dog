package com.dog.unlimited.secure.fast.show_ad

import com.dog.unlimited.secure.fast.Abs0907Ac
import com.dog.unlimited.secure.fast.dog.log0907
import com.dog.unlimited.secure.fast.load_admob.Ss0907AdType
import com.dog.unlimited.secure.fast.load_admob.Ss0907LoadAdmob
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SsShow0907OpenAd(
    private val abs0907Ac: Abs0907Ac,
    private val adType:String,
    private val result:()->Unit
) {

    fun ss0907show(){
        if (Ss0907LoadAdmob.showing0907Open||!abs0907Ac.resume()){
            log0907("cannot show $adType ad, ${Ss0907LoadAdmob.showing0907Open}...${abs0907Ac.resume()}")
            return
        }
        val adResData = Ss0907LoadAdmob.getAdResData(adType)
        if (null!=adResData){
            log0907("start show $adType ")
            Ss0907LoadAdmob.showing0907Open=true
            if (adResData is AppOpenAd){
                showOpenAd(adResData)
            }
            if (adResData is InterstitialAd){
                showInterstitialAdAd(adResData)
            }
        }
    }

    private fun showOpenAd(appOpenAd: AppOpenAd){
        appOpenAd.fullScreenContentCallback=callback
        appOpenAd.show(abs0907Ac)
    }

    private fun showInterstitialAdAd(appOpenAd: InterstitialAd){
        appOpenAd.fullScreenContentCallback=callback
        appOpenAd.show(abs0907Ac)
    }

    private val callback=object : FullScreenContentCallback() {
        override fun onAdDismissedFullScreenContent() {
            super.onAdDismissedFullScreenContent()
            Ss0907LoadAdmob.showing0907Open=false
            showFinish()
        }

        override fun onAdShowedFullScreenContent() {
            super.onAdShowedFullScreenContent()
            Ss0907LoadAdmob.showing0907Open=true
            Ss0907LoadAdmob.deleteAdRes(adType)
        }

        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            super.onAdFailedToShowFullScreenContent(p0)
            Ss0907LoadAdmob.showing0907Open=false
            Ss0907LoadAdmob.deleteAdRes(adType)
            showFinish()
        }
    }

    private fun showFinish(){
        if (adType==Ss0907AdType.AD_CONNECT){
            Ss0907LoadAdmob.callLogic(adType)
        }
        GlobalScope.launch(Dispatchers.Main) {
            delay(200L)
            if (abs0907Ac.resume()){
                result.invoke()
            }
        }
    }
}