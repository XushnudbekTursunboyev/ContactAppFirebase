package uz.xushnudbek.contactappfirebase.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.xushnudbek.contactappfirebase.domain.repository.ContactRepository
import uz.xushnudbek.contactappfirebase.domain.repository.impl.ContactRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {
    @[Binds Singleton]
    fun bindAppRepository(impl: ContactRepositoryImpl): ContactRepository
}