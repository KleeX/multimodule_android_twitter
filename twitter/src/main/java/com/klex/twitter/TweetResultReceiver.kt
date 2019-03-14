package com.klex.twitter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.twitter.sdk.android.tweetcomposer.TweetUploadService


class TweetResultReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when {
            TweetUploadService.UPLOAD_SUCCESS == intent.action -> {
                context.sendBroadcast(Intent(TwitterActionUploadSuccess))
            }
            TweetUploadService.UPLOAD_FAILURE == intent.action -> {
                context.sendBroadcast(Intent(TwitterActionUploadFailure))
            }
            TweetUploadService.TWEET_COMPOSE_CANCEL == intent.action -> {
                context.sendBroadcast(Intent(TwitterActionUploadCancel))
            }
        }
    }
}