package com.dog.unlimited.secure.fast

import com.dog.unlimited.secure.fast.ss0907server.Ss0907LocalInfo
import kotlinx.android.synthetic.main.activity_agree.*

class Agree0907Ac:Abs0907Ac() {
    override fun layout(): Int {
        return R.layout.activity_agree
    }

    override fun view() {
        immersionBar?.statusBarView(top)?.init()
        iv_finish.setOnClickListener { finish() }

        webview.apply {
            settings.javaScriptEnabled=true
            loadUrl(Ss0907LocalInfo.AGREE_STR)
        }
    }
}