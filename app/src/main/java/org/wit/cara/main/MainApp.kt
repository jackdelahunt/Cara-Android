package org.wit.cara.main

import android.app.Application
import org.wit.cara.models.CaraImageMemStore
import timber.log.Timber

class MainApp : Application() {

    val placemarks = CaraImageMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}