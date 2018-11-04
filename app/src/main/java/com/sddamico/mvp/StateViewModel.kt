package com.sddamico.mvp

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.BehaviorRelay

abstract class StateViewModel<S>(initialState: S) : ViewModel() {
	val state = BehaviorRelay.createDefault(initialState)!!
}