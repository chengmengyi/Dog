package com.dog.unlimited.secure.fast.ss0907server

import com.dog.unlimited.secure.fast.ss0907bean.Ss0807ServerData

class Ss0907LocalInfo {
    companion object{
        const val EMAIL_STR="xulinn4935e@gmail.com"
        const val AGREE_STR="https://sites.google.com/view/fastdogapp/%E9%A6%96%E9%A0%81"


        val localServerList= arrayListOf(
            Ss0807ServerData(
                dog_0907_host = "185.172.113.78",
                dog_0907_pwd = "l40ofbwPpFwKCWBdXP4E",
                dog_0907_country = "Japan",
                dog_0907_city = "Tokyo",
                dog_0907_port = 2828,
                dog_0907_method = "chacha20-ietf-poly1305"
            ),
            Ss0807ServerData(
                dog_0907_host = "79.133.124.170",
                dog_0907_pwd = "l40ofbwPpFwKCWBdXP4E",
                dog_0907_country = "UnitedStates",
                dog_0907_city = "Chicago",
                dog_0907_port = 2828,
                dog_0907_method = "chacha20-ietf-poly1305"
            )
        )

        const val RAO_JSON="""{
    "state":1,
    "name":[
   
    ]
}"""
    }
}