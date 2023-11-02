package uz.xushnudbek.contactappfirebase.presentation.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.xushnudbek.contactappfirebase.domain.repository.ContactRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModelImpl @Inject constructor(
    private val repository: ContactRepository,
    private val direction: MainDirection
) : ViewModel(), MainContract.MainViewModel {
    override val uiState = MutableStateFlow(MainContract.UIState())

    private fun reduce(block: MainContract.UIState.() -> MainContract.UIState) {
        val oldState = uiState.value
        val newState = block(oldState)
        uiState.tryEmit(newState)
    }

    init {
        repository.loadContacts()
            .onEach {
                reduce { this.copy(loader = true) }
                it.onSuccess {list->
                    reduce { this.copy(state = list, loader = false) }
                }
                it.onFailure {
                    reduce { this.copy(loader = false) }
                }
            }.launchIn(viewModelScope)
    }

    override fun onEventDispatcher(intent: MainContract.Intent) {
        when (intent) {

            MainContract.Intent.LoadData ->{
                repository.loadContacts()
                    .onEach {
                        it.onSuccess {list->
                            reduce { this.copy(state = list, loader = false) }
                        }
                        it.onFailure {
                            reduce { this.copy(loader = false) }
                        }
                    }.launchIn(viewModelScope)
            }

            MainContract.Intent.ClickAdd -> {
                viewModelScope.launch {
                    direction.openAddScreen()
                }
            }

            is MainContract.Intent.ClickEdit -> {
                Log.d("TTT", "onEventDispatcher: ${intent.data.id}")
                viewModelScope.launch {
                    direction.openEditScreen(intent.data)
                }
            }
            //123

            is MainContract.Intent.ClickDelete -> {
                reduce { this.copy(loader = true) }
                Log.d("TTT", "onEventDispatcher: ${intent.data.id}")
                repository.deleteContact(intent.data).onEach {
                }.launchIn(viewModelScope)
                repository.loadContacts()
                    .onEach {
                        it.onSuccess {list->
                            reduce { this.copy(state = list, loader = false) }
                        }
                        it.onFailure {
                            reduce { this.copy(loader = false) }
                        }
                    }.launchIn(viewModelScope)
            }
        }
    }
}