package ua.blackwind.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
}