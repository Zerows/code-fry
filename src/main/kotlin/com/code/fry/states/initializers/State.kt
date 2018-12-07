package com.code.fry.states.initializers

sealed class State {
    object Start : State()
    object ConnectToDB : State()
    object ConnectQueue : State()
    object Success : State()
    object Stop : State()
}

sealed class Event {
    object OnStart : Event()
    object OnDbConnect : Event()
    object OnDbConnected : Event()
    object OnQueueConnect : Event()
    object OnQueueConnected : Event()
    object OnSuccess : Event()
    data class OnFail(val t: Throwable?) : Event()
}

sealed class SideEffect {
    object LogStart : SideEffect()
    object LogDBConnected : SideEffect()
    object LogQueueConnected : SideEffect()
    object LogSuccess : SideEffect()
}