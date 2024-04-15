package core.mvi.di

import android.content.Context
import core.mvi.keepers.NetworkKeeper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    fun provideNetworkKeeper(@ApplicationContext context: Context) = NetworkKeeper(context)
}
