package com.manuel28g.test.paging3.repository

import com.manuel28g.test.paging3.api.BinanceAPI
import com.manuel28g.test.paging3.data.CryptoCurrency

class BinanceRepositoryImpl(private val api: BinanceAPI): BinanceRepository {
    override fun getData() :List<CryptoCurrency>?{
        return api.getData().execute().body()
    }
}