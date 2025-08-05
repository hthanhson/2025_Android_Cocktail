package com.example.cocktaildb.login.presentation.presenter

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.example.cocktaildb.home.presentation.view.HomeActivity
import com.example.cocktaildb.login.presentation.data.model.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseAuthPresenter {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun loginWithEmail(activity: Activity, email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(activity, "Vui lòng nhập email và password", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    loginSuccess(activity)
                } else {
                    Toast.makeText(activity, "Email hoặc password không đúng", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun signUpWithEmail(
        activity: Activity,
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
        termsAccepted: Boolean
    ) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(activity, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(activity, "Password không khớp", Toast.LENGTH_SHORT).show()
            return
        }

        if (!termsAccepted) {
            Toast.makeText(activity, "Vui lòng đồng ý điều khoản", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser
                    saveUserToFirestore(activity, firebaseUser, name, password)
                } else {
                    Toast.makeText(activity, task.exception?.message ?: "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun saveGoogleUser(activity: Activity, account: GoogleSignInAccount) {
        val firebaseUser = auth.currentUser
        val name = account.displayName ?: ""
        val email = account.email ?: ""
        saveUserToFirestore(activity, firebaseUser, name, null)
    }

    private fun saveUserToFirestore(activity: Activity, firebaseUser: FirebaseUser?, name: String, password: String?) {
        val uid = firebaseUser?.uid ?: return
        val email = firebaseUser.email ?: ""

        val user = User(uid = uid, name = name, email = email, password = password)

        firestore.collection("users")
            .document(uid)
            .set(user)
            .addOnSuccessListener {
                loginSuccess(activity)
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Không thể lưu thông tin người dùng", Toast.LENGTH_SHORT).show()
            }
    }

    fun loginSuccess(activity: Activity) {
        activity.startActivity(Intent(activity, HomeActivity::class.java))
        activity.finish()
    }
}
