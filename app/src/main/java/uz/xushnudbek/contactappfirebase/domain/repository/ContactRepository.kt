package uz.xushnudbek.contactappfirebase.domain.repository

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import uz.xushnudbek.contactappfirebase.data.model.ContactData
import uz.xushnudbek.contactappfirebase.data.model.ContactRequest

interface ContactRepository {
    fun loadContacts(): Flow<Result<List<ContactData>>>

     fun addContact(contactData: ContactRequest, imageUri:Uri):Flow<String>
     fun deleteContact(contactData: ContactData):Flow<String>
     fun editContact(contactData: ContactData, imageUri:Uri):Flow<String>
}