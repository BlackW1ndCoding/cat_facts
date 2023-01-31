package ua.blackwind.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ua.blackwind.data.CatFactsRemoteDataSource
import ua.blackwind.data.CatFactsRepository
import ua.blackwind.data.ICatFactsRepository
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
            .add(KotlinJsonAdapterFactory::class.java)
            .build()
    }

    @Provides
    fun provideCatFactsRepository(
        @ApplicationContext context: Context,
        database: CatFactsDatabase,
        moshi: Moshi
    ): ICatFactsRepository {
        return CatFactsRepository(CatFactsRemoteDataSource(context), database, moshi)
    }

}