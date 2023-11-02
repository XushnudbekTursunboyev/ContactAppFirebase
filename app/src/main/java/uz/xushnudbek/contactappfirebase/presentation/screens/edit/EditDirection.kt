package uz.xushnudbek.contactappfirebase.presentation.screens.edit


import uz.xushnudbek.contactappfirebase.presentation.screens.main.MainScreen
import uz.xushnudbek.contactappfirebase.utils.navigator.AppNavigator
import javax.inject.Inject
import javax.inject.Singleton

interface EditDirection {
    suspend fun back()
}

@Singleton
class EditDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : EditDirection {
    override suspend fun back() {
        appNavigator.back()
    }
}