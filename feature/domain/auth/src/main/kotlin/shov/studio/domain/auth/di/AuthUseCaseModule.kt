package shov.studio.domain.auth.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import data.multiaccounts.repositories.AccountsRepository
import data.multiaccounts.repositories.AuthRepository
import shov.studio.domain.auth.usecases.AuthFullUserUseCase
import shov.studio.domain.auth.usecases.AuthUserUseCase
import shov.studio.domain.auth.usecases.DeleteAccountUseCase
import shov.studio.domain.auth.usecases.GetAccountByIdUseCase
import shov.studio.domain.auth.usecases.GetAccountsUseCase
import shov.studio.domain.auth.usecases.GetAuthLinkUseCase
import shov.studio.domain.auth.usecases.GetLastUsedAccountUseCase
import shov.studio.domain.auth.usecases.GetLastUsedAccountsUseCase
import shov.studio.domain.auth.usecases.IsAuthorizedFlowUseCase
import shov.studio.domain.auth.usecases.IsAuthorizedUseCase
import shov.studio.domain.auth.usecases.OnUserChangeUseCase
import shov.studio.domain.auth.usecases.RefreshTokenUseCase
import data.user.repositories.UserRepository

@Module
@InstallIn(SingletonComponent::class)
class AuthUseCaseModule {
    @Provides
    fun provideDeleteAccountUseCase(
        accountsRepository: AccountsRepository,
        refreshTokenUseCase: RefreshTokenUseCase
    ) = DeleteAccountUseCase(accountsRepository, refreshTokenUseCase)

    @Provides
    fun provideGetAccountByIdUseCase(accountsRepository: AccountsRepository) =
        GetAccountByIdUseCase(accountsRepository)

    @Provides
    fun provideGetAccountsUseCase(accountsRepository: AccountsRepository) =
        GetAccountsUseCase(accountsRepository)

    @Provides
    fun provideGetAuthLinkUseCase(authRepository: AuthRepository) =
        GetAuthLinkUseCase(authRepository)

    @Provides
    fun provideGetLastUsedAccountsUseCase(accountsRepository: AccountsRepository) =
        GetLastUsedAccountsUseCase(accountsRepository)

    @Provides
    fun provideGetLastUsedAccountUseCase(accountsRepository: AccountsRepository) =
        GetLastUsedAccountUseCase(accountsRepository)

    @Provides
    fun provideAuthFullUserUseCase(
        authRepository: AuthRepository,
        @ApplicationContext context: Context,
        userRepository: UserRepository,
        accountsRepository: AccountsRepository
    ) = AuthFullUserUseCase(authRepository, userRepository, accountsRepository, context)

    @Provides
    fun provideAuthUserUseCase(
        authRepository: AuthRepository,
        @ApplicationContext context: Context,
        userRepository: UserRepository,
        accountsRepository: AccountsRepository
    ) = AuthUserUseCase(authRepository, userRepository, accountsRepository, context)

    @Provides
    fun provideIsAuthorizedFlowUseCase(
        authRepository: AuthRepository,
        accountsRepository: AccountsRepository
    ) = IsAuthorizedFlowUseCase(authRepository, accountsRepository)

    @Provides
    fun provideIsAuthorizedUseCase(
        authRepository: AuthRepository,
        accountsRepository: AccountsRepository
    ) = IsAuthorizedUseCase(authRepository, accountsRepository)

    @Provides
    fun provideOnUserChangeUseCase(
        accountsRepository: AccountsRepository,
        refreshTokenUseCase: RefreshTokenUseCase
    ) = OnUserChangeUseCase(accountsRepository, refreshTokenUseCase)

    @Provides
    fun provideRefreshTokenUseCase(@ApplicationContext context: Context) =
        RefreshTokenUseCase(context)
}
