package com.demo.dog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demo.dog.dog.height
import com.gyf.immersionbar.ImmersionBar

abstract class Abs0907Ac:AppCompatActivity() {
    protected var immersionBar:ImmersionBar?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        height()
        setContentView(layout())
        immersionBar= ImmersionBar.with(this).apply {
            statusBarAlpha(0f)
            autoDarkModeEnable(true)
            statusBarDarkFont(true)
            init()
        }
        view()
    }

    abstract fun layout():Int

    abstract fun view()
}