package com.code.fry

import com.code.fry.Logger.Logger
import com.code.fry.dao.Pads
import com.code.fry.dao.Results
import com.code.fry.db.DbSettings.DB
import com.code.fry.queue.Queue
import com.code.fry.states.initializers.Event
import com.code.fry.states.initializers.MyStateMachine
import com.code.fry.states.initializers.SideEffect
import com.code.fry.states.initializers.State
import com.tinder.StateMachine
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class MQMain {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val stateMachine = MyStateMachine.stateMachine
            val transition = stateMachine.transition(Event.OnStart)
            handleEvent(transition.event, stateMachine)
        }

        private fun handleEvent(event: Event, stateMachine: StateMachine<State, Event, SideEffect>) {
            var transition: StateMachine.Transition<State, Event, SideEffect>? = null
            when (event) {
                Event.OnStart -> {
                    Logger
                    transition = stateMachine.transition(Event.OnDbConnect)
                }
                Event.OnDbConnect -> {
                    DB
                    transaction {
                        transition = try {
                            SchemaUtils.create(Pads, Results)
                            stateMachine.transition(Event.OnDbConnected)
                        } catch (e: Throwable) {
                            stateMachine.transition(Event.OnFail(e))
                        }
                    }
                }
                Event.OnDbConnected -> {
                    transition = stateMachine.transition(Event.OnQueueConnect)
                }
                Event.OnQueueConnect -> {
                    transition = try {
                        Queue.start()
                        stateMachine.transition(Event.OnQueueConnected)
                    } catch (e: java.lang.Exception) {
                        stateMachine.transition(Event.OnFail(e))
                    }
                }
                Event.OnQueueConnected -> {
                    transition = stateMachine.transition(Event.OnSuccess)
                }
            }
            if (transition != null) {
                handleEvent(transition!!.event, stateMachine)
            }
        }
    }
}

