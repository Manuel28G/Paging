package com.manuel28g.test.paging3.datasource

import androidx.paging.ItemKeyedDataSource
import com.manuel28g.test.paging3.api.BinanceAPI
import com.manuel28g.test.paging3.data.CryptoCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CryptoDataSource(private val api: BinanceAPI) : ItemKeyedDataSource<Int,CryptoCurrency>(){
    override fun getKey(item: CryptoCurrency): Int  = item.id
    companion object{
        private const val SECOND_CRYPTO_TITLE = "USDT"
        private var response: List<CryptoCurrency>?= null
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<CryptoCurrency>
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            response = api.getData().execute().body()?.filter {
                //Get only the cryptos with the pair indicated
                it.cryptoPair.contains(SECOND_CRYPTO_TITLE)
            }?.map {
                val cryptoTitle = it.cryptoPair.split(SECOND_CRYPTO_TITLE)
                it.cryptoPair = "${cryptoTitle[0]}/${SECOND_CRYPTO_TITLE}"
                it
            }
            response?.take(params.requestedLoadSize)?.let {
                //remove from response the data send to the adapter
                response = response?.subList(params.requestedLoadSize,response?.size?: 0)
                callback.onResult(it)
            }
        }
    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<CryptoCurrency>) {
        response?.take(params.requestedLoadSize)?.let {
            //remove from response the data send to the adapter
            response?.let {
                if(response != null && params.requestedLoadSize < it.size) {
                    response = response?.subList(params.requestedLoadSize, it.size ?: 0)
                    callback.onResult(it)
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<CryptoCurrency>) {
        //This method is called before the adapter show the initial data
        //Is useful to do an action preload like remove the loader in the UI
    }
}