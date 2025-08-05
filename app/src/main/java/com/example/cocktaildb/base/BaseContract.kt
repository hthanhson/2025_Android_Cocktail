package com.example.cocktaildb.base

interface BaseContract<in V : BaseContract.View, P : BaseContract.Presenter<V>> {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showError(message: String)
    }
    interface Presenter<in V : View> {
        fun attachView(view: V)
        fun detachView()
    }
}
