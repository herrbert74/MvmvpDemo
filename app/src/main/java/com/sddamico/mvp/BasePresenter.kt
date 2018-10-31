package com.sddamico.mvp

import com.babestudios.incrementmvp.rx.PresenterScope
import com.jakewharton.rxrelay2.BehaviorRelay
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.lifecycle.CorrespondingEventsFunction
import com.uber.autodispose.lifecycle.LifecycleScopeProvider
import com.uber.autodispose.lifecycle.LifecycleScopes
import io.reactivex.CompletableSource
import io.reactivex.Observable

abstract class BasePresenter<V : MvpView> : Presenter<V>, LifecycleScopeProvider<PresenterScope> {

	private val lifecycle = BehaviorRelay.createDefault<PresenterScope>(PresenterScope.DETACHED)!!

	var view: V? = null

	override fun attach(view: V) {
		this.view = view
		lifecycle.accept(PresenterScope.ATTACHED)
	}

	override fun detach() {
		this.view = null
		lifecycle.accept(PresenterScope.DETACHED)
	}

	override fun lifecycle(): Observable<PresenterScope> = lifecycle

	override fun correspondingEvents(): CorrespondingEventsFunction<PresenterScope> {
		return CorrespondingEventsFunction {
			when (it) {
				PresenterScope.DETACHED -> PresenterScope.ATTACHED
				PresenterScope.ATTACHED -> PresenterScope.DETACHED
			}
		}
	}

	override fun peekLifecycle(): PresenterScope? = lifecycle.value

	override fun requestScope(): CompletableSource = LifecycleScopes.resolveScopeFromLifecycle(this)

	private inline fun <T> autoDisposable(it: Observable<T>) = AutoDispose.autoDisposable<T>(this).apply(it)

	internal inline fun <T> Observable<T>.autoDispose() = this.`as` { autoDisposable(this) }
}