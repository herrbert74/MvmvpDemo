package com.sddamico.mvp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.clicks
import kotlinx.android.synthetic.main.activity_increment.*

class IncrementActivity : AppCompatActivity(), IncrementRxViewContract {

	private lateinit var presenter: IncrementRxPresenterContract

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		initPresenter()

		setContentView(R.layout.activity_increment)

		increment.clicks().subscribe { presenter.onIncrementClicked() }
	}

	override fun onStart() {
		super.onStart()

		presenter.attach(this)
	}

	override fun onStop() {
		presenter.detach()

		super.onStop()
	}

	override fun setCountView(countString: String) {
		counter.text = countString
	}

	private fun initPresenter() {
		val maybePresenter = lastCustomNonConfigurationInstance as IncrementRxPresenterContract?

		if (maybePresenter != null) {
			presenter = maybePresenter
		}

		if (!::presenter.isInitialized) {
			presenter = IncrementPresenter()
		}
	}

	override fun onRetainCustomNonConfigurationInstance(): Any = presenter
}