package xyz.rfsfernandes.faureciaaptoide.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder(baseUrl: String) {
    private var instance: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val aptoideService = instance.create(AptoideService::class.java)
}
