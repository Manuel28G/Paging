package com.manuel28g.test.paging3.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Crypto",
    indices = [Index(value = ["binance"], unique = false)])
data class CryptoCurrency(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("symbol")
    var cryptoPair:String,
    @SerializedName("price")
    val price: Float
)
