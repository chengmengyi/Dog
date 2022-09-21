package com.dog.dog.show_ad

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import com.blankj.utilcode.util.SizeUtils
import com.dog.dog.Abs0907Ac
import com.dog.dog.R
import com.dog.dog.dog.Ac0907Life
import com.dog.dog.dog.log0907
import com.dog.dog.dog.show
import com.dog.dog.load_admob.Ss0907AdType
import com.dog.dog.load_admob.Ss0907LoadAdmob
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import kotlinx.coroutines.*

class SsCheck0907NativeAd(
    private val abs0907Ac: Abs0907Ac,
    private val adType:String,
) {
    private var launch:Job?=null
    private var historyAdRes:NativeAd?=null

    fun loop(){
        Ss0907LoadAdmob.callLogic(adType)
        if (null!=launch){
            stopJob()
        }
        launch = GlobalScope.launch(Dispatchers.Main) {
            delay(300L)
            if (abs0907Ac.resume()) {
                while (true) {
                    if (!isActive) {
                        break
                    }
                    val adRes = Ss0907LoadAdmob.getAdResData(adType)
                    if (abs0907Ac.resume() && null != adRes && adRes is NativeAd) {
                        cancel()
                        if (historyAdRes != null) {
                            historyAdRes!!.destroy()
                        }
                        historyAdRes = adRes
                        showHomeAd(adRes)
                    }
                    delay(1000L)
                }
            }
        }
    }

    private fun showHomeAd(ad: NativeAd){
        log0907("start load $adType")
        val native_ad_view=abs0907Ac.findViewById<NativeAdView>(R.id.nv_ad)
        native_ad_view.mediaView=abs0907Ac.findViewById(R.id.mv_ad_cover)
        if (null!=ad.mediaContent){
            native_ad_view.mediaView?.apply {
                setMediaContent(ad.mediaContent)
                setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                outlineProvider = object : ViewOutlineProvider() {
                    override fun getOutline(view: View?, outline: Outline?) {
                        if (view == null || outline == null) return
                        outline.setRoundRect(
                            0,
                            0,
                            view.width,
                            view.height,
                            SizeUtils.dp2px(10F).toFloat()
                        )
                        view.clipToOutline = true
                    }
                }
            }
        }
        native_ad_view.headlineView=abs0907Ac.findViewById(R.id.tv_ad_title)
        (native_ad_view.headlineView as AppCompatTextView).text=ad.headline

        native_ad_view.bodyView=abs0907Ac.findViewById(R.id.tv_ad_desc)
        (native_ad_view.bodyView as AppCompatTextView).text=ad.body

        native_ad_view.iconView=abs0907Ac.findViewById(R.id.iv_ad_logo)
        (native_ad_view.iconView as ImageFilterView).setImageDrawable(ad.icon?.drawable)

        native_ad_view.callToActionView=abs0907Ac.findViewById(R.id.tv_ad_btn)
        (native_ad_view.callToActionView as AppCompatTextView).text=ad.callToAction

        native_ad_view.setNativeAd(ad)

        abs0907Ac.findViewById<AppCompatImageView>(R.id.iv_cover).show(false)

        if (adType==Ss0907AdType.AD_HOME){
            Ac0907Life.updateHomeNativeAd=false
        }
        Ss0907LoadAdmob.deleteAdRes(adType)
        Ss0907LoadAdmob.callLogic(adType)
    }

    fun stopJob(){
        launch?.cancel()
        launch=null
    }
    
}