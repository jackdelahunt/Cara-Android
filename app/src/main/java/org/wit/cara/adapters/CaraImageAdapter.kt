package org.wit.cara.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.cara.databinding.CardCaraImageBinding
import org.wit.cara.models.CaraImageModel

interface CaraImageListener {
    fun onCaraImageClick(caraImage: CaraImageModel)
}

class CaraImageAdapter constructor(private var caraImages: List<CaraImageModel>,
                                   private val listener: CaraImageListener) :
    RecyclerView.Adapter<CaraImageAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardCaraImageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val placemark = caraImages[holder.adapterPosition]
        holder.bind(placemark, listener)
    }

    override fun getItemCount(): Int = caraImages.size

    class MainHolder(private val binding : CardCaraImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(caraImage: CaraImageModel, listener: CaraImageListener) {
            binding.caraImageTitle.text = caraImage.title
            Picasso.get().load(caraImage.image).resize(380,250).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onCaraImageClick(caraImage) }
        }
    }
}