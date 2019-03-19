package com.klex.domain

import io.reactivex.Scheduler

interface SchedulerDataSource {
    fun main(): Scheduler
    fun io(): Scheduler
}