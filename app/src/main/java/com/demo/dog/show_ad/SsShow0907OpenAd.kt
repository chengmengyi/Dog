package com.demo.dog.show_ad

import com.demo.dog.Abs0907Ac
import com.demo.dog.dog.log0907
import com.demo.dog.load_admob.Ss0907AdType
import com.demo.dog.load_admob.Ss0907LoadAdmob
import com.demo.dog.ss0907server.SsServer0907Ma
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

    fun ss0907show(show:(jump:Boolean)->Unit){
        val adResData = Ss0907LoadAdmob.getAdResData(adType)
        if (SsServer0907Ma.cannotLoadAd()&&null==adResData){
            show.invoke(true)
            return
        }
        if (null!=adResData){
            if (Ss0907LoadAdmob.showing0907Open||!abs0907Ac.resume()){
                log0907("cannot show $adType ad, ${Ss0907LoadAdmob.showing0907Open}...${abs0907Ac.resume()}....")
                show.invoke(false)
                return
            }
            log0907("start show $adType ")
            show.invoke(false)
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
            log0907("==onAdDismissedFullScreenContent===")
            Ss0907LoadAdmob.showing0907Open=false
            showFinish()
        }

        override fun onAdShowedFullScreenContent() {
            super.onAdShowedFullScreenContent()
            SsServer0907Ma.addShowNum()
            Ss0907LoadAdmob.showing0907Open=true
            Ss0907LoadAdmob.deleteAdRes(adType)
        }

        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            super.onAdFailedToShowFullScreenContent(p0)
            Ss0907LoadAdmob.showing0907Open=false
            Ss0907LoadAdmob.deleteAdRes(adType)
            showFinish()
        }

        override fun onAdClicked() {
            super.onAdClicked()
            SsServer0907Ma.addClickNum()
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