package com.code.fry.states.initializers

import com.code.fry.loggers.Logger.Logger
import com.tinder.StateMachine

object MyStateMachine {
    val stateMachine by lazy {
        StateMachine.create<State, Event, SideEffect> {
            initialState(State.Start)
            state<State.Start> {
                on<Event.OnStart> {
                    transitionTo(State.ConnectToDB, SideEffect.LogStart)
                }
            }
            state<State.ConnectToDB> {
                on<Event.OnDbConnected> {
                    transitionTo(State.ConnectQueue, SideEffect.LogDBConnected)
                }
                on<Event.OnFail> { event ->
                    Logger.error("DB Error", event.t)
                    transitionTo(State.Stop)
                }
            }
            state<State.ConnectQueue> {
                on<Event.OnQueueConnected> {
                    transitionTo(State.Success, SideEffect.LogQueueConnected)
                }
                on<Event.OnFail> { event ->
                    Logger.error("Queue Error", event.t)
                    transitionTo(State.Stop)
                }
            }
            state<State.Success> {

            }
            onTransition {
                val validTransition = it as? StateMachine.Transition.Valid ?: return@onTransition
                when (validTransition.sideEffect) {
                    SideEffect.LogQueueConnected -> Logger.info("Queue Connected")
                    SideEffect.LogDBConnected -> Logger.info("DB Connected")
                    SideEffect.LogStart -> Logger.info("Application Start, Initializing Logger")
                }
            }
        }
    }
}