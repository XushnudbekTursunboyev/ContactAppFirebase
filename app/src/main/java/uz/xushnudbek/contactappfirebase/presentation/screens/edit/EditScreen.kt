package uz.xushnudbek.contactappfirebase.presentation.screens.edit

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import uz.xushnudbek.contactappfirebase.R
import uz.xushnudbek.contactappfirebase.data.model.ContactData
import uz.xushnudbek.contactappfirebase.ui.theme.ContactAppFirebaseTheme
import uz.xushnudbek.contactappfirebase.utils.Constants
import uz.xushnudbek.contactappfirebase.utils.extensions.MaskVisualTransformation

class EditScreen(private val data: ContactData) : AndroidScreen() {
    @Composable
    override fun Content() {
        val viewmodel: EditContract.EditViewModel = getViewModel<EditViewModelImpl>()
        EditScreenContent(
            data = data,
            onIntent = viewmodel::onEventDispatcher
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
private fun EditScreenContent(
    data: ContactData,
    onIntent: (EditContract.Intent) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        val uri = Uri.parse(data.image)
        var imageUri by remember {
            mutableStateOf<Uri?>(uri)
        }
        val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? -> imageUri = uri }
        val uriParser = Uri.parse(imageUri.toString())
        var name by remember { mutableStateOf(data.name) }
        var phone by remember { mutableStateOf(data.phone) }



        Column(modifier = Modifier.fillMaxWidth()) {

            GlideImage(
                model = uriParser,
                contentDescription = null,
                loading = placeholder(R.drawable.ic_launcher_background),
                modifier = Modifier
                    .padding(4.dp)
                    .padding(top = 100.dp)
                    .height(150.dp)
                    .width(150.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape)
                    .clickable { launcher.launch("image/*") },
                contentScale = ContentScale.Crop,
            )

            TextField(
                value = name, onValueChange = {
                    name = it
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .background(color = Color.Cyan), shape = RoundedCornerShape(5.dp),
                label = { Text(text = "Name")}
            )

            TextField(
                value = phone, onValueChange = {
                    if (Constants.INPUT_LENGTH >= it.length){
                        phone = it.filter { it.isDigit() }.trim()
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .background(color = Color.Cyan), shape = RoundedCornerShape(5.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                visualTransformation = MaskVisualTransformation(Constants.MASK),
                singleLine = true,
                maxLines = 13,
                placeholder = { Text(text = "+998-9#-###-##-##") },
                label = { Text(text = "Phone")}
            )

            Button(
                onClick = {
                    Log.d("TTT", "EditScreenContent: Click edit")
                    onIntent.invoke(EditContract.Intent.ClickEdit(ContactData(data.id, imageUri.toString(), name, phone), imageUri!!)) }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(text = "Edit")
            }
        }
    }
}
