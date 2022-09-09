package com.demo.dog

import kotlinx.android.synthetic.main.activity_agree.*

class Agree0907Ac:Abs0907Ac() {
    override fun layout(): Int {
        return R.layout.activity_agree
    }

    override fun view() {
        immersionBar?.statusBarView(top)?.init()
        iv_finish.setOnClickListener { finish() }
    }
}