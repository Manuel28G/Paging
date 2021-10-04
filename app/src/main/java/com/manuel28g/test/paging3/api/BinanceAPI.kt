package com.manuel28g.test.paging3.api

import com.manuel28g.test.paging3.data.CryptoCurrency
import retrofit2.Call
import retrofit2.http.GET

interface BinanceAPI {
        @GET("ticker/price")
        fun getData(): Call<List<CryptoCurrency>>
}