package com.dog.unlimited.secure.fast

import android.animation.ValueAnimator
import android.content.Intent
import android.view.KeyEvent
import android.view.animation.LinearInterpolator
import com.blankj.utilcode.util.ActivityUtils
import com.dog.unlimited.secure.fast.load_admob.Ss0907AdType
import com.dog.unlimited.secure.fast.load_admob.Ss0907LoadAdmob
import com.dog.unlimited.secure.fast.show_ad.SsShow0907OpenAd
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Abs0907Ac() {
    private var animator: ValueAnimator?=null
    private val show by lazy { SsShow0907OpenAd(this,Ss0907AdType.AD_OPEN){toNextAc()} }

    override fun layout(): Int {
        return R.layout.activity_main
    }

    override fun view() {
        Ss0907LoadAdmob.callLogic(Ss0907AdType.AD_OPEN)
        Ss0907LoadAdmob.callLogic(Ss0907AdType.AD_CONNECT)
        Ss0907LoadAdmob.callLogic(Ss0907AdType.AD_HOME)
        Ss0907LoadAdmob.callLogic(Ss0907AdType.AD_RESULT)

        animator=ValueAnimator.ofInt(0, 100).apply {
            duration=10000L
            interpolator = LinearInterpolator()
            addUpdateListener {
                val progress = it.animatedValue as Int
                main_progress.progress = progress
                val duration = (10 * (progress / 100.0F)).toInt()
                if (duration in 2..9){
                    if (null!=Ss0907LoadAdmob.getAdResData(Ss0907AdType.AD_OPEN)){
                        stopAnimator()
                        main_progress.progress = 100
                        show.ss0907show()
                    }
                }else if (duration>=10){
                    toNextAc()
                }
            }
            start()
        }
    }

    private fun toNextAc(){
        if (!ActivityUtils.isActivityExistsInStack(Connect0907Ac::class.java)){
            startActivity(Intent(this,Connect0907Ac::class.java))
        }
        finish()
    }

    override fun onPause() {
        super.onPause()
        animator?.pause()
    }

    override fun onResume() {
        super.onResume()
        animator?.resume()
    }


    private fun stopAnimator(){
        animator?.removeAllUpdateListeners()
        animator?.cancel()
        animator=null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAnimator()
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode== KeyEvent.KEYCODE_BACK){
            return true
        }
        return false
    }
}