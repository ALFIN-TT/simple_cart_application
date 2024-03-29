package com.alfin.productlistingapp.core.presentation.state

import com.alfin.productlistingapp.core.data.state.State


/**
 * A base UI state
 */
open class BaseState(
    var uiState: State = State.INITIAL,
    var errorMessage: String? = ""
)
