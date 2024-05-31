package com.cirogg.conexa.di

import com.cirogg.conexa.data.model.news.NewsDao
import com.cirogg.conexa.data.model.users.UsersDao
import com.cirogg.conexa.data.remote.api.NewsApiService
import com.cirogg.conexa.data.remote.api.UsersApiService
import com.cirogg.conexa.data.repository.NewsRepository
import com.cirogg.conexa.data.repository.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logging
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.org/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(newsApiService: NewsApiService, newsDao: NewsDao): NewsRepository {
        return NewsRepository(newsApiService = newsApiService, newsDao = newsDao)
    }

    @Provides
    @Singleton
    fun provideUsersApiService(retrofit: Retrofit): UsersApiService {
        return retrofit.create(UsersApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUsersRepository(usersApiService: UsersApiService, usersDao: UsersDao): UsersRepository {
        return UsersRepository(usersApiService = usersApiService, usersDao = usersDao)
    }



}