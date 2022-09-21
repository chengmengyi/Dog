package com.dog.dog.load_admob

import com.dog.dog.dog.log0907
import com.dog.dog.ss0907bean.Ss0907AdData

object Ss0907LoadAdmob:Abs0907Ad(){
    fun callLogic(adType:String,again:Boolean=true){
        if (checkLoading(adType)) return

        if (checkHadRes(adType)) return

        val parseAdList = parseAdList(adType)
        if (parseAdList.isNullOrEmpty()){
            log0907("$adType ad list null")
            return
        }
        load.add(adType)
        preLoad(adType,parseAdList.iterator(),again)
    }

    private fun preLoad(adType: String, iterator: Iterator<Ss0907AdData>,again:Boolean=true){
        val data = iterator.next()
        log0907("start load ad,id:${data.id_dog0907},sort:${data.sort_dog0907},type:${data.type_dog0907}")
        loadAdByType(
            adType,
            data,
            result = {
                if (it.failMsg.isNullOrEmpty()){
                    log0907("load ad success $adType")
                    load.remove(adType)
                    adMap[adType]=it
                }else{
                    log0907("load ad fail $adType,,,,${it.failMsg}")
                    if (iterator.hasNext()){
                        preLoad(adType,iterator,again)
                    }else{
                        load.remove(adType)
                        if (adType==Ss0907AdType.AD_OPEN&&again){
                            callLogic(adType,again = false)
                        }
                    }
                }
            }
        )
    }

    fun getAdResData(adType: String)=adMap[adType]?.adRes

    override fun deleteAdRes(adType: String) {
        adMap.remove(adType)
    }
}