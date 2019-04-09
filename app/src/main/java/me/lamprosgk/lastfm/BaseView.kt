package me.lamprosgk.lastfm

interface BaseView<T> {

    var mPresenter: T
    fun showLoading(loading: Boolean)
    fun showError(error: Throwable)

}