package uz.xushnudbek.contactappfirebase.data.model

import java.io.File
import java.io.Serializable

data class ContactData(
    val id:String,
    val image:String,
    val name:String,
    val phone:String
):Serializable