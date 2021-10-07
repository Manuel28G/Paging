package com.manuel28g.test.paging3.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.manuel28g.test.paging3.data.CryptoCurrency
import com.manuel28g.test.paging3.datasource.CryptoDataPagingSource
import com.manuel28g.test.paging3.helpers.RetrofitHelper
import com.manuel28g.test.paging3.repository.BinanceRepository
import com.manuel28g.test.paging3.repository.BinanceRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CryptoInfoViewModel: ViewModel() {
    private val mRepository : BinanceRepository = BinanceRepositoryImpl(RetrofitHelper().getInstance())
    private val mCryptoDataList = MutableLiveData<List<CryptoCurrency>?>()
    private val mPagingSource = CryptoDataPagingSource(RetrofitHelper().getInstance())


    fun getData(){
        GlobalScope.launch(Dispatchers.IO) {
            mRepository.getData().let {
                mCryptoDataList.postValue(it)
            }
        }
    }

    fun cryptoDataListObserve(): Flow<PagingData<CryptoCurrency>?> {
        return Pager(PagingConfig(10,1,true)){
            mPagingSource
        }.flow.cachedIn(viewModelScope)
    }
}