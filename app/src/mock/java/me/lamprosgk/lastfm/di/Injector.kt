package me.lamprosgk.lastfm.di

import me.lamprosgk.lastfm.BuildConfig
import me.lamprosgk.lastfm.data.ArtistsRepositoryImpl
import me.lamprosgk.lastfm.data.TracksRepositoryImpl
import me.lamprosgk.lastfm.data.remote.ArtistsService
import me.lamprosgk.lastfm.data.remote.MockArtistsServiceImpl
import me.lamprosgk.lastfm.data.remote.MockTracksServiceImpl
import me.lamprosgk.lastfm.data.remote.TopTracksService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object Injector {

    // provide mock implementations
    fun provideArtistsRepository() = ArtistsRepositoryImpl(provideArtistService())
    fun provideTracksRepository() = TracksRepositoryImpl(provideTracksService())

    fun provideLoggingCapableHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    private fun provideArtistService(): ArtistsService = MockArtistsServiceImpl()
    private fun provideTracksService(): TopTracksService = MockTracksServiceImpl()

}