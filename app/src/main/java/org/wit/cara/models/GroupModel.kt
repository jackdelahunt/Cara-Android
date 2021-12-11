package org.wit.cara.models

import android.net.Uri
import android.os.Parcelable
import com.google.firebase.database.DatabaseReference
import kotlinx.parcelize.Parcelize
import org.wit.cara.main.MainApp
import org.wit.cara.main.database

@Parcelize
data class GroupModel(
    var name: String = "",
    var caraImages: ArrayList<CaraImageModel> = ArrayList<CaraImageModel>(),
    var id: Long = 0
) : Parcelable
