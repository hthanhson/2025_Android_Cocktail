package com.example.cocktaildb

import android.content.Intent
import com.example.cocktaildb.base.BaseActivity
import com.example.cocktaildb.databinding.ActivityFrontPageBinding
import com.example.cocktaildb.login.presentation.view.LoginActivity

class MainActivity : BaseActivity<ActivityFrontPageBinding>() {

    override fun getViewBinding(): ActivityFrontPageBinding {
        return ActivityFrontPageBinding.inflate(layoutInflater)
    }

    override fun setupViews() {
        // Setup any views if needed
    }

    override fun setupListeners() {
        binding.btnNavigateToCocktails.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
