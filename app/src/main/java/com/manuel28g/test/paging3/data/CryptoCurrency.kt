package com.manuel28g.test.paging3.data

import com.google.gson.annotations.SerializedName

data class CryptoCurrency(
    @SerializedName("symbol")
    val cryptoPair:String,
    @SerializedName("price")
    val price: Float
)
