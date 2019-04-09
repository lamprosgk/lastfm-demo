package me.lamprosgk.lastfm.di

import me.lamprosgk.lastfm.BASE_URL
import me.lamprosgk.lastfm.data.remote.ArtistsService
import me.lamprosgk.lastfm.BuildConfig
import me.lamprosgk.lastfm.data.ArtistsRepositoryImpl
import me.lamprosgk.lastfm.data.TracksRepositoryImpl
import me.lamprosgk.lastfm.data.remote.TopTracksService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Injector {

    fun provideLoggingCapableHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    private fun provideRetrofit(client: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    fun provideArtistsRepository() = ArtistsRepositoryImpl(provideArtistService())

    private fun provideArtistService(): ArtistsService {
        val client = provideLoggingCapableHttpClient()
        return provideRetrofit(client, BASE_URL)
            .create(ArtistsService::class.java)
    }


    fun provideTracksRepository() = TracksRepositoryImpl(provideTracksService())

    private fun provideTracksService(): TopTracksService {
        val client = provideLoggingCapableHttpClient()
        return provideRetrofit(client, BASE_URL)
            .create(TopTracksService::class.java)
    }
}