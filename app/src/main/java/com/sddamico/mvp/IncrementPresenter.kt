package com.sddamico.mvp

interface IncrementPresenterContract : Presenter<IncrementState, IncrementStateViewModel> {
	fun onIncrementClicked()
}

class IncrementStateViewModel : StateViewModel<IncrementState>(IncrementState())

class IncrementPresenter(override val viewModel: IncrementStateViewModel)
	: BasePresenter<IncrementState, IncrementStateViewModel>(viewModel), IncrementPresenterContract {

	override fun onIncrementClicked() {
		sendToViewModel {
			it.copy(
					count = it.count.toInt().plus(1).toString()
			)
		}
	}
}