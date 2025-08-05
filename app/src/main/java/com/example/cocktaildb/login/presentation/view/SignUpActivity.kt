package com.example.cocktaildb.login.presentation.view
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
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

class SignUpActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_sign_up)

        googleAuthPresenter = GoogleAuthPresenter(this)
        firebaseAuthPresenter = FirebaseAuthPresenter()

        val tvLoginLink = findViewById<TextView>(R.id.tvLoginLink)
        val btnGoogle = findViewById<ImageView>(R.id.btnGoogle)
        val btnSignUp = findViewById<Button>(R.id.btnSignUp)
        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        val cbTerms = findViewById<CheckBox>(R.id.cbTerms)

        tvLoginLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        btnGoogle.setOnClickListener {
            val signInIntent = googleAuthPresenter.getSignInIntent()
            googleSignInLauncher.launch(signInIntent)
        }

        btnSignUp.setOnClickListener {
            firebaseAuthPresenter.signUpWithEmail(
                this,
                etName.text.toString(),
                etEmail.text.toString(),
                etPassword.text.toString(),
                etConfirmPassword.text.toString(),
                cbTerms.isChecked
            )
        }
    }
}
