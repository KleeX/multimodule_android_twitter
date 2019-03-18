package com.klex.storage

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.google.gson.GsonBuilder
import io.reactivex.Observable

class PendingTweet(context: Context) {

    private val preferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)
    private var rxPreferences = RxSharedPreferences.create(preferences)
    val gson = GsonBuilder().create()

    fun clearFull() {
        preferences.edit()
            .clear()
            .apply()
    }

    fun putPendingTweet(text: String = "", picturePath: String = "") {
        preferences.edit()
            .putString(TWEET_JSON, gson.toJson(TweetPendingEntity(text, picturePath)))
            .apply()
    }

    fun observePendingTweet(): Observable<TweetPendingEntity> =
        rxPreferences.getString(TWEET_JSON, "").asObservable()
            .map {
                return@map if (it.isEmpty()) TweetPendingEntity()
                else gson.fromJson(it, TweetPendingEntity::class.java)
            }

    companion object {
        private const val TWEET_JSON = "tweet_json"
    }
}