package com.klex.storage

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.f2prateek.rx.preferences2.RxSharedPreferences
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables

class PendingTweet(context: Context) {

    private val preferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)
    private var rxPreferences = RxSharedPreferences.create(preferences)

    fun clearFull() {
        preferences.edit()
            .clear()
            .apply()
    }

    fun putPendingTweet(text: String = "", picturePath: String = "") {
        preferences.edit()
            .putString(TWEET_TEXT, text)
            .putString(TWEET_PICTURE, picturePath)
            .apply()
    }

    companion object {
        private const val TWEET_TEXT = "tweet_text"
        private const val TWEET_PICTURE = "tweet_pic"
    }

    fun observePendingTweet(): Observable<TweetPendingEntity> =
        Observables.combineLatest(
            rxPreferences.getString(TWEET_TEXT, "").asObservable(),
            rxPreferences.getString(TWEET_PICTURE, "").asObservable()
        ) { text, picture ->
            return@combineLatest TweetPendingEntity(text, picture)
        }
}