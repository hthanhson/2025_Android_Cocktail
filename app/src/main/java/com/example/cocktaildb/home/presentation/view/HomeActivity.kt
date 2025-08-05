package com.example.cocktaildb.home.presentation.view

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktaildb.base.BaseActivity
import com.example.cocktaildb.cocktail.data.datasource.CocktailRemoteDataSource
import com.example.cocktaildb.cocktail.data.repository.CocktailRepository
import com.example.cocktaildb.cocktail.data.repository.ICocktailRepository
import com.example.cocktaildb.databinding.ActivityHomeBinding
import com.example.cocktaildb.home.presentation.adapter.PopularCocktailAdapter
import com.example.cocktaildb.home.presentation.contract.HomeContract
import com.example.cocktaildb.home.presentation.presenter.HomePresenter
import com.example.cocktaildb.utils.ImageLoader

class HomeActivity : BaseActivity<ActivityHomeBinding>(), HomeContract.View {

    private lateinit var presenter: HomePresenter
    private lateinit var repository: ICocktailRepository
    private lateinit var adapter: PopularCocktailAdapter

    override fun getViewBinding(): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun setupViews() {
        // Dependency injection setup
        val dataSource = CocktailRemoteDataSource()
        repository = CocktailRepository(dataSource)
        presenter = HomePresenter(repository)
        presenter.attachView(this)

        adapter = PopularCocktailAdapter { cocktail ->
            presenter.onCocktailClicked(cocktail)
        }

        binding.rvPopularCocktails.apply {
            layoutManager = GridLayoutManager(this@HomeActivity, 2)
            adapter = this@HomeActivity.adapter
        }

        // Load header background image
        ImageLoader.loadImage(binding.ivHeaderBackground, "https://cdn.tgdd.vn/2020/07/CookProduct/commercial0040-1200x676.jpg")
    }

    override fun setupListeners() {
        binding.searchBar.setOnClickListener {
            // Search functionality will be implemented in future versions
            Toast.makeText(this, "Search functionality coming soon!", Toast.LENGTH_SHORT).show()
        }

        binding.btnViewAll.setOnClickListener {
            presenter.onViewAllClicked()
        }
    }

    override fun showPopularCocktails(cocktails: List<com.example.cocktaildb.cocktail.data.model.Cocktail>) {
        adapter.submitList(cocktails)
    }

    override fun showCocktailDetail(cocktail: com.example.cocktaildb.cocktail.data.model.Cocktail) {
        Toast.makeText(
            this,
            "Selected: ${cocktail.name}",
            Toast.LENGTH_SHORT
        ).show()
        // Future: Navigate to detail screen
    }

    override fun navigateToAllCocktails() {
        // Navigation to all cocktails screen will be implemented in future versions
        Toast.makeText(this, "View All functionality coming soon!", Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        presenter.loadPopularCocktails()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
