package com.klex.ui.feed

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import com.klex.ui.mvpx.MvpXFragment

class TweetsFragment : MvpXFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.registerReceiver(statusReceiver, IntentFilter(TwitterActionUploadSuccess))
        activity?.registerReceiver(statusReceiver, IntentFilter(TwitterActionUploadFailure))
        activity?.registerReceiver(statusReceiver, IntentFilter(TwitterActionUploadCancel))
    }

    override fun onDestroy() {
        activity?.unregisterReceiver(statusReceiver)
        super.onDestroy()
    }

    private val statusReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                TwitterActionUploadSuccess -> {

                }
                TwitterActionUploadFailure -> {

                }
                TwitterActionUploadCancel -> {

                }
            }
        }
    }
}