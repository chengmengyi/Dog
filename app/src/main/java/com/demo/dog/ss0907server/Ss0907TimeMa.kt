package com.demo.dog.ss0907server

import com.demo.dog.interfaces.IConnectTimeListener
import kotlinx.coroutines.*
import java.lang.Exception

object Ss0907TimeMa {
    private var time=0L
    private var iConnectTimeListener:IConnectTimeListener?=null
    private var timeCount:Job?=null

    fun resetTime(){
        time=0L
    }

    fun setListener(iConnectTimeListener:IConnectTimeListener?){
        this.iConnectTimeListener=iConnectTimeListener
    }

    fun startTime0907Count(){
        if (null!= timeCount) return
        timeCount = GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                iConnectTimeListener?.updateConnectTime(t())
                time++
                delay(1000L)
            }
        }
    }

    fun stopTime0907Count(){
        timeCount?.cancel()
        timeCount=null
    }

    fun t():String{
        try {
            val shi=time/3600
            val fen= (time % 3600) / 60
            val miao= (time % 3600) % 60
            val s=if (shi<10) "0${shi}" else shi
            val f=if (fen<10) "0${fen}" else fen
            val m=if (miao<10) "0${miao}" else miao
            return "${s}:${f}:${m}"
        }catch (e: Exception){}
        return "00:00:00"
    }
}