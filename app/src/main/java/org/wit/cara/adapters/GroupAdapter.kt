package org.wit.cara.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.cara.databinding.CardGroupBinding
import org.wit.cara.models.GroupModel

interface GroupListener {
    fun onGroupClick(group: GroupModel)
}

class GroupAdapter constructor(private var groups: List<GroupModel>,
                               private val listener: GroupListener) :
    RecyclerView.Adapter<GroupAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardGroupBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val group = groups[holder.adapterPosition]
        holder.bind(group, listener)
    }

    override fun getItemCount(): Int = groups.size

    class MainHolder(private val binding : CardGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(group: GroupModel, listener: GroupListener) {
            binding.cardGroupName.text = group.name
            binding.root.setOnClickListener { listener.onGroupClick(group) }
        }
    }
}