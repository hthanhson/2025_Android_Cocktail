package com.example.cocktaildb.home.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktaildb.cocktail.data.model.Cocktail
import com.example.cocktaildb.databinding.ItemPopularCocktailBinding
import com.example.cocktaildb.utils.ImageLoader

class PopularCocktailAdapter(
    private val onCocktailClick: (Cocktail) -> Unit
) : ListAdapter<Cocktail, PopularCocktailAdapter.PopularCocktailViewHolder>(PopularCocktailDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularCocktailViewHolder {
        val binding = ItemPopularCocktailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PopularCocktailViewHolder(binding, onCocktailClick)
    }

    override fun onBindViewHolder(holder: PopularCocktailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PopularCocktailViewHolder(
        private val binding: ItemPopularCocktailBinding,
        private val onCocktailClick: (Cocktail) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cocktail: Cocktail) {
            binding.tvCocktailName.text = cocktail.name
            binding.tvCocktailCategory.text = cocktail.description

            // Set a default rating for now (you can add rating field to Cocktail model later)
            binding.tvRating.text = "4.8"

            // Load image from URL
            if (cocktail.imageUrl.isNotEmpty()) {
                ImageLoader.loadImage(binding.ivCocktail, cocktail.imageUrl)
            }

            itemView.setOnClickListener {
                onCocktailClick(cocktail)
            }
        }
    }

    private class PopularCocktailDiffCallback : DiffUtil.ItemCallback<Cocktail>() {
        override fun areItemsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
            return oldItem == newItem
        }
    }
}
