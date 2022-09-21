package com.dog.unlimited.secure.fast.dog

import android.app.ActivityManager
import android.app.Application
import com.dog.unlimited.secure.fast.Connect0907Ac
import com.dog.unlimited.secure.fast.ss0907server.SsServer0907Ma
import com.github.shadowsocks.Core
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.tencent.mmkv.MMKV

class Dog:Application() {
    override fun onCreate() {
        super.onCreate()
        Core.init(this,Connect0907Ac::class)
        MMKV.initialize(this)
        if (!packageName.equals(name(this))){
            return
        }
        Firebase.initialize(this)
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