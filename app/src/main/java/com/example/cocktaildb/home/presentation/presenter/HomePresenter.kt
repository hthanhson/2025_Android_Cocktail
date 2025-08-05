package com.example.cocktaildb.home.presentation.presenter

import com.example.cocktaildb.base.BasePresenter
import com.example.cocktaildb.cocktail.data.repository.ICocktailRepository
import com.example.cocktaildb.home.presentation.contract.HomeContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomePresenter(
    private val cocktailRepository: ICocktailRepository
) : BasePresenter<HomeContract.View>(), HomeContract.Presenter {

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun loadPopularCocktails() {
        getView()?.showLoading()
        scope.launch {
            try {
                val cocktails = withContext(Dispatchers.IO) {
                    cocktailRepository.getCocktails()
                }
                if (isViewAttached()) {
                    getView()?.hideLoading()
                    getView()?.showPopularCocktails(cocktails)
                }
            } catch (e: Exception) {
                if (isViewAttached()) {
                    getView()?.hideLoading()
                    getView()?.showError("Failed to load popular cocktails: ${e.message}")
                }
            }
        }
    }

    override fun onCocktailClicked(cocktail: com.example.cocktaildb.cocktail.data.model.Cocktail) {
        if (isViewAttached()) {
            getView()?.showCocktailDetail(cocktail)
        }
    }

    override fun onViewAllClicked() {
        if (isViewAttached()) {
            getView()?.navigateToAllCocktails()
        }
    }

    override fun detachView() {
        super.detachView()
        scope.cancel()
    }
}
