package com.demo.dog

import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.dog.load_admob.Ss0907AdType
import com.demo.dog.load_admob.Ss0907LoadAdmob
import com.demo.dog.server_adapter.ChooseServer0907Adapter
import com.demo.dog.show_ad.SsShow0907OpenAd
import com.demo.dog.ss0907bean.Ss0907ServerData
import com.demo.dog.ss0907server.Ss0907ConnectMa
import com.demo.dog.ss0907server.SsServer0907Ma
import kotlinx.android.synthetic.main.activity_choose_server.*

class ChooseServer0907Ac:Abs0907Ac() {
    private val backAd by lazy { SsShow0907OpenAd(this, Ss0907AdType.AD_BACK){finish()} }

    
    override fun layout(): Int {
        return R.layout.activity_choose_server
    }

    override fun view() {
        immersionBar?.statusBarView(top)?.init()
        iv_finish.setOnClickListener { onBackPressed() }
        Ss0907LoadAdmob.callLogic(Ss0907AdType.AD_BACK)

        val list= arrayListOf<Ss0907ServerData>()
        list.add(SsServer0907Ma.createFast0907Data())
        list.addAll(SsServer0907Ma.getServer0907List())
        recycler_view.layoutManager=LinearLayoutManager(this)
        recycler_view.adapter=ChooseServer0907Adapter(this,list){
            click(it)
        }
    }

    private fun click(ss0807ServerData: Ss0907ServerData){
        val currentServerData = Ss0907ConnectMa.getCurrentServerData()
        val serverConnected = Ss0907ConnectMa.serverConnected()
        if (currentServerData.dog_0907_host!=ss0807ServerData.dog_0907_host){
            if (serverConnected){
                AlertDialog.Builder(this).apply {
                    setMessage("You are currently connected and need to disconnect before manually connecting to the server.")
                    setPositiveButton("sure") { _, _ ->
                        back("dis",ss0807ServerData)
                    }
                    setNegativeButton("cancel",null)
                    show()
                }
                return
            }else{
                back("con",ss0807ServerData)
            }
        }else{
            if (!serverConnected){
                back("con",ss0807ServerData)
            }else{
                finish()
            }
        }
    }

    private fun back(s:String,serverData: Ss0907ServerData){
        Ss0907ConnectMa.setCurrentServerData(serverData)
        val intent = Intent()
        intent.putExtra("con",s)
        setResult(907,intent)
        finish()
    }

    override fun onBackPressed() {
        val adResData = Ss0907LoadAdmob.getAdResData(Ss0907AdType.AD_BACK)
        if (null!=adResData){
            backAd.ss0907show()
            return
        }
        finish()
    }
}