package com.manuel28g.test.paging3.repository

import com.manuel28g.test.paging3.api.BinanceAPI
import com.manuel28g.test.paging3.data.CryptoCurrency
import kotlinx.coroutines.runBlocking

class BinanceRepositoryImpl(private val api: BinanceAPI): BinanceRepository {
    override fun getData() : List<CryptoCurrency>? =
        runBlocking {
            api.getData().execute().body()
        }
}