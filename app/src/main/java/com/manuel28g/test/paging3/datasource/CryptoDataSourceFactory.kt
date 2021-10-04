package com.manuel28g.test.paging3.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.manuel28g.test.paging3.api.BinanceAPI
import com.manuel28g.test.paging3.data.CryptoCurrency

class CryptoDataSourceFactory(private val api: BinanceAPI): DataSource.Factory<Int,CryptoCurrency>() {

    private val sourceLiveData = MutableLiveData<CryptoDataSource>()

    override fun create(): DataSource<Int, CryptoCurrency> {
        val latestSource = CryptoDataSource(api)
        sourceLiveData.postValue(latestSource)
        return latestSource
    }
}