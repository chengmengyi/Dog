package com.dog.unlimited.secure.fast

import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.dog.unlimited.secure.fast.server_adapter.ChooseServer0907Adapter
import com.dog.unlimited.secure.fast.ss0907bean.Ss0807ServerData
import com.dog.unlimited.secure.fast.ss0907server.Ss0907ConnectMa
import com.dog.unlimited.secure.fast.ss0907server.SsServer0907Ma
import kotlinx.android.synthetic.main.activity_choose_server.*

class ChooseServer0907Ac:Abs0907Ac() {
    override fun layout(): Int {
        return R.layout.activity_choose_server
    }

    override fun view() {
        immersionBar?.statusBarView(top)?.init()
        iv_finish.setOnClickListener { finish() }

        val list= arrayListOf<Ss0807ServerData>()
        list.add(SsServer0907Ma.createFast0907Data())
        list.addAll(SsServer0907Ma.getServer0907List())
        recycler_view.layoutManager=LinearLayoutManager(this)
        recycler_view.adapter=ChooseServer0907Adapter(this,list){
            click(it)
        }
    }

    private fun click(ss0807ServerData: Ss0807ServerData){
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

    private fun back(s:String,serverData: Ss0807ServerData){
        Ss0907ConnectMa.setCurrentServerData(serverData)
        val intent = Intent()
        intent.putExtra("con",s)
        setResult(907,intent)
        finish()
    }
}