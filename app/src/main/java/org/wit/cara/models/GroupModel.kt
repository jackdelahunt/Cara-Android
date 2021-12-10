package org.wit.cara.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GroupModel(
    var name: String = "",
    var caraImages: ArrayList<CaraImageModel> = ArrayList<CaraImageModel>(),
    var id: Long = 0
) : Parcelable
