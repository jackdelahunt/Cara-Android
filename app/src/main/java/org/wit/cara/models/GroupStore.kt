package org.wit.cara.models

import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import org.wit.cara.main.database
import timber.log.Timber.i

interface GroupStore {
    fun findAll(): List<GroupModel>
    fun create(group: GroupModel)
    fun updateImage(caraImageModel: CaraImageModel)
    fun findById(id: Long): GroupModel?
    fun addImage(group: GroupModel, imageModel: CaraImageModel)
}

class GroupMemStore : GroupStore {

    var groups = ArrayList<GroupModel>()

    init {
        database.child("group_store").get().addOnSuccessListener {
            groups = it.getValue<GroupMemStore>()!!.groups
        }
    }

    fun write() {
        database.child("group_store").setValue(this)
    }

    override fun findAll(): List<GroupModel> {
        return groups
    }

    override fun create(group: GroupModel) {
        group.id = getId()
        groups.add(group)
        write()
    }

    override fun addImage(group: GroupModel, imageModel: CaraImageModel) {
        group.caraImages.add(imageModel)
        write()
    }


    override fun updateImage(caraImageModel: CaraImageModel) {
        val groupId = caraImageModel.groupId
        for(group in groups) {
            if(group.id == groupId) {
                var i = 0;
                for(caraImage in group.caraImages) {
                    if(caraImage.id == caraImageModel.id) {
                        group.caraImages[i] = caraImageModel
                        writeImage(group.caraImages[i])
                    }
                    i++;
                }
            }
        }
        write()
    }

    fun writeImage(caraImageModel: CaraImageModel) {
        val childUpdates = hashMapOf<String, Any>(
            "/group_store/groups/${caraImageModel.groupId}/caraImages/${caraImageModel.id}" to caraImageModel,
        )

        database.updateChildren(childUpdates)
    }

    @Exclude
    override fun findById(id: Long): GroupModel? {
        for (group in groups)
            if (group.id == id) return group

        return null
    }
}