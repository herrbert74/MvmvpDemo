package com.sddamico.mvp

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.zipWith

abstract class BasePresenter<S, out VM : StateViewModel<S>>(override val viewModel: VM) : Presenter<S, VM> {

	@SuppressLint("CheckResult")
	fun sendToViewModel(reducer: (S) -> S) {
		Observable.just(reducer)
				.observeOn(AndroidSchedulers.mainThread()) // ensures mutations happen serially on main thread
				.zipWith(viewModel.state)
				.map { (reducer, state) -> reducer.invoke(state) }
				.subscribe(viewModel.state)
	}
}