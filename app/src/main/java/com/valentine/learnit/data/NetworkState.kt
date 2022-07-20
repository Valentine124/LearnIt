package com.valentine.learnit.data
enum class State {
    LOADING,
    SUCCESS,
    FAILED
}
class NetworkState(val status: State, val message: String) {
    companion object {
        val SUCCESS: NetworkState
        val LOADING: NetworkState
        val ERROR: NetworkState

        init {
            SUCCESS = NetworkState(State.SUCCESS, "Success")
            LOADING = NetworkState(State.LOADING, "Loading")
            ERROR = NetworkState(State.FAILED, "Something Went Wrong!")
        }
    }
}