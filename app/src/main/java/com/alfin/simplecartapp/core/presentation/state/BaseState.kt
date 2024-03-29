package com.alfin.simplecartapp.core.presentation.state

import com.alfin.simplecartapp.core.data.state.State


/**
 * A base UI state
 */
open class BaseState(
    var uiState: State = State.INITIAL,
    var errorMessage: String? = ""
)
