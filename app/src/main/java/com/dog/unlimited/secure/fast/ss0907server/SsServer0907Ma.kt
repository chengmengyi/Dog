package com.dog.unlimited.secure.fast.ss0907server

import com.dog.unlimited.secure.fast.ss0907bean.Ss0907ServerData
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

object SsServer0907Ma {
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


        val remoteConfig = Firebase.remoteConfig
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful){
                saveRaoJson(remoteConfig.getString("dog_0907_rao"))
                firebaseCity(remoteConfig.getString("dog_0907_cites"))
                firebaseServers(remoteConfig.getString("dog_0907_servers"))
                firebaseAd(remoteConfig.getString("dog_0907_ad"))
            }
        }
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
        MMKV.defaultMMKV().encode("dog_0907_ad",json)
    }

    fun getAdStr():String{
        val adStr = MMKV.defaultMMKV().decodeString("dog_0907_ad")
        return if (adStr.isNullOrEmpty()) Ss0907LocalInfo.dog0907_AD else adStr
    }
}