package com.klex.domain

data class Tweet(
    val username: String,
    val nickname: String,
    val userAvatar: String,
    val textContent: String,
    val pictureUrl: String,
    val created: Long
)

class TweetPending(val text: String = "", val picturePath: String = "")