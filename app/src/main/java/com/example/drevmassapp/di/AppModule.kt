package com.example.drevmassapp.di

import android.content.Context
import com.example.drevmassapp.data.local.PreferencesManager
import com.example.drevmassapp.data.repository.AuthRepositoryImpl
import com.example.drevmassapp.data.repository.CatalogRepositoryImpl
import com.example.drevmassapp.domain.repository.AuthRepository
import com.example.drevmassapp.domain.repository.CatalogRepository
import com.example.drevmassapp.domain.usecase.AuthUseCase
import com.example.drevmassapp.domain.usecase.CatalogUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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

    @Provides
    fun provideCatalogRepository(@ApplicationContext context: Context): CatalogRepository {
        return CatalogRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideCatalogUseCase(catalogRepository: CatalogRepository): CatalogUseCase {
        return CatalogUseCase(catalogRepository)
    }
}
