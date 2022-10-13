package com.dog.unlimited.secure.fast.ss0907server

import com.dog.unlimited.secure.fast.Abs0907Ac
import com.dog.unlimited.secure.fast.interfaces.IConnectStateListener
import com.dog.unlimited.secure.fast.ss0907bean.Ss0907ServerData
import com.github.shadowsocks.Core
import com.github.shadowsocks.aidl.IShadowsocksService
import com.github.shadowsocks.aidl.ShadowsocksConnection
import com.github.shadowsocks.bg.BaseService
import com.github.shadowsocks.preference.DataStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object Ss0907ConnectMa:ShadowsocksConnection.Callback {
    private var context:Abs0907Ac?=null
    private var connectState=BaseService.State.Idle
    private var currentServerData=SsServer0907Ma.createFast0907Data()
    private var lastServerData=SsServer0907Ma.createFast0907Data()
    private val shadowsocksConnection= ShadowsocksConnection(true)

    private var iConnectStateListener:IConnectStateListener?=null

    fun init(context:Abs0907Ac,iConnectStateListener:IConnectStateListener){
        this.context=context
        this.iConnectStateListener=iConnectStateListener
        shadowsocksConnection.connect(context,this)
    }

    fun setCurrentServerData(ss0807ServerData: Ss0907ServerData){
        this.currentServerData=ss0807ServerData
    }

    fun getCurrentServerData()=currentServerData

    fun getLastServerData()= lastServerData

    fun connectServer(){
        setConnectState(BaseService.State.Connecting)
        GlobalScope.launch {
            if (currentServerData.fast()){
                DataStore.profileId = SsServer0907Ma.getFast0907Data().get0907ServerId()
            }else{
                DataStore.profileId = currentServerData.get0907ServerId()
            }
            Core.startService()
        }
    }

    fun disconnectServer(){
        setConnectState(BaseService.State.Stopping)
        GlobalScope.launch {
            Core.stopService()
        }
    }

    fun serverConnected()= connectState==BaseService.State.Connected

    fun serverStopped()= connectState==BaseService.State.Stopped

    private fun setConnectState(state:BaseService.State){
        this.connectState=state
        if (serverConnected()){
            lastServerData= currentServerData
        }
    }

    override fun stateChanged(state: BaseService.State, profileName: String?, msg: String?) {
        setConnectState(state)
        if (serverConnected()){
            Ss0907TimeMa.startTime0907Count()
        }

        if (serverStopped()){
            Ss0907TimeMa.stopTime0907Count()
            iConnectStateListener?.serverStopped()
        }
    }

    override fun onServiceConnected(service: IShadowsocksService) {
        val state = BaseService.State.values()[service.state]
        setConnectState(state)
        if (serverConnected()){
            Ss0907TimeMa.startTime0907Count()
            iConnectStateListener?.serverConnected()
        }
    }

    override fun onBinderDied() {
        if (null!= context){
            shadowsocksConnection.disconnect(context!!)
        }
    }

    fun onDestroy(){
        onBinderDied()
        iConnectStateListener=null
    }
}