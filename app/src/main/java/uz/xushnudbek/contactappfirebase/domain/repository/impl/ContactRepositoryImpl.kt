package uz.xushnudbek.contactappfirebase.domain.repository.impl

import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.xushnudbek.contactappfirebase.data.model.ContactData
import uz.xushnudbek.contactappfirebase.data.model.ContactRequest
import uz.xushnudbek.contactappfirebase.domain.repository.ContactRepository
import uz.xushnudbek.contactappfirebase.utils.extensions.getAll
import uz.xushnudbek.contactappfirebase.utils.extensions.getAllLive
import java.util.UUID
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val storageRef:StorageReference
) : ContactRepository {

    override fun loadContacts(): Flow<Result<List<ContactData>>> = db.collection("users")
        .getAllLive {
            return@getAllLive  ContactData(
                it.id,
                it.data?.getOrDefault("image", "").toString(),
                it.data?.getOrDefault("name", "").toString(),
                it.data?.getOrDefault("phone", "").toString())
        }

    override fun addContact(contactData: ContactRequest, imageUri: Uri): Flow<String> = flow {
        val contactId = "${UUID.randomUUID()}"

        imageUri.let {
            storageRef.child(contactId).putFile(it)
                .addOnSuccessListener { task ->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener {
                            db.collection("users")
                                .document(contactId)
                                .set(ContactData(contactId, it.toString(), contactData.name, contactData.phone))
                                .addOnSuccessListener {

                                }
                                .addOnFailureListener {

                                }
                        }
                }
        }
    }

    override  fun deleteContact(contactData: ContactData): Flow<String> = flow {
        db.collection("users")
            .document(contactData.id)
            .delete()
            .addOnSuccessListener {
                Log.d("TTT", "delete: Success")
            }
            .addOnFailureListener {
                Log.d("TTT", "delete: Error")
            }
    }

    override  fun editContact(contactData: ContactData, imageUri: Uri): Flow<String> = flow {
        val imageUri = Uri.parse(contactData.image)
        storageRef.child(contactData.id).putFile(imageUri)
            .addOnSuccessListener { task ->
                task.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener {
                        db.collection("users")
                            .document(contactData.id).set(ContactData(contactData.id,it.toString(), contactData.name, contactData.phone))
                            .addOnSuccessListener {
                                Log.d("TTT", "editContact: Success")
                            }
                            .addOnFailureListener {
                                Log.d("TTT", "editContact: Error")

                            }
                    }

            }
            .addOnFailureListener {
            db.collection("users")
                .document(contactData.id).set(ContactData(contactData.id,imageUri.toString(), contactData.name, contactData.phone))
                .addOnSuccessListener {
                    Log.d("TTT", "editContact: Success")
                }
                .addOnFailureListener {
                    Log.d("TTT", "editContact: Error")

                }
        }
    }
}