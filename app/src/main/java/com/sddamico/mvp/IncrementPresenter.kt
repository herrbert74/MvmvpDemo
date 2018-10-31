package com.sddamico.mvp

typealias ViewContract = IncrementActivityContract.IncrementActivityViewContract
typealias PresenterContract = IncrementActivityContract.IncrementPresenterContract

interface IncrementActivityContract {
	interface IncrementActivityViewContract : MvpView {
		fun setCountView(countString : String)
	}

	interface IncrementPresenterContract : Presenter<IncrementActivityContract.IncrementActivityViewContract> {
		fun onIncrementClicked()
	}
}

class IncrementPresenter : BasePresenter<ViewContract>(), PresenterContract {

	private var count = 0

	override fun attach(view: ViewContract) {
		super.attach(view)

		view.setCountView(getCountString())
	}

	override fun onIncrementClicked() {
		count++

		view?.setCountView(getCountString())
	}

	private fun getCountString() = count.toString()
}