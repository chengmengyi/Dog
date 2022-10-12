package com.demo.dog.ss0907server

import android.annotation.SuppressLint
import com.demo.dog.dog.log0907
import com.demo.dog.ss0907bean.Ss0907ServerData
import com.google.firebase.ktx.Firebase
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object SsServer0907Ma {
    private var clickMaxNum=15
    private var showMaxNum=50

    private var currentClickNum=0
    private var currentShowNum=0
    val citesList= arrayListOf<String>()
    val serversList= arrayListOf<Ss0907ServerData>()

    fun getServer0907List():ArrayList<Ss0907ServerData>{
        return if (serversList.isEmpty()) Ss0907LocalInfo.localServerList
        else serversList
    }

    fun getFast0907Data():Ss0907ServerData{
        val serverList = getServer0907List()
        if (!citesList.isNullOrEmpty()){
            val filter = serverList.filter { citesList.contains(it.dog_0907_city) }
            if (!filter.isNullOrEmpty()){
                return filter.random()
            }
        }
        return serverList.random()
    }

    fun createFast0907Data()=Ss0907ServerData(dog_0907_country = "Super Fast Server")


    fun firebase(){
        saveRaoJson(Ss0907LocalInfo.RAO_JSON)
        update0907ServerId(Ss0907LocalInfo.localServerList)
        getCurrentShowNum()
        getCurrentClickNum()


//        val remoteConfig = Firebase.remoteConfig
//        remoteConfig.fetchAndActivate().addOnCompleteListener {
//            if (it.isSuccessful){
//                saveRaoJson(remoteConfig.getString("dog_0907_rao"))
//                firebaseCity(remoteConfig.getString("dog_0907_cites"))
//                firebaseServers(remoteConfig.getString("dog_0907_servers"))
//                firebaseAd(remoteConfig.getString("dog_0907_ad"))
//            }
//        }
    }

    private fun update0907ServerId(server0907:ArrayList<Ss0907ServerData>){
        GlobalScope.launch {
            server0907.forEach {
                it.update0907ServerId()
            }
        }
    }

    private fun saveRaoJson(string: String){
        MMKV.mmkvWithID("dog").encode("json",string)
    }
    
    private fun firebaseCity(json:String){
        try {
            citesList.clear()
            val jsonArray = JSONObject(json).getJSONArray("dog_0907_cites")
            for (index in 0 until jsonArray.length()){
                citesList.add(jsonArray.optString(index))
            }
        }catch (e:Exception){

        }
    }
    
    private fun firebaseServers(json: String){
        serversList.clear()
        try {
            val jsonArray = JSONObject(json).getJSONArray("dog_0907_servers")
            for (index in 0 until jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(index)
                val server = Ss0907ServerData(
                    jsonObject.optString("method_dog0907"),
                    jsonObject.optString("pwd_dog0907"),
                    jsonObject.optString("host_dog0907"),
                    jsonObject.optString("country_dog0907"),
                    jsonObject.optInt("port_dog0907"),
                    jsonObject.optString("city_dog0907"),
                )
                serversList.add(server)
            }
            update0907ServerId(serversList)
        }catch (e:Exception){

        }
    }

    private fun firebaseAd(json: String){
        try {
            val jsonObject = JSONObject(json)
            clickMaxNum=jsonObject.optInt("dog0907_click")
            showMaxNum=jsonObject.optInt("dog0907_show")
        }catch (e:Exception){

        }
        MMKV.defaultMMKV().encode("dog_0907_ad",json)
    }

    fun getAdStr():String{
        val adStr = MMKV.defaultMMKV().decodeString("dog_0907_ad")
        return if (adStr.isNullOrEmpty()) Ss0907LocalInfo.dog0907_AD else adStr
    }

    fun cannotLoadAd():Boolean{
        return currentShowNum>= showMaxNum||currentClickNum>= clickMaxNum
    }

    fun addShowNum(){
        currentShowNum++
        try {
            MMKV.defaultMMKV().encode("${getCurrentTime()}..show",currentShowNum)
        }catch (e:Exception){

        }
    }

    fun addClickNum(){
        currentClickNum++
        try {
            MMKV.defaultMMKV().encode("${getCurrentTime()}..click",currentClickNum)
        }catch (e:Exception){

        }
    }

    private fun getCurrentShowNum(){
        currentShowNum=MMKV.defaultMMKV().decodeInt("${getCurrentTime()}..show",0)
    }

    private fun getCurrentClickNum(){
        currentClickNum=MMKV.defaultMMKV().decodeInt("${getCurrentTime()}..click",0)
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentTime()=SimpleDateFormat("yyyy-MM-dd").format(Date(System.currentTimeMillis()))
}