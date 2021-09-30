package com.manuel28g.test.paging3.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manuel28g.test.paging3.data.CryptoCurrency
import com.manuel28g.test.paging3.helpers.RetrofitHelper
import com.manuel28g.test.paging3.repository.BinanceRepository
import com.manuel28g.test.paging3.repository.BinanceRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CryptoInfoViewModel: ViewModel() {
    private val mRepository : BinanceRepository = BinanceRepositoryImpl(RetrofitHelper().getInstance())
    private val mCryptoDataList = MutableLiveData<List<CryptoCurrency>?>()

    fun getData(){
        GlobalScope.launch(Dispatchers.IO) {
            mRepository.getData().let {
                mCryptoDataList.postValue(it)
            }
        }
    }

    fun cryptoDataListObserve():LiveData<List<CryptoCurrency>?>{
        return mCryptoDataList
    }
}