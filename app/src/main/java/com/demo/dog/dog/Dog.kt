package com.demo.dog.dog

import android.app.ActivityManager
import android.app.Application
import com.demo.dog.Connect0907Ac
import com.demo.dog.ss0907server.SsServer0907Ma
import com.github.shadowsocks.Core
import com.tencent.mmkv.MMKV


lateinit var mDog: Dog
class Dog:Application() {
    override fun onCreate() {
        super.onCreate()
        mDog=this
        Core.init(this,Connect0907Ac::class)
        MMKV.initialize(this)
        if (!packageName.equals(name(this))){
            return
        }
        SsServer0907Ma.firebase()
        Ac0907Life.registerActivityLifecycleCallbacks(this)
    }

    private fun name(applicationContext: Application): String {
        val pid = android.os.Process.myPid()
        var processName = ""
        val manager = applicationContext.getSystemService(Application.ACTIVITY_SERVICE) as ActivityManager
        for (process in manager.runningAppProcesses) {
            if (process.pid === pid) {
                processName = process.processName
            }
        }
        return processName
    }

}