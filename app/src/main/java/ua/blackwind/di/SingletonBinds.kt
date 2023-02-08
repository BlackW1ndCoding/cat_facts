package ua.blackwind.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.blackwind.data.cat_facts.CatFactsRepository
import ua.blackwind.data.cat_facts.ICatFactsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SingletonBinds {
    @Singleton
    @Binds
    abstract fun provideCatFactsRepository(
        repository: CatFactsRepository
    ): ICatFactsRepository
}