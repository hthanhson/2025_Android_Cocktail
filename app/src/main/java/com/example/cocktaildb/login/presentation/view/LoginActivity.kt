package com.example.cocktaildb.login.presentation.view

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.cocktaildb.R

import com.example.cocktaildb.login.presentation.presenter.FirebaseAuthPresenter
import com.example.cocktaildb.login.presentation.presenter.GoogleAuthPresenter

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var googleAuthPresenter: GoogleAuthPresenter
    private lateinit var firebaseAuthPresenter: FirebaseAuthPresenter

    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                googleAuthPresenter.handleSignInResult(
                    result.data,
                    onSuccess = { account: GoogleSignInAccount ->
                        firebaseAuthPresenter.loginSuccess(this)
                    },
                    onFailure = { error ->
                        Toast.makeText(this, "Google Sign-In failed: $error", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        googleAuthPresenter = GoogleAuthPresenter(this)
        firebaseAuthPresenter = FirebaseAuthPresenter()

        val tvSignUp = findViewById<TextView>(R.id.tvSignUp)
        val btnGoogle = findViewById<ImageView>(R.id.btnGoogle)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnSignIn = findViewById<TextView>(R.id.btnSignIn)

        tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        btnGoogle.setOnClickListener {
            val signInIntent = googleAuthPresenter.getSignInIntent()
            googleSignInLauncher.launch(signInIntent)
        }

        btnSignIn.setOnClickListener {
            firebaseAuthPresenter.loginWithEmail(this, etEmail.text.toString(), etPassword.text.toString())
        }
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)

        tvForgotPassword.setOnClickListener {
            val email = etEmail.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập email trước khi đặt lại mật khẩu", Toast.LENGTH_SHORT).show()
            } else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Vui lòng kiểm tra hòm thư để thay đổi mật khẩu", Toast.LENGTH_LONG).show()
                        } else {
                            val error = task.exception?.message ?: "Đã xảy ra lỗi"
                            Toast.makeText(this, "Không thể gửi email: $error", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }


    }
}