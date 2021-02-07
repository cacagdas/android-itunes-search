package com.cacagdas.itunessearchapp.di

import android.app.Application
import androidx.room.Room
import com.cacagdas.itunessearchapp.api.ITunesService
import com.cacagdas.itunessearchapp.db.ITunesDao
import com.cacagdas.itunessearchapp.db.ITunesDb
import com.cacagdas.itunessearchapp.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideMovieService(): ITunesService {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
                .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(ITunesService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): ITunesDb {
        return Room
                .databaseBuilder(app, ITunesDb::class.java, "itunes.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    fun provideITunesDao(db: ITunesDb): ITunesDao {
        return db.iTunesDao()
    }
}
