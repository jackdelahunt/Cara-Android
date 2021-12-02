package org.wit.cara.models

import timber.log.Timber.i

interface CaraImageStore {
    fun findAll(): List<CaraImageModel>
    fun create(caraImage: CaraImageModel)
    fun update(caraImage: CaraImageModel)
}

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class CaraImageMemStore : CaraImageStore {

    val placemarks = ArrayList<CaraImageModel>()

    override fun findAll(): List<CaraImageModel> {
        return placemarks
    }

    override fun create(caraImage: CaraImageModel) {
        caraImage.id = getId()
        placemarks.add(caraImage)
        logAll()
    }

    override fun update(caraImage: CaraImageModel) {
        var foundCaraImage: CaraImageModel? = placemarks.find { p -> p.id == caraImage.id }
        if (foundCaraImage != null) {
            foundCaraImage.title = caraImage.title
            foundCaraImage.description = caraImage.description
            foundCaraImage.image = caraImage.image
            logAll()
        }
    }

    private fun logAll() {
        placemarks.forEach { i("$it") }
    }
}