package com.demo.dog

import android.animation.ValueAnimator
import android.content.Intent
import android.view.KeyEvent
import android.view.animation.LinearInterpolator
import androidx.core.animation.doOnEnd
import com.blankj.utilcode.util.ActivityUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Abs0907Ac() {
    private var animator: ValueAnimator?=null

    override fun layout(): Int {
        return R.layout.activity_main
    }

    override fun view() {
        animator=ValueAnimator.ofInt(0, 100).apply {
            duration=3000L
            interpolator = LinearInterpolator()
            addUpdateListener {
                val progress = it.animatedValue as Int
                main_progress.progress = progress
            }
            doOnEnd {
                toNextAc()
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