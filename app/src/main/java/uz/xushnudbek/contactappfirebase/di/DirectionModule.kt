package uz.xushnudbek.contactappfirebase.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.xushnudbek.contactappfirebase.presentation.screens.add.AddDirection
import uz.xushnudbek.contactappfirebase.presentation.screens.add.AddDirectionImpl
import uz.xushnudbek.contactappfirebase.presentation.screens.edit.EditDirection
import uz.xushnudbek.contactappfirebase.presentation.screens.edit.EditDirectionImpl
import uz.xushnudbek.contactappfirebase.presentation.screens.main.MainDirection
import uz.xushnudbek.contactappfirebase.presentation.screens.main.MainDirectionImpl

@Module
@InstallIn(ViewModelComponent::class)
interface DirectionModule {
    @Binds
    fun bindMainDirection(impl: MainDirectionImpl): MainDirection

    @Binds
    fun bindInfoDirection(impl: AddDirectionImpl): AddDirection

    @Binds
    fun bindEditDirection(impl: EditDirectionImpl): EditDirection
}