package com.klex.domain

data class Tweet(
    val username: String,
    val nickname: String,
    val userAvatar: String,
    val textContent: String,
    val pictureUrl: String,
    val created: Long
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Tweet

        if (username != other.username) return false
        if (nickname != other.nickname) return false
        if (userAvatar != other.userAvatar) return false
        if (textContent != other.textContent) return false
        if (pictureUrl != other.pictureUrl) return false
        if (created != other.created) return false

        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + nickname.hashCode()
        result = 31 * result + userAvatar.hashCode()
        result = 31 * result + textContent.hashCode()
        result = 31 * result + pictureUrl.hashCode()
        result = 31 * result + created.hashCode()
        return result
    }

    override fun toString(): String {
        return "Tweet(username='$username', nickname='$nickname', userAvatar='$userAvatar', textContent='$textContent', pictureUrl='$pictureUrl', created=$created)"
    }
}

class TweetPending(val text: String = "", val picturePath: String = "")