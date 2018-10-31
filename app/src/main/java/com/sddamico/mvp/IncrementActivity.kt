package com.sddamico.mvp

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_increment.*

class IncrementActivity : androidx.appcompat.app.AppCompatActivity(), ViewContract {

	private lateinit var presenter: PresenterContract

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		initPresenter()
		setContentView(R.layout.activity_increment)
		increment.setOnClickListener { presenter.onIncrementClicked() }
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
		val maybePresenter = lastCustomNonConfigurationInstance as PresenterContract?
		if (maybePresenter != null) {
			presenter = maybePresenter
		}
		if (!::presenter.isInitialized) {
			presenter = IncrementPresenter()
		}
	}

	override fun onRetainCustomNonConfigurationInstance(): Any = presenter
}

