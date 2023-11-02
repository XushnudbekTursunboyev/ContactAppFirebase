package uz.xushnudbek.contactappfirebase.presentation.screens.add

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import uz.xushnudbek.contactappfirebase.domain.repository.ContactRepository
import javax.inject.Inject

@HiltViewModel
class AddViewModelImpl @Inject constructor(
    private val repository: ContactRepository,
    private val direction: AddDirection
) : ViewModel(), AddContract.AddViewModel {

    override fun onEventDispatcher(intent: AddContract.Intent) {
        when(intent){
            is AddContract.Intent.ClickAdd -> {
                repository.addContact(intent.data, imageUri = intent.imageUri).onEach {
                }.launchIn(viewModelScope)
                Log.d("TTT", "AddScreenContent: Click")

                viewModelScope.launch {
                    direction.back()
                }
            }
        }
    }



}