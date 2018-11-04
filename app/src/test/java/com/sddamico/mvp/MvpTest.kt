package com.sddamico.mvp

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test


class MvmvpTest {

	private val mainThreadScheduler = TestScheduler()
	private val ioThreadScheduler = TestScheduler()

	@Before
	fun fixAndroidScheduler() {
		RxAndroidPlugins.setInitMainThreadSchedulerHandler { mainThreadScheduler }
		RxJavaPlugins.setInitIoSchedulerHandler { ioThreadScheduler }
	}

	@Test
	fun `test initial state`() {
		val viewModel = IncrementStateViewModel()

		assert.that(viewModel.state.value.count, equalTo("0"))
	}


	@Test
	fun `test increment`() {
		val viewModel = IncrementStateViewModel()
		val presenterImpl = IncrementPresenter(viewModel)

		assert.that(viewModel.state.value.count, equalTo("0"))

		presenterImpl.onIncrementClicked()

		ioThreadScheduler.triggerActions()
		mainThreadScheduler.triggerActions()

		assert.that(viewModel.state.value.count, equalTo("1"))
	}
}