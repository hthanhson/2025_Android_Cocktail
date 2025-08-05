package com.example.cocktaildb.home.presentation.contract

import com.example.cocktaildb.base.BaseContract
import com.example.cocktaildb.cocktail.data.model.Cocktail

interface HomeContract : BaseContract<HomeContract.View, HomeContract.Presenter> {
    interface View : BaseContract.View {
        fun showPopularCocktails(cocktails: List<Cocktail>)
        fun showCocktailDetail(cocktail: Cocktail)
        fun navigateToAllCocktails()
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun loadPopularCocktails()
        fun onCocktailClicked(cocktail: Cocktail)
        fun onViewAllClicked()
    }
}
