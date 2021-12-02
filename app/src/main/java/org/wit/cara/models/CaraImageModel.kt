package org.wit.cara.models
import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CaraImageModel(
    var title: String = "",
    var id: Long = 0,
    var image: Uri = Uri.EMPTY
) : Parcelable
