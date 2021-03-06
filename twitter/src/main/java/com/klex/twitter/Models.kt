package com.klex.twitter

data class TweetResponse(
    val username: String,
    val nickname: String,
    val userAvatar: String,
    val textContent: String,
    val pictures: List<String>,
    val created: String
)