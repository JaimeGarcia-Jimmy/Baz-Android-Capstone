package com.example.criptomonedas.di

import com.example.criptomonedas.Constants
import com.example.criptomonedas.data.api.LoggingInterceptor
import com.example.criptomonedas.data.api.services.BooksService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(LoggingInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRxAdapter(): RxJava3CallAdapterFactory = RxJava3CallAdapterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(retrofitClient: OkHttpClient, rxJava3CallAdapterFactory: RxJava3CallAdapterFactory): Retrofit {
        return Retrofit.Builder()
            .client(retrofitClient)
            .baseUrl(Constants.BITSO_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(rxJava3CallAdapterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideBooksService(retrofit: Retrofit): BooksService {
        return retrofit.create(BooksService::class.java)
    }

    @Singleton
    @Provides
    fun providesCompositeDisposable(): CompositeDisposable = CompositeDisposable()
}
