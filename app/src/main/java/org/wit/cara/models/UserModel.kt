package org.wit.cara.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var displayName: String = "",
    var uid: String = ""
) : Parcelable {

    @Exclude
    fun isSignedIn(): Boolean {
        return displayName != "";
    }
}
