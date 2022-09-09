package com.demo.dog

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.net.VpnService
import android.util.Log
import com.demo.dog.dog.*
import com.demo.dog.interfaces.IConnectStateListener
import com.demo.dog.interfaces.IUpdateConnectUI
import com.demo.dog.ss0907server.Ss0907ConnectMa
import com.demo.dog.ss0907server.Ss0907TimeMa
import com.github.shadowsocks.Core
import com.github.shadowsocks.bg.BaseService
import com.github.shadowsocks.utils.StartService
import kotlinx.android.synthetic.main.activity_connect.*
import kotlinx.android.synthetic.main.layout_bottom.*
import kotlinx.android.synthetic.main.layout_connect.*
import kotlinx.android.synthetic.main.layout_set.*
import kotlinx.coroutines.*

class Connect0907Ac:Abs0907Ac(),IUpdateConnectUI, IConnectStateListener {
    private var can0907Click=true
    private var hadPermission=false
    private var showConnectUI=false
    private var connect=false
    private var connectSuccessJob:Job?=null
    private var objectAnimator: ObjectAnimator?=null


    private val register0907ForActivityResult = registerForActivityResult(StartService()) {
        if (!it && hadPermission) {
            hadPermission = false
            preConnectServer()
        } else {
            can0907Click=true
            show0907Toast("Connected fail")
        }
    }

    override fun layout(): Int {
        return R.layout.activity_connect
    }

    override fun view() {
        immersionBar?.statusBarView(top)?.init()
        Ss0907ConnectMa.init(this,this)
        showConnectOrSetUI()
        updateServer0907Info()
        setClickListener()
    }

    private fun setClickListener(){
        llc_show_connect.setOnClickListener {
            if (can0907Click){
                showConnectOrSetUI()
            }
        }
        llc_show_set.setOnClickListener {
            if (can0907Click){
                showConnectOrSetUI()
            }
        }
        choose_server.setOnClickListener {
            if (can0907Click){
                startActivityForResult(Intent(this,ChooseServer0907Ac::class.java),907)
            }
        }
        iv_connect_btn.setOnClickListener {
            if (can0907Click){
                val serverConnected = Ss0907ConnectMa.serverConnected()
                connectOrDisconnect(!serverConnected)
            }
        }
        llc_contact.setOnClickListener {
            if (can0907Click){
                contact()
            }
        }
        llc_policy.setOnClickListener {
            if (can0907Click){
                startActivity(Intent(this,Agree0907Ac::class.java))
            }
        }
        llc_share.setOnClickListener {
            if (can0907Click){
                share()
            }
        }
        llc_update.setOnClickListener {
            if (can0907Click){
                update()
            }
        }
    }

    private fun connectOrDisconnect(connect:Boolean){
        can0907Click=false
        if (connect){
            preConnectServer()
        }else{
            updateConnectingUI()
            Ss0907ConnectMa.disconnectServer()
            checkConnectOrDisconnectSuccess(false)
        }
    }

    private fun preConnectServer(){
        if (showNoNetDialog()){
            return
        }
        if (VpnService.prepare(this) != null) {
            hadPermission = true
            register0907ForActivityResult.launch(null)
            return
        }
        updateServer0907Info()
        Ss0907ConnectMa.connectServer()
        Ss0907TimeMa.resetTime()
        updateConnectingUI()
        checkConnectOrDisconnectSuccess(true)
    }

    private fun startRotateIcon(){
        objectAnimator=ObjectAnimator.ofFloat(iv_connect_status, "rotation", 0f, 360f).apply {
            duration=1000L
            repeatCount= ValueAnimator.INFINITE
            repeatMode=ObjectAnimator.RESTART
            start()
        }
    }

    private fun stopRotateIcon(){
        iv_connect_status.rotation=0F
        objectAnimator?.cancel()
        objectAnimator=null
    }

    private fun checkConnectOrDisconnectSuccess(connect: Boolean){
        this.connect=connect
        connectSuccessJob=GlobalScope.launch(Dispatchers.Main) {
            var time=0
            while (true){
                if (!isActive){
                    break
                }
                delay(1000)
                time++
                if (time>=10){
                    cancel()
                    result()
                }
                val bool=if (connect) Ss0907ConnectMa.serverConnected() else Ss0907ConnectMa.serverStopped()
                if (bool){
                    cancel()
                    result()
                }
            }
        }
    }

    private fun result(jumpRsult:Boolean=true){
        val bool=if (connect) Ss0907ConnectMa.serverConnected() else Ss0907ConnectMa.serverStopped()
        if (bool){
            if (connect){
                updateConnectedUI()
            }else{
                updateStoppedUI()
                updateServer0907Info()
            }
            if (jumpRsult){
                jumpResult()
            }
            can0907Click=true
        }else{
            updateStoppedUI()
            show0907Toast(if (connect) "Connect Fail" else "Disconnect Fail")
            can0907Click=true
        }
    }

    private fun jumpResult(){
        if (Ac0907Life.front){
            startActivity(Intent(this,ConnectResult0907Ac::class.java).apply {
                putExtra("con",connect)
            })
        }
    }

    private fun showConnectOrSetUI(){
        showConnectUI=!showConnectUI
        layout_connect.show(showConnectUI)
        layout_set.show(!showConnectUI)
        llc_show_connect.isSelected=showConnectUI
        llc_show_set.isSelected=!showConnectUI
    }

    private fun updateServer0907Info(){
        val currentServerData = Ss0907ConnectMa.getCurrentServerData()
        tv_server_name.text=currentServerData.dog_0907_country
        iv_server_icon.setImageResource(getServerIcon(currentServerData.dog_0907_city))
    }

    override fun updateStoppedUI() {
        stopRotateIcon()
        connect_idle.show(true)
        connecting_view.show(false)
        iv_connect_status.setImageResource(R.drawable.connect_btn_icon)
        iv_connect_btn.setImageResource(R.drawable.connect_bg)
    }

    override fun updateConnectingUI() {
        startRotateIcon()
        connect_idle.show(false)
        connecting_view.show(true)
        iv_connect_btn.setImageResource(R.drawable.connect_bg)
        iv_connect_status.setImageResource(R.drawable.connect_btn_icon)
    }

    override fun updateConnectedUI() {
        stopRotateIcon()
        connect_idle.show(true)
        connecting_view.show(false)
        iv_connect_btn.setImageResource(R.drawable.connected_bg)
        iv_connect_status.setImageResource(R.drawable.connected_btn_icon)
    }

    override fun serverConnected() {
        updateConnectedUI()
    }

    override fun serverStopped() {
        if (can0907Click){
            updateStoppedUI()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==907){
            if (!(data?.getStringExtra("con").isNullOrEmpty())){
                iv_connect_btn.performClick()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopRotateIcon()
        connectSuccessJob?.cancel()
        Ss0907ConnectMa.onDestroy()
    }
}