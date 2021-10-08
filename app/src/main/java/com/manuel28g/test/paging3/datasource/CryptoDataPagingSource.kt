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
        private const val STARTING_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, CryptoCurrency>): Int? {
        return state.anchorPosition?.let {
            state?.closestPageToPosition(it)?.prevKey?.plus(1)?:
            state?.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CryptoCurrency> {
        val position = params.key ?: STARTING_INDEX
        try{

            val response = api.getData()

            if(mData == null) {
                mData = response.body()?.filter {
                    //Get only the cryptos with the pair indicated
                    it.cryptoPair.contains(SECOND_CRYPTO_TITLE)
                }?.map {
                    val cryptoTitle = it.cryptoPair.split(SECOND_CRYPTO_TITLE)
                    it.cryptoPair = "${cryptoTitle[0]}/${SECOND_CRYPTO_TITLE}"
                    it
                }
            }
            val nextKey = if(mData?.isEmpty() == true) null else {position + params.loadSize}

            mData?.take(params.loadSize)?.let {
                //remove from response the data send to the adapter
                mData = mData?.subList(params.loadSize,mData?.size?: 0)

                return LoadResult.Page(
                    data = it,
                    prevKey = if(position == STARTING_INDEX ) null else position - 1,
                    nextKey = nextKey
                )

            }
            return LoadResult.Error(NullPointerException("The object response is null"))
        }catch (ex: Exception){
            return LoadResult.Error(ex)
        }

    }
}