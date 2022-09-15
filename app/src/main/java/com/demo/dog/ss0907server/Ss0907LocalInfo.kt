package com.demo.dog.ss0907server

import com.demo.dog.ss0907bean.Ss0907ServerData

class Ss0907LocalInfo {
    companion object{
        const val EMAIL_STR=""
        const val AGREE_STR=""

        val localServerList= arrayListOf(
            Ss0907ServerData(
                dog_0907_host = "100.223.52.0",
                dog_0907_pwd = "123456",
                dog_0907_country = "Japan",
                dog_0907_city = "Tokyo",
                dog_0907_port = 100,
                dog_0907_method = "chacha20-ietf-poly1305"
            ),
            Ss0907ServerData(
                dog_0907_host = "100.223.52.78",
                dog_0907_pwd = "123456",
                dog_0907_country = "unitedStates",
                dog_0907_city = "Tokyo",
                dog_0907_port = 100,
                dog_0907_method = "chacha20-ietf-poly1305"
            )
        )

        const val RAO_JSON="""{
    "state":1,
    "name":[
   
    ]
}"""


        const val dog0907_AD="""{
    "dog0907_open": [
        {
            "dog0907_source": "admob",
            "dog0907_id": "ca-app-pub-3940256099942544/3419835294AA",
            "dog0907_type": "chaping",
            "dog0907_sort": 1
        },
        {
            "dog0907_source": "admob",
            "dog0907_id": "ca-app-pub-3940256099942544/3419835294A",
            "dog0907_type": "kaiping",
            "dog0907_sort": 2
        },
        {
            "dog0907_source": "admob",
            "dog0907_id": "ca-app-pub-3940256099942544/3419835294AA",
            "dog0907_type": "kaiping",
            "dog0907_sort": 3
        }
    ],
    "dog0907_home": [
        {
            "dog0907_source": "admob",
            "dog0907_id": "ca-app-pub-3940256099942544/2247696110A",
            "dog0907_type": "yuansheng",
            "dog0907_sort": 2
        },
        {
            "dog0907_source": "admob",
            "dog0907_id": "ca-app-pub-3940256099942544/2247696110",
            "dog0907_type": "yuansheng",
            "dog0907_sort": 1
        },
        {
            "dog0907_source": "admob",
            "dog0907_id": "ca-app-pub-3940256099942544/2247696110AA",
            "dog0907_type": "yuansheng",
            "dog0907_sort": 3
        }
    ],
    "dog0907_result": [
        {
            "dog0907_source": "admob",
            "dog0907_id": "ca-app-pub-3940256099942544/2247696110A",
            "dog0907_type": "yuansheng",
            "dog0907_sort": 2
        },
        {
            "dog0907_source": "admob",
            "dog0907_id": "ca-app-pub-3940256099942544/2247696110AA",
            "dog0907_type": "yuansheng",
            "dog0907_sort": 1
        },
        {
            "dog0907_source": "admob",
            "dog0907_id": "ca-app-pub-3940256099942544/2247696110AA",
            "dog0907_type": "yuansheng",
            "dog0907_sort": 3
        }
    ],
    "dog0907_connect": [
        {
            "dog0907_source": "admob",
            "dog0907_id": "ca-app-pub-3940256099942544/8691691433A",
            "dog0907_type": "chaping",
            "dog0907_sort": 2
        },
        {
            "dog0907_source": "admob",
            "dog0907_id": "ca-app-pub-3940256099942544/1033173712AA",
            "dog0907_type": "chaping",
            "dog0907_sort": 1
        },
        {
            "dog0907_source": "admob",
            "dog0907_id": "ca-app-pub-3940256099942544/1033173712AA",
            "dog0907_type": "chaping",
            "dog0907_sort": 3
        }
    ],
    "dog0907_back": [
        {
            "dog0907_source": "admob",
            "dog0907_id": "ca-app-pub-3940256099942544/1033173712A",
            "dog0907_type": "chaping",
            "dog0907_sort": 2
        },
        {
            "dog0907_source": "admob",
            "dog0907_id": "ca-app-pub-3940256099942544/8691691433",
            "dog0907_type": "chaping",
            "dog0907_sort": 1
        },
        {
            "dog0907_source": "admob",
            "dog0907_id": "ca-app-pub-3940256099942544/1033173712AA",
            "dog0907_type": "chaping",
            "dog0907_sort": 3
        }
    ]
}"""
        
    }
}