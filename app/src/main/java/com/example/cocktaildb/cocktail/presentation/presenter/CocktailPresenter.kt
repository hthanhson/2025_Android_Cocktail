package com.example.cocktaildb.cocktail.presentation.presenter

import com.example.cocktaildb.base.BasePresenter
import com.example.cocktaildb.cocktail.data.model.Cocktail
import com.example.cocktaildb.cocktail.data.repository.ICocktailRepository
import com.example.cocktaildb.cocktail.presentation.contract.CocktailContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CocktailPresenter(
    private val cocktailRepository: ICocktailRepository
) : BasePresenter<CocktailContract.View>(), CocktailContract.Presenter {

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun loadCocktails() {
        getView()?.showLoading()
        scope.launch {
            try {
                val cocktails = withContext(Dispatchers.IO) {
                    cocktailRepository.getCocktails()
                }
                if (isViewAttached()) {
                    getView()?.hideLoading()
                    getView()?.showCocktails(cocktails)
                }
            } catch (e: Exception) {
                if (isViewAttached()) {
                    getView()?.hideLoading()
                    getView()?.showError("Failed to load cocktails: ${e.message}")
                }
            }
        }
    }

    override fun onCocktailClicked(cocktail: Cocktail) {
        if (isViewAttached()) {
            getView()?.showCocktailDetail(cocktail)
        }
    }

    override fun detachView() {
        super.detachView()
        scope.cancel()
    }
}
