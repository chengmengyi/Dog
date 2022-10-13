package com.dog.unlimited.secure.fast.load_admob

import com.dog.unlimited.secure.fast.dog.log0907
import com.dog.unlimited.secure.fast.dog.mDog
import com.dog.unlimited.secure.fast.ss0907bean.Ss0907AdData
import com.dog.unlimited.secure.fast.ss0907bean.Ss0907AdRes
import com.dog.unlimited.secure.fast.ss0907server.SsServer0907Ma
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAdOptions
import org.json.JSONObject

abstract class Abs0907Ad {
    var showing0907Open=false
    protected val adMap= hashMapOf<String, Ss0907AdRes>()
    protected val load= arrayListOf<String>()

    protected fun checkLoading(adType: String):Boolean{
        return if (load.contains(adType)){
            log0907("$adType loading")
            true
        }else {
            false
        }
    }

    protected fun checkHadRes(adType: String):Boolean{
        if (adMap.containsKey(adType)){
            val ad0906Res = adMap[adType]
            if (ad0906Res?.adRes!=null){
                val ex = (System.currentTimeMillis() - ad0906Res.time) >= 1000L * 60L * 60L
                if (ex){
                    deleteAdRes(adType)
                }else{
                    log0907("$adType had cache")
                    return true
                }
            }
        }
        return false
    }

    protected fun parseAdList(adType: String):List<Ss0907AdData>{
        val list= arrayListOf<Ss0907AdData>()
        try {
            val jsonArray = JSONObject(SsServer0907Ma.getAdStr()).getJSONArray(adType)
            for (index in 0 until jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(index)
                val admobInfo0826Be = Ss0907AdData(
                    jsonObject.optString("dog0907_id"),
                    jsonObject.optInt("dog0907_sort"),
                    jsonObject.optString("dog0907_type"),
                    jsonObject.optString("dog0907_source"),
                )
                list.add(admobInfo0826Be)
            }
        }catch (e:Exception){}
        return list.filter { it.source_dog0907 == "admob" }.sortedByDescending { it.sort_dog0907 }
    }

    abstract fun deleteAdRes(adType: String)

    protected fun loadAdByType(adType: String,ss0907AdData: Ss0907AdData,result:(adRes:Ss0907AdRes)->Unit){
        when(ss0907AdData.type_dog0907){
            "kaiping"->{
                AppOpenAd.load(
                    mDog,
                    ss0907AdData.id_dog0907,
                    AdRequest.Builder().build(),
                    AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                    object : AppOpenAd.AppOpenAdLoadCallback(){
                        override fun onAdLoaded(p0: AppOpenAd) {
                            super.onAdLoaded(p0)
                            result.invoke(Ss0907AdRes(time = System.currentTimeMillis(), adRes = p0))
                        }

                        override fun onAdFailedToLoad(p0: LoadAdError) {
                            super.onAdFailedToLoad(p0)
                            result.invoke(Ss0907AdRes(failMsg = p0.message))
                        }
                    }
                )
            }
            "chaping"->{
                InterstitialAd.load(
                    mDog,
                    ss0907AdData.id_dog0907,
                    AdRequest.Builder().build(),
                    object : InterstitialAdLoadCallback(){
                        override fun onAdFailedToLoad(p0: LoadAdError) {
                            result.invoke(Ss0907AdRes(failMsg = p0.message))
                        }

                        override fun onAdLoaded(p0: InterstitialAd) {
                            result.invoke(Ss0907AdRes(time = System.currentTimeMillis(), adRes = p0))
                        }
                    }
                )
            }
            "yuansheng"->{
                AdLoader.Builder(
                    mDog,
                    ss0907AdData.id_dog0907,
                ).forNativeAd {
                    result.invoke(Ss0907AdRes(time = System.currentTimeMillis(), adRes = it))
                }
                    .withAdListener(object : AdListener(){
                        override fun onAdFailedToLoad(p0: LoadAdError) {
                            super.onAdFailedToLoad(p0)
                            result.invoke(Ss0907AdRes(failMsg = p0.message))
                        }
                    })
                    .withNativeAdOptions(
                        NativeAdOptions.Builder()
                            .setAdChoicesPlacement(
                                NativeAdOptions.ADCHOICES_TOP_LEFT
                            )
                            .build()
                    )
                    .build()
                    .loadAd(AdRequest.Builder().build())
            }
        }
    }
}