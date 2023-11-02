package uz.xushnudbek.contactappfirebase.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import uz.xushnudbek.contactappfirebase.R
import uz.xushnudbek.contactappfirebase.data.model.ContactData

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ContactItem(
    data: ContactData,
    onClickEdit: (ContactData) -> Unit,
    onCLickDelete: (ContactData) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(onClick = { onClickEdit(data) })
            .padding(8.dp)
            .background(color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        GlideImage(
            model = data.image,
            contentDescription = null,
            loading = placeholder(R.drawable.ic_launcher_background),
            modifier = Modifier
                .padding(4.dp)
                .height(36.dp)
                .width(36.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.size(8.dp))
        Column(
            modifier = Modifier
                .weight(0.7f, true)
        ) {

            Text(
                text = data.name,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.size(4.dp))

            Text(text = data.phone)

        }

        Column(modifier = Modifier.weight(0.3f)) {

        }

        Button(onClick = { onCLickDelete(data)}) {
            Text(text = "Delete")
        }
    }
}