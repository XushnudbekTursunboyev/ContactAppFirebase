package uz.xushnudbek.contactappfirebase.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Provides
    @Singleton
    fun provideFireStore():FirebaseFirestore = FirebaseFirestore.getInstance()

    @[Provides  Singleton]
    fun providesFireBaseStorage() : StorageReference = Firebase.storage.reference
}