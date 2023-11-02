package uz.xushnudbek.contactappfirebase.presentation.screens.main

import android.annotation.SuppressLint
import android.util.Log
import android.widget.ProgressBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import uz.xushnudbek.contactappfirebase.ui.components.ContactItem

class MainScreen: AndroidScreen() {
    @Composable
    override fun Content() {
        val viewModel: MainContract.MainViewModel = getViewModel<MainViewModelImpl>()
        MainScreenContent(
            uiState = viewModel.uiState.collectAsState(), onIntent = viewModel::onEventDispatcher
        )

    }
}

@Composable
fun MainScreenContent(
    uiState: State<MainContract.UIState>,
    onIntent: (MainContract.Intent) -> Unit
){
    //onIntent.invoke(MainContract.Intent.LoadData)

    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(onClick = { onIntent.invoke(MainContract.Intent.ClickAdd) }, modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(12.dp)) {
            Text(text = "+", style = TextStyle(fontSize = 27.sp))
        }

        CircularProgressIndicator(progress = if (uiState.value.loader) 1f else 0f, modifier = Modifier.align(Alignment.Center))

        Log.d("TTT", uiState.value.state.toString())

        LazyColumn{
            items(uiState.value.state) {
                ContactItem(
                    data = it,
                    onClickEdit = {onIntent.invoke(MainContract.Intent.ClickEdit(it))},
                    onCLickDelete = {onIntent.invoke(MainContract.Intent.ClickDelete(it))}
                )
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun MainScreenPreview(){
        MainScreenContent(uiState = mutableStateOf(MainContract.UIState())){

        }
}