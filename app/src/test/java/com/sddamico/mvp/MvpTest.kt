package com.sddamico.mvp

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test


class MvpTest {
	private val mainThreadScheduler = TestScheduler()
	private val ioThreadScheduler = TestScheduler()

	@Before
	fun fixAndroidScheduler() {
		RxAndroidPlugins.setInitMainThreadSchedulerHandler { mainThreadScheduler }
		RxJavaPlugins.setInitIoSchedulerHandler { ioThreadScheduler }
	}

	@Test
	fun `test initial state`() {
		val presenterImpl = IncrementPresenter()
		val view = mock<IncrementRxViewContract> {}
		presenterImpl.attach(view)
		ioThreadScheduler.triggerActions()
		mainThreadScheduler.triggerActions()
		verify(view).setCountView("0")
	}


	@Test
	fun `test increment`() {
		val presenterImpl = IncrementPresenter()
		val view = mock<IncrementRxViewContract> {}
		presenterImpl.attach(view)
		presenterImpl.onIncrementClicked()
		ioThreadScheduler.triggerActions()
		mainThreadScheduler.triggerActions()
		verify(view).setCountView("0")
		verify(view).setCountView("1")
	}
}
