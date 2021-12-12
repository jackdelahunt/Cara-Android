package org.wit.cara.models

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.wit.cara.main.MainApp
import timber.log.Timber.i

interface CaraImageStore {
    fun findAll(): List<CaraImageModel>
    fun create(caraImage: CaraImageModel)
    fun update(caraImage: CaraImageModel)
}

class CaraImageMemStore : CaraImageStore {

    val placemarks = ArrayList<CaraImageModel>()
    lateinit var app: MainApp

    override fun findAll(): List<CaraImageModel> {
        return placemarks
    }

    override fun create(caraImage: CaraImageModel) {
        // caraImage.id = getId()
        placemarks.add(caraImage)
        logAll()
    }

    override fun update(caraImage: CaraImageModel) {
        var foundCaraImage: CaraImageModel? = placemarks.find { p -> p.id == caraImage.id }
        if (foundCaraImage != null) {
            foundCaraImage.title = caraImage.title
            foundCaraImage.image = caraImage.image
            logAll()
        }
    }

    private fun logAll() {
        placemarks.forEach { i("$it") }
    }
}