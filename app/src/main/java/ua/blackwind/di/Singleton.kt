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
import ua.blackwind.data.cat_facts.CatFactsRemoteDataSource
import ua.blackwind.data.cat_facts.CatFactsRepository
import ua.blackwind.data.cat_facts.ICatFactsRemoteDataSource
import ua.blackwind.data.cat_facts.ICatFactsRepository
import ua.blackwind.data.db.CatFactsDatabase

@Module
@InstallIn(SingletonComponent::class)
object Singleton {

    @Provides
    fun provideCatFactsDatabase(@ApplicationContext context: Context): CatFactsDatabase {
        return Room
            .databaseBuilder(context, CatFactsDatabase::class.java, CatFactsDatabase.DB_NAME)
            .build()
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun provideRequestQueue(@ApplicationContext context: Context) = Volley.newRequestQueue(context)

    @Provides
    fun provideCatFactsRemoteDataSource(requestQueue: RequestQueue): ICatFactsRemoteDataSource {
        return CatFactsRemoteDataSource(requestQueue)
    }

    @Provides
    fun provideCatFactsRepository(
        remoteDataSource: ICatFactsRemoteDataSource,
        database: CatFactsDatabase,
        moshi: Moshi
    ): ICatFactsRepository {
        return CatFactsRepository(remoteDataSource, database, moshi)
    }
}