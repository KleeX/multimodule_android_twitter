package com.klex.presentation

class Tweet(
    val username: String,
    val nickname: String,
    val userAvatar: String,
    val textContent: String,
    val pictureUrl: String,
    val created: Long
) {
    lateinit var time: Time
}

enum class TimePointer {
    Seconds, Minutes, Hours, Yesterday, Later
}

data class Time(val time: Int, val timePointer: TimePointer)