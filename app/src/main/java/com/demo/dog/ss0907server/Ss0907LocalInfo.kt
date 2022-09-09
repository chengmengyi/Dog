package com.demo.dog.ss0907server

import com.demo.dog.ss0907bean.Ss0807ServerData

class Ss0907LocalInfo {
    companion object{
        const val EMAIL_STR=""
        const val AGREE_STR=""

        val localServerList= arrayListOf(
            Ss0807ServerData(
                dog_0907_host = "100.223.52.0",
                dog_0907_pwd = "123456",
                dog_0907_country = "Japan",
                dog_0907_city = "Tokyo",
                dog_0907_port = 100,
                dog_0907_method = "chacha20-ietf-poly1305"
            ),
            Ss0807ServerData(
                dog_0907_host = "100.223.52.78",
                dog_0907_pwd = "123456",
                dog_0907_country = "unitedStates",
                dog_0907_city = "Tokyo",
                dog_0907_port = 100,
                dog_0907_method = "chacha20-ietf-poly1305"
            )
        )

//        val localServerList= arrayListOf(
//            Ss0807ServerData(
//                dog_0907_host = "185.172.113.78",
//                dog_0907_pwd = "l40ofbwPpFwKCWBdXP4E",
//                dog_0907_country = "Japan",
//                dog_0907_city = "Tokyo",
//                dog_0907_port = 2828,
//                dog_0907_method = "chacha20-ietf-poly1305"
//            ),
//            Ss0807ServerData(
//                dog_0907_host = "79.133.124.170",
//                dog_0907_pwd = "l40ofbwPpFwKCWBdXP4E",
//                dog_0907_country = "UnitedStates",
//                dog_0907_city = "Chicago",
//                dog_0907_port = 2828,
//                dog_0907_method = "chacha20-ietf-poly1305"
//            )
//        )




        const val RAO_JSON="""{
    "state":1,
    "name":[
   
    ]
}"""
    }
}