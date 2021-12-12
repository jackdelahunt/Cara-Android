package org.wit.cara.models
import android.net.Uri
import android.os.Parcelable
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@IgnoreExtraProperties
data class CaraImageModel(
    var title: String = "",
    var id: Long = 0,
    var groupId: Long = 0,
    var image: String = ""
) : Parcelable