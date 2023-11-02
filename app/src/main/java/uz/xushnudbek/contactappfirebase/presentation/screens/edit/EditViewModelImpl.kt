package uz.xushnudbek.contactappfirebase.presentation.screens.edit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.xushnudbek.contactappfirebase.domain.repository.ContactRepository
import javax.inject.Inject

@HiltViewModel
class EditViewModelImpl @Inject constructor(
    private val repository: ContactRepository,
    private val direction: EditDirection
) : ViewModel(), EditContract.EditViewModel {

    override fun onEventDispatcher(intent: EditContract.Intent) {
        when (intent) {
            is EditContract.Intent.ClickEdit -> {
                viewModelScope.launch {
                    repository.editContact(contactData = intent.data, imageUri = intent.imageUri).onEach {
                        Log.d("TTT", "onEventDispatcher: edit call")
                    }.launchIn(viewModelScope)
                }

                Log.d("TTT", "onEventDispatcher: edit call down")

                viewModelScope.launch {
                    direction.back()
                }
            }
        }
    }
}