package com.dog.dog.dog

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.ActivityUtils
import com.dog.dog.Connect0907Ac
import com.dog.dog.MainActivity
import com.google.android.gms.ads.AdActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object Ac0907Life {
    var front=true
    var updateHomeNativeAd=true
    private var startMain=false
    private var job0907: Job?=null


    fun registerActivityLifecycleCallbacks(application: Application){
        application.registerActivityLifecycleCallbacks(callback)
    }

    private val callback=object : Application.ActivityLifecycleCallbacks{
        private var acNum=0
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

        override fun onActivityStarted(activity: Activity) {
            acNum++
            stopJob()
            if (acNum==1){
                front=true
                if (startMain){
                    updateHomeNativeAd=true
                    startMain(activity)
                }
                startMain=false
            }
        }

        override fun onActivityResumed(activity: Activity) {}

        override fun onActivityPaused(activity: Activity) {}

        override fun onActivityStopped(activity: Activity) {
            acNum--
            if (acNum<=0){
                front=false
                startJob()
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

        override fun onActivityDestroyed(activity: Activity) {}

        private fun stopJob(){
            job0907?.cancel()
            job0907=null
        }

        private fun startJob(){
            job0907= GlobalScope.launch {
                delay(3000L)
                startMain=true
                    ActivityUtils.finishActivity(MainActivity::class.java)
                    ActivityUtils.finishActivity(AdActivity::class.java)
            }
        }

        private fun startMain(activity: Activity) {
            if (ActivityUtils.isActivityExistsInStack(Connect0907Ac::class.java)){
                activity.startActivity(Intent(activity, MainActivity::class.java))
            }
        }
    }
}