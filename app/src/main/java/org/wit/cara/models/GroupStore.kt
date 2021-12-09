package org.wit.cara.models

import timber.log.Timber.i

interface GroupStore {
    fun findAll(): List<GroupModel>
    fun create(group: GroupModel)
    fun update(group: GroupModel)
}

class GroupMemStore : GroupStore {

    val groups = ArrayList<GroupModel>()

    override fun findAll(): List<GroupModel> {
        return groups
    }

    override fun create(group: GroupModel) {
        group.id = getId()
        groups.add(group)
    }

    override fun update(group: GroupModel) {
        var foundGroup: GroupModel? = groups.find { p -> p.id == group.id }
        if (foundGroup != null) {
            foundGroup.name = group.name
        }
    }
}