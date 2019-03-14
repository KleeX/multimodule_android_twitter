package com.klex.twitter

const val TwitterActionUploadSuccess = "com.klex.twitter.upload.success"
const val TwitterActionUploadFailure = "com.klex.twitter.upload.failure"
const val TwitterActionUploadCancel = "com.klex.twitter.upload.cancel"

data class TweetResponse(
    val username: String,
    val nickname: String,
    val userAvatar: String,
    val textContent: String,
    val pictures: List<String>
)