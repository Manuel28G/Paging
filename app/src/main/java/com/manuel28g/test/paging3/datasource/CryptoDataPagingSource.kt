package com.manuel28g.test.paging3.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.manuel28g.test.paging3.api.BinanceAPI
import com.manuel28g.test.paging3.data.CryptoCurrency
import java.lang.Exception
import java.lang.NullPointerException

class CryptoDataPagingSource(private val api: BinanceAPI): PagingSource<Int,CryptoCurrency>() {

    companion object{
        private const val SECOND_CRYPTO_TITLE = "USDT"
        private var mData: List<CryptoCurrency>?= null
    }

    override fun getRefreshKey(state: PagingState<Int, CryptoCurrency>): Int? {
        return state.anchorPosition?.let {
            state?.closestItemToPosition(it)?.id
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CryptoCurrency> {
        val nextPageNumber = params.key ?: 1
        try{

            val response = api.getData()
            val data = response.body()?.filter {
                //Get only the cryptos with the pair indicated
                it.cryptoPair.contains(SECOND_CRYPTO_TITLE)
            }?.map {
                val cryptoTitle = it.cryptoPair.split(SECOND_CRYPTO_TITLE)
                it.cryptoPair = "${cryptoTitle[0]}/${SECOND_CRYPTO_TITLE}"
                it
            }
            data?.take(params.loadSize)?.let {
                //remove from response the data send to the adapter
                mData = data?.subList(params.loadSize,data?.size?: 0)

                return LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = nextPageNumber
                )

            }
            return LoadResult.Error(NullPointerException("The object response is null"))
        }catch (ex: Exception){
            return LoadResult.Error(ex)
        }

    }
}