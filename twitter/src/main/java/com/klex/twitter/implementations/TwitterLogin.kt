package com.klex.twitter.implementations

import android.content.Context
import android.content.Intent
import android.os.Build
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import com.klex.twitter.FakeTwitterActivity
import com.klex.twitter.interfaces.TwitterLoginSource
import com.twitter.sdk.android.core.TwitterCore


class TwitterLogin(private val context: Context) : TwitterLoginSource {

    override fun authenticate() {
        context.startActivity(Intent(context, FakeTwitterActivity::class.java))
    }

    override fun logout() {
        val twitterSession = TwitterCore.getInstance().sessionManager.activeSession
        if (twitterSession != null) {
            clearCookies()
            TwitterCore.getInstance().sessionManager.clearActiveSession()
        }
    }

    private fun clearCookies() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null)
            CookieManager.getInstance().flush()
        } else {
            val cookieSyncManager = CookieSyncManager.createInstance(context)
            cookieSyncManager.startSync()
            val cookieManager = CookieManager.getInstance()
            cookieManager.removeAllCookie()
            cookieManager.removeSessionCookie()
            cookieSyncManager.stopSync()
            cookieSyncManager.sync()
        }
    }
}