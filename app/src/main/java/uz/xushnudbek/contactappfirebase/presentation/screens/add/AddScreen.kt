package uz.xushnudbek.contactappfirebase.presentation.screens.add

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import org.checkerframework.checker.units.qual.Current
import uz.xushnudbek.contactappfirebase.R
import uz.xushnudbek.contactappfirebase.data.model.ContactRequest
import uz.xushnudbek.contactappfirebase.ui.theme.ContactAppFirebaseTheme
import uz.xushnudbek.contactappfirebase.utils.Constants.INPUT_LENGTH
import uz.xushnudbek.contactappfirebase.utils.Constants.MASK
import uz.xushnudbek.contactappfirebase.utils.extensions.MaskVisualTransformation
import java.util.UUID

class AddScreen : AndroidScreen() {
    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    override fun Content() {
        val viewModel: AddContract.AddViewModel = getViewModel<AddViewModelImpl>()
        AddScreenContent(
            onIntent = viewModel::onEventDispatcher
        )
    }
}

@ExperimentalGlideComposeApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddScreenContent(onIntent: (AddContract.Intent) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        var name by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }
        var imageUri by remember { mutableStateOf<Uri?>(null) }
        Column(modifier = Modifier.fillMaxWidth()) {

            val pickImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                if (uri != null) {
                    imageUri = uri
                }
            }

            val uriParser = Uri.parse(imageUri.toString())


            GlideImage(
                model = uriParser,
                contentDescription = null,
                loading= placeholder(R.drawable.ic_launcher_background),
                modifier = Modifier
                    .padding(4.dp)
                    .padding(top = 100.dp)
                    .height(150.dp)
                    .align(Alignment.CenterHorizontally)
                    .width(150.dp)
                    .clip(CircleShape)
                    .clickable { pickImageLauncher.launch("image/*") },
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
                    if (INPUT_LENGTH >= it.length){
                        phone = it.filter { it.isDigit() }.trim()
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .background(color = Color.Cyan), shape = RoundedCornerShape(5.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                visualTransformation = MaskVisualTransformation(MASK),
                singleLine = true,
                maxLines = 13,
                placeholder = { Text(text = "+998-9#-###-##-##") },
                label = { Text(text = "Phone")}
                    )

            val context = LocalContext.current

            Button(
                onClick = {
                    if (imageUri != null && imageUri.toString().isNotEmpty()){
                        if (phone.length==8 && name.isNotEmpty()){
                            onIntent.invoke(AddContract.Intent.ClickAdd(ContactRequest(imageUri.toString(), name, phone), imageUri = imageUri!!))
                        }else{
                            Toast.makeText(context, "Name or phone number wrong", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(context, "Select image", Toast.LENGTH_SHORT).show()
                    }


                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(text = "Add")
            }
        }
    }
}

//private fun uploadImageToFirebaseStorage(imageUri: Uri) {
//    val storage = Firebase.storage
//    val storageRef = storage.reference
//    val imageRef = storageRef.child("images/${UUID.randomUUID()}")
//
//    imageRef.putFile(imageUri)
//        .addOnSuccessListener { taskSnapshot ->
//            val downloadUrl = taskSnapshot.metadata?.reference?.downloadUrl
//
//            downloadUrl?.addOnSuccessListener {
//
//            }
//            Log.d("TTT", "success: ${downloadUrl.toString()}")
//        }
//        .addOnFailureListener { exception ->
//            Log.d("TTT", "error: $exception")
//        }
//}



@ExperimentalGlideComposeApi
@Preview(showBackground = true)
@Composable
private fun AddScreenPreview() {
    ContactAppFirebaseTheme {
        AddScreenContent {}
    }
}

