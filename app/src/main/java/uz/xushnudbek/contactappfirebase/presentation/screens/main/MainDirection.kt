package uz.xushnudbek.contactappfirebase.presentation.screens.main

import uz.xushnudbek.contactappfirebase.data.model.ContactData
import uz.xushnudbek.contactappfirebase.presentation.screens.add.AddScreen
import uz.xushnudbek.contactappfirebase.presentation.screens.edit.EditScreen
import uz.xushnudbek.contactappfirebase.utils.navigator.AppNavigator
import javax.inject.Inject
import javax.inject.Singleton

interface MainDirection {
    suspend fun openAddScreen()
    suspend fun openEditScreen(contactData: ContactData)
}

@Singleton
class MainDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : MainDirection {
    override suspend fun openAddScreen() {
        appNavigator.openScreenSaveStack(AddScreen())
    }

    override suspend fun openEditScreen(contactData: ContactData) {
        appNavigator.openScreenSaveStack(EditScreen(contactData))
    }

}