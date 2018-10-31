package com.sddamico.mvp

abstract class BasePresenter<V : MvpView> : Presenter<V> {

	var view: V? = null

	override fun attach(view: V) {
		this.view = view
	}

	override fun detach() {
		this.view = null
	}
}