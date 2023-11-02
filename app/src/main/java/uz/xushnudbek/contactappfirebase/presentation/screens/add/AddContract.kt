package uz.xushnudbek.contactappfirebase.presentation.screens.add

import android.net.Uri
import kotlinx.coroutines.flow.StateFlow
import uz.xushnudbek.contactappfirebase.data.model.ContactData
import uz.xushnudbek.contactappfirebase.data.model.ContactRequest


interface AddContract {
    interface AddViewModel {
        fun onEventDispatcher(intent: Intent)
    }

    interface Intent {
        data class ClickAdd(
            val data: ContactRequest,
            val imageUri:Uri
        ) : Intent
    }
}