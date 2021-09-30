package com.manuel28g.test.paging3.repository

import com.manuel28g.test.paging3.data.CryptoCurrency

interface BinanceRepository {

    fun getData() :List<CryptoCurrency>?
}