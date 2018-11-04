package com.sddamico.mvp

interface Presenter<State, out VM : StateViewModel<State>> {
	val viewModel: VM
}