package com.github.shadowsocks.bg

import android.net.VpnService
import android.util.Log
import androidx.collection.ArraySet
import androidx.collection.arraySetOf
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tencent.mmkv.MMKV
import org.json.JSONObject

class Rao {
    companion object{
        private val blockListPackName = arraySetOf<String>(
            "com.google.android.gms",
            "com.google.android.ext.services",
            "com.google.process.gservices",
            "com.android.vending",
            "com.google.android.gms.persistent",
            "com.google.android.cellbroadcastservice",
            "com.google.android.packageinstaller",
            "com.google.android.gms.location.history",
        )

        private val whiteList= arrayOf(
            "com.android.chrome",
            "com.microsoft.emmx",
            "org.mozilla.firefox",
            "com.opera.browser",
            "com.google.android.googlequicksearchbox",
            "mark.via.gp",
            "com.UCMobile.intl",
            "com.brave.browser",
            "privacy.explorer.fast.safe.browser"
        )

        fun loadConfig(builder: VpnService.Builder,packageName:String) {
            when (getState()) {
                0 -> {
                    runCatching { builder.addDisallowedApplication(packageName) }
                }
                1 -> {
                    blockListPackName.addAll(getBlockListPack(packageName))
                    for (pn in blockListPackName) {
                        runCatching { builder.addDisallowedApplication(pn) }
                    }
                }
                2 -> {
                    for (pn in whiteList) {
                        runCatching { builder.addAllowedApplication(pn) }
                    }
                }
            }
        }

        private fun getState():Int{
            var state=0
            try {
                state= JSONObject(getStateJson()).optInt("state")
            } catch (e: Exception) {
            }
            return state
        }

        private fun getStateJson()= MMKV.mmkvWithID("dog").decodeString("json")

        private fun getBlockListPack(packageName: String): ArraySet<String> {
            val list= arraySetOf<String>()
            try{
                val jsonArray = JSONObject(getStateJson()).getJSONArray("name")
                for (index in 0 until jsonArray.length() ){
                    list.add(jsonArray.optString(index))
                }
                list.add(packageName)
            }catch (e:Exception){

            }
            return list
        }
    }
}