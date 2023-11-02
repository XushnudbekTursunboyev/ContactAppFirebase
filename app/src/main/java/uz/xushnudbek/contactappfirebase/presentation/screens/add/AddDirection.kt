package uz.xushnudbek.contactappfirebase.presentation.screens.add


import uz.xushnudbek.contactappfirebase.presentation.screens.main.MainScreen
import uz.xushnudbek.contactappfirebase.utils.navigator.AppNavigator
import javax.inject.Inject
import javax.inject.Singleton

interface AddDirection {
    suspend fun back()
}

@Singleton
class AddDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : AddDirection {
    override suspend fun back() {
        appNavigator.back()
    }

}