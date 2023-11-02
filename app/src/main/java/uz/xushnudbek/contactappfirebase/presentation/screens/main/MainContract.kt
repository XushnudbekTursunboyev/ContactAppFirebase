package uz.xushnudbek.contactappfirebase.presentation.screens.main

import kotlinx.coroutines.flow.StateFlow
import uz.xushnudbek.contactappfirebase.data.model.ContactData


interface MainContract {
    interface MainViewModel {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val state: List<ContactData> = listOf(),
        val loader:Boolean = false
    )

    interface Intent {
        data class ClickEdit(
            val data: ContactData
        ) : Intent

        data class ClickDelete(
            val data: ContactData
        ):Intent

        object ClickAdd:Intent

        object LoadData:Intent
    }
}