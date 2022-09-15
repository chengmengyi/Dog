package com.demo.dog.ss0907bean

import com.github.shadowsocks.database.Profile
import com.github.shadowsocks.database.ProfileManager

class Ss0907ServerData(
    val dog_0907_method:String="",
    val dog_0907_pwd:String="",
    val dog_0907_host:String="",
    val dog_0907_country:String="",
    val dog_0907_port:Int=0,
    val dog_0907_city:String=""
) {

    fun update0907ServerId(){
        val profile = Profile(
            id = 0L,
            host = dog_0907_host,
            password = dog_0907_pwd,
            remotePort = dog_0907_port,
            name = "$dog_0907_country - $dog_0907_city",
            method = dog_0907_method
        )

        var id:Long?=null
        ProfileManager.getActiveProfiles()?.forEach {
            if (it.remotePort==profile.remotePort&&it.host==profile.host){
                id=it.id
                return@forEach
            }
        }
        if (null==id){
            ProfileManager.createProfile(profile)
        }else{
            profile.id=id!!
            ProfileManager.updateProfile(profile)
        }
    }

    fun get0907ServerId():Long{
        ProfileManager.getActiveProfiles()?.forEach {
            if (it.host==dog_0907_host&&it.remotePort==dog_0907_port){
                return it.id
            }
        }
        return 0L
    }


    fun fast()=dog_0907_country=="Super Fast Server"&&dog_0907_host.isEmpty()
}