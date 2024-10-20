package com.example.drevmassapp.di

import com.example.drevmassapp.data.repository.AuthRepositoryImpl
import com.example.drevmassapp.domain.repository.AuthRepository
import com.example.drevmassapp.domain.usecase.AuthUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideLoginRepository(): AuthRepository {
        return AuthRepositoryImpl()
    }

    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository): AuthUseCase {
        return AuthUseCase(authRepository)
    }
}
