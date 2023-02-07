package ua.blackwind.data.api

sealed interface ApiState {
    object Loading: ApiState
    object Success: ApiState
    object Error: ApiState
}