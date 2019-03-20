package com.klex.interactors

import io.reactivex.Scheduler

interface SchedulerDataSource {
    fun main(): Scheduler
    fun io(): Scheduler
}