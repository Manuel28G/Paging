package com.manuel28g.test.paging3.helpers

import com.manuel28g.test.paging3.BuildConfig
import com.manuel28g.test.paging3.api.BinanceAPI
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitHelper {

    private lateinit var retrofitWithAuth: BinanceAPI

    protected fun baseClient():OkHttpClient.Builder{
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 2
        dispatcher.maxRequestsPerHost = 1

        return OkHttpClient
            .Builder()
            .dispatcher(dispatcher)
            .retryOnConnectionFailure(true)
            .connectTimeout(BuildConfig.API_TIMEOUT, TimeUnit.SECONDS)
    }

    protected fun getRetrofit(client:OkHttpClient,urlAPI: String): Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl(urlAPI)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
    }

    protected inline fun <reified T>  buildClient(client:OkHttpClient.Builder, urlAPI: String): T{
        val retrofit = getRetrofit(client.build(),urlAPI).build()
        return  retrofit.create(T::class.java)
    }

    /**
     * This method get retrofit service without auth bearer header
     */
    protected inline fun <reified T> getClientService(urlAPI:String): T {
        return buildClient(baseClient(),urlAPI)
    }


    fun getInstance(): BinanceAPI {
        if (!this::retrofitWithAuth.isInitialized) {
            retrofitWithAuth = getClientService(BuildConfig.CRYPTO_API_URL)
        }
        return retrofitWithAuth
    }

}