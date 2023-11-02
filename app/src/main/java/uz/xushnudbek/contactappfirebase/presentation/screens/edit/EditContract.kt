package uz.xushnudbek.contactappfirebase.presentation.screens.edit

import android.net.Uri
import uz.xushnudbek.contactappfirebase.data.model.ContactData

interface EditContract {
    interface EditViewModel {
        fun onEventDispatcher(intent: Intent)
    }

    interface Intent {
        data class ClickEdit(
            val data: ContactData,
            val imageUri:Uri
        ) : Intent
    }
}