package com.dog.unlimited.secure.fast.ss0907server

import com.dog.unlimited.secure.fast.ss0907bean.Ss0807ServerData
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object SsServer0907Ma {


    fun getServer0907List()=Ss0907LocalInfo.localServerList

    fun getFast0907Data()= getServer0907List().random()

    fun createFast0907Data()=Ss0807ServerData(dog_0907_country = "Super Fast Server")


    fun firebase(){
        saveRaoJson(Ss0907LocalInfo.RAO_JSON)
        update0907ServerId(Ss0907LocalInfo.localServerList)


        val remoteConfig = Firebase.remoteConfig
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful){
                saveRaoJson(remoteConfig.getString("dog_0907_rao"))
            }
        }
    }

    private fun update0907ServerId(server0907:ArrayList<Ss0807ServerData>){
        GlobalScope.launch {
            server0907.forEach {
                it.update0907ServerId()
            }
        }
    }

    private fun saveRaoJson(string: String){
        MMKV.mmkvWithID("dog").encode("json",string)
    }
}