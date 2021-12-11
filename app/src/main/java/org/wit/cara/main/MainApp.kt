package org.wit.cara.main

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.wit.cara.databinding.ActivityGroupListBinding
import org.wit.cara.models.CaraImageMemStore
import org.wit.cara.models.GroupMemStore
import timber.log.Timber

lateinit var database: DatabaseReference

class MainApp : Application() {

    lateinit var groups: GroupMemStore
    lateinit var caraImages: CaraImageMemStore


    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this);
        database = Firebase.database("https://cara-a6833-default-rtdb.europe-west1.firebasedatabase.app/").reference
        groups = GroupMemStore()
        caraImages = CaraImageMemStore()

        Timber.plant(Timber.DebugTree())
    }
}