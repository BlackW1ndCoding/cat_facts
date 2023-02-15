package ua.blackwind.di

import android.content.Context
import androidx.room.Room
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import ua.blackwind.data.cat_facts.CatFactsRemoteDataSource
import ua.blackwind.data.cat_facts.ICatFactsRemoteDataSource
import ua.blackwind.data.cat_images.CatImagesRemoteDataSource
import ua.blackwind.data.cat_images.ICatImagesRemoteDataSource
import ua.blackwind.data.db.CatFactsDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonProvides {
    @Provides
    @Singleton
    @ApplicationScope
    fun provideActivityCoroutineScope(): CoroutineScope = MainScope()

    @Singleton
    @Provides
    fun provideCatFactsDatabase(@ApplicationContext context: Context): CatFactsDatabase {
        return Room
            .databaseBuilder(context, CatFactsDatabase::class.java, CatFactsDatabase.DB_NAME)
            //.createFromAsset("cat_facts.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideRequestQueue(@ApplicationContext context: Context) = Volley.newRequestQueue(context)



}