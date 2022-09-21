package com.dog.unlimited.secure.fast

import com.dog.unlimited.secure.fast.dog.getServerIcon
import com.dog.unlimited.secure.fast.interfaces.IConnectTimeListener
import com.dog.unlimited.secure.fast.ss0907server.Ss0907ConnectMa
import com.dog.unlimited.secure.fast.ss0907server.Ss0907TimeMa
import kotlinx.android.synthetic.main.activity_connect_result.*

class ConnectResult0907Ac:Abs0907Ac(), IConnectTimeListener {
    private var booleanExtra=false

    override fun layout(): Int {
        return R.layout.activity_connect_result
    }

    override fun view() {
        immersionBar?.statusBarView(top)?.init()
        iv_finish.setOnClickListener { finish() }

        booleanExtra = intent.getBooleanExtra("con", false)
        bg_view.isSelected=booleanExtra
        tv_result_time.isSelected=booleanExtra
        llc_server_bg.isSelected=booleanExtra
        iv_result_connect_status.isSelected=booleanExtra
        tv_result_status.text=if (booleanExtra) "Connected" else "Disconnected"

        val lastServerData = Ss0907ConnectMa.getLastServerData()
        tv_result_server_name.text=lastServerData.dog_0907_country
        iv_result_icon.setImageResource(getServerIcon(lastServerData.dog_0907_country))

        if (booleanExtra){
            Ss0907TimeMa.setListener(this)
        }else{
            updateConnectTime(Ss0907TimeMa.t())
        }
    }

    override fun updateConnectTime(time: String) {
        tv_result_time.text=time
    }

    override fun onDestroy() {
        super.onDestroy()
        if (booleanExtra){
            Ss0907TimeMa.setListener(null)
        }
    }
}