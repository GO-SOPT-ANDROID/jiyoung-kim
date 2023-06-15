package org.android.go.sopt.util.state

sealed class UiState {
    object Loading : UiState()
    object Success : UiState()
    data class Failure(val code: Int?) : UiState()
}
