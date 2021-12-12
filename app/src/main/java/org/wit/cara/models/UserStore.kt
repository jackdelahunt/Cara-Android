package org.wit.cara.models

import com.firebase.ui.auth.data.model.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import org.wit.cara.main.database
import timber.log.Timber.i

class UserMemStore {

    var user: UserModel = UserModel()
    var groups = ArrayList<GroupModel>()
    var lastId = 0L

    @Exclude
    var signedIn: Boolean = false

    fun signIn(firebaseUser: FirebaseUser) {
        user.displayName = firebaseUser.displayName!!
        user.uid = firebaseUser.uid
        signedIn = true;
        fetch()
    }

    fun findGroupById(id: Long): GroupModel? {
        for (group in groups)
            if (group.id == id) return group

        return null
    }

    fun addImage(caraImageModel: CaraImageModel) {
        caraImageModel.id = getId()
        val group = findGroupById(caraImageModel.groupId) ?: return
        group.caraImages.add(caraImageModel)
        postImage(group.id, group.caraImages.size - 1)
    }

    fun updateImage(caraImageModel: CaraImageModel) {
        val group = findGroupById(caraImageModel.groupId) ?: return
        var i = 0;
        for(caraImage in group.caraImages) {
            if(caraImage.id == caraImageModel.id) {
                group.caraImages[i] = caraImageModel
                postImage(group.id, group.caraImages.size - 1)
            }
            i++;
        }
    }

    fun addGroup(group: GroupModel) {
        group.id = getId()
        groups.add(group)
        postGroup(group)
    }


    private fun postImage(groupId: Long, index: Int) {
        val caraImageModel = findGroupById(groupId)!!.caraImages[index]
        val childUpdates = hashMapOf<String, Any>(
            "/users/${user.uid}/groups/${caraImageModel.groupId}/caraImages/$index" to caraImageModel
        )

        database.updateChildren(childUpdates)
    }

    private fun postGroup(group: GroupModel) {
        val childUpdates = hashMapOf<String, Any>(
            "/users/${user.uid}/groups/${group.id}/" to group
        )

        database.updateChildren(childUpdates)
    }

    private fun postLastId() {
        val childUpdates = hashMapOf<String, Any>(
            "/users/${user.uid}/lastId/" to lastId
        )

        database.updateChildren(childUpdates)
    }

    private fun fetch() {

        database.child("users").child(user.uid).get().addOnSuccessListener {
            if(it.value == null) {
                postNewUser()
                return@addOnSuccessListener
            }
            val loadedData = it.getValue<UserMemStore>()!!
            user = loadedData.user
            groups = loadedData.groups
            lastId = loadedData.lastId
        }.addOnFailureListener {
            i("failed to load data")
        }
    }

    private fun postNewUser() {
        val childUpdates = hashMapOf<String, Any>(
            "/users/${user.uid}/" to this,
        )

        database.updateChildren(childUpdates)
    }

    private fun getId(): Long {
        val toReturn = lastId;
        lastId++;
        postLastId()
        return toReturn;
    }
}