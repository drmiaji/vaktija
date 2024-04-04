package vk.vaktija.di

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vk.vaktija.network.ApiService
import vk.vaktija.repository.PrayerTimeRepositoryImpl

interface AppModule {
    val apiService: ApiService
    val prayerTimeRepository: PrayerTimeRepositoryImpl
}

class AppModuleImpl(private val appContext: Context) : AppModule {
    private val BASE_URL = "https://api.vaktija.ba/vaktija/v1/"

    override val apiService: ApiService by lazy {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(loggingInterceptor).build())
            .build()
        retrofit.create(ApiService::class.java)
    }

    override val prayerTimeRepository: PrayerTimeRepositoryImpl by lazy {
        PrayerTimeRepositoryImpl(apiService)
    }
}