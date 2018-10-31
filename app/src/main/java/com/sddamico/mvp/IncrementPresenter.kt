package com.sddamico.mvp

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface IncrementRxViewContract : MvpView {
	fun setCountView(countString: String)
}

interface IncrementRxPresenterContract : Presenter<IncrementRxViewContract> {
	fun onIncrementClicked()
}

class IncrementPresenter : BasePresenter<IncrementRxViewContract>(), IncrementRxPresenterContract {

	private var count = BehaviorRelay.createDefault(0)

	override fun attach(view: IncrementRxViewContract) {
		super.attach(view)

		getCountString()
				.autoDispose()
				.subscribe { view.setCountView(it) }
	}

	override fun onIncrementClicked() {
		count.take(1)
				.observeOn(Schedulers.io())
				.map { it.plus(1) }
				.observeOn(AndroidSchedulers.mainThread())
				.autoDispose()
				.subscribe(count)
	}

	private fun getCountString() = count.map { it.toString() }
}