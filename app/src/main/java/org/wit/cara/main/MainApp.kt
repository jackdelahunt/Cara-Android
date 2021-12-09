package org.wit.cara.main

import android.app.Application
import org.wit.cara.models.CaraImageMemStore
import org.wit.cara.models.GroupMemStore
import timber.log.Timber

class MainApp : Application() {

    val caraImages = CaraImageMemStore()
    val groups = GroupMemStore()


    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}