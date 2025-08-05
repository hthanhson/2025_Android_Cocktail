package com.example.cocktaildb.cocktail.presentation.contract

import com.example.cocktaildb.base.BaseContract
import com.example.cocktaildb.cocktail.data.model.Cocktail

interface CocktailContract : BaseContract<CocktailContract.View, CocktailContract.Presenter> {
    interface View : BaseContract.View {
        fun showCocktails(cocktails: List<Cocktail>)
        fun showCocktailDetail(cocktail: Cocktail)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun loadCocktails()
        fun onCocktailClicked(cocktail: Cocktail)
    }
}
