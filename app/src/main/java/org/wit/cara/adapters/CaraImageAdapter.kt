package org.wit.cara.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.cara.databinding.CardCaraImageBinding
import org.wit.cara.models.CaraImageModel

interface PlacemarkListener {
    fun onPlacemarkClick(caraImage: CaraImageModel)
}

class CaraImageAdapter constructor(private var caraImages: List<CaraImageModel>,
                                   private val listener: PlacemarkListener) :
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

        fun bind(caraImage: CaraImageModel, listener: PlacemarkListener) {
            binding.caraImageTitle.text = caraImage.title
            binding.description.text = caraImage.description
            Picasso.get().load(caraImage.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onPlacemarkClick(caraImage) }
        }
    }
}