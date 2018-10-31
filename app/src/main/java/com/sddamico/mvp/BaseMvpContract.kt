package com.sddamico.mvp

interface MvpView

interface Presenter<V : MvpView> {
	fun attach(view: V)

	fun detach()
}