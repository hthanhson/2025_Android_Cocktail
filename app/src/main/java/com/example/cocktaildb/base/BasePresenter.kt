package com.example.cocktaildb.base

abstract class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V> {

    private var view: V? = null

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    protected fun getView(): V? = view

    protected fun isViewAttached(): Boolean = view != null
}
