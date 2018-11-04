package com.sddamico.mvp

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.rxbinding2.view.clicks
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.uber.autodispose.AutoDispose.autoDisposable
import com.uber.autodispose.ScopeProvider
import com.ubercab.autodispose.rxlifecycle.RxLifecycleInterop
import io.reactivex.CompletableSource
import kotlinx.android.synthetic.main.activity_increment.*

class IncrementActivity : RxAppCompatActivity(), ScopeProvider {

	override fun requestScope(): CompletableSource = RxLifecycleInterop.from(this).requestScope()

	private val viewModel by lazy { ViewModelProviders.of(this).get(IncrementStateViewModel::class.java) }

	private lateinit var presenter: IncrementPresenterContract

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		initPresenter()

		setContentView(R.layout.activity_increment)

		observeActions()
		observeState()
	}


	private fun observeState() {
		viewModel.state
				.`as`(autoDisposable(this))
				.subscribe { render(it) }
	}

	private fun render(state: IncrementState) {
		counter.text = state.count
	}

	private fun observeActions() {
		increment.clicks()
				.`as`(autoDisposable(this))
				.subscribe { presenter.onIncrementClicked() }
	}

	private fun initPresenter() {
		val maybePresenter = lastCustomNonConfigurationInstance as IncrementPresenterContract?

		if (maybePresenter != null) {
			presenter = maybePresenter
		}

		if (!::presenter.isInitialized) {
			presenter = IncrementPresenter(viewModel)
		}
	}

	override fun onRetainCustomNonConfigurationInstance(): Any = presenter
}