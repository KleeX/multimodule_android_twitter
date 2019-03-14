package com.klex.twitter.fake_activities

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.tweetcomposer.ComposerActivity
import java.io.File


class FakeTweetActivity : AppCompatActivity() {

    companion object {
        const val TWEET_TEXT = "tweet_text"
        const val TWEET_PICTURE = "tweet_pictures"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val imagePath = intent?.extras?.getString(TWEET_PICTURE)
        val uri = if (imagePath != null) Uri.fromFile(File(imagePath)) else null
        val text = intent?.extras?.getString(TWEET_TEXT)
        val session = TwitterCore.getInstance().sessionManager
            .activeSession
        val intent = ComposerActivity.Builder(this)
            .session(session)
            .image(uri)
            .text(text)
            .createIntent()
        startActivity(intent)
        finish()
    }
}