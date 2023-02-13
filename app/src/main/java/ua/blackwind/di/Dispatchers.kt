package ua.blackwind.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesDispatcherModule {

    @DefaultDispatcher
    @Singleton
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Singleton
    @IoDispatcher
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Singleton
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @MainImmediateDispatcher
    @Singleton
    @Provides
    fun providesMainImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate

}