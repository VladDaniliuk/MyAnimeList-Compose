package data.multiaccounts.di

import android.content.Context
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import data.multiaccounts.repositories.AuthRepository
import data.multiaccounts.sources.AccountInfoSource
import data.multiaccounts.sources.AccountPreferencesSource
import data.multiaccounts.sources.AuthSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MultiAccountsModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(appContext, AppDatabase::class.java, "AccountInfo").build()

    @Provides
    fun provideAccountSource(appDatabase: AppDatabase): AccountInfoSource = appDatabase
        .accountInfoSource()

    @Provides
    fun provideLocalPreferencesSource(@ApplicationContext appContext: Context) =
        AccountPreferencesSource(
            EncryptedSharedPreferences.create(
                appContext,
                "TOKENS_PREFERENCES",
                MasterKey.Builder(appContext, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        )

    @Provides
    fun provideAuthSource(client: HttpClient) = AuthSource(client)

    @Singleton
    @Provides
    fun provideAuthRepository(preferencesSource: AccountPreferencesSource, authSource: AuthSource) =
        AuthRepository(preferencesSource, authSource)
}
