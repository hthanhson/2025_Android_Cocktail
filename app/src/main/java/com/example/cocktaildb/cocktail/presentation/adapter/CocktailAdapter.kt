package com.example.cocktaildb.cocktail.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktaildb.cocktail.data.model.Cocktail
import com.example.cocktaildb.databinding.ItemCocktailBinding
import com.example.cocktaildb.utils.ImageLoader

class CocktailAdapter(
    private val onCocktailClick: (Cocktail) -> Unit
) : ListAdapter<Cocktail, CocktailAdapter.CocktailViewHolder>(CocktailDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val binding = ItemCocktailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CocktailViewHolder(binding, onCocktailClick)
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CocktailViewHolder(
        private val binding: ItemCocktailBinding,
        private val onCocktailClick: (Cocktail) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cocktail: Cocktail) {
            binding.tvCocktailName.text = cocktail.name
            binding.tvCocktailDescription.text = cocktail.description
            binding.tvIngredients.text = "Ingredients: ${cocktail.ingredients.joinToString(", ")}"

            // Load image from URL if available
            if (cocktail.imageUrl.isNotEmpty()) {
                ImageLoader.loadImage(binding.ivCocktail, cocktail.imageUrl)
            }

            itemView.setOnClickListener {
                onCocktailClick(cocktail)
            }
        }
    }

    private class CocktailDiffCallback : DiffUtil.ItemCallback<Cocktail>() {
        override fun areItemsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
            return oldItem == newItem
        }
    }
}
