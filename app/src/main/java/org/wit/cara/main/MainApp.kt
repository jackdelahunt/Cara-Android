package org.wit.cara.main

import android.annotation.SuppressLint
import android.app.Application
import android.app.UiModeManager.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.wit.cara.models.UserMemStore
import org.wit.cara.models.UserModel
import timber.log.Timber

lateinit var database: DatabaseReference

class MainApp : Application() {

    lateinit var userStore: UserMemStore


    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this);
        database = Firebase.database("https://cara-a6833-default-rtdb.europe-west1.firebasedatabase.app/").reference
        userStore = UserMemStore()

        Timber.plant(Timber.DebugTree())
    }
}