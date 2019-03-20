package com.klex.storage

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.google.gson.GsonBuilder
import com.klex.storage.interfaces.IPendingTweet
import io.reactivex.Observable

class PendingTweet(context: Context) : IPendingTweet {

    private val preferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)
    private var rxPreferences = RxSharedPreferences.create(preferences)
    private val gson = GsonBuilder().create()

    override fun clear() {
        preferences.edit()
            .clear()
            .apply()
    }

    //json used for observing just one field
    override fun putPendingTweet(text: String, picturePath: String) {
        preferences.edit()
            .putString(TWEET_JSON, gson.toJson(TweetPendingEntity(text, picturePath)))
            .apply()
    }

    override fun observePendingTweet(): Observable<TweetPendingEntity> =
        rxPreferences.getString(TWEET_JSON, "")
            .asObservable()
            .map {
                return@map if (it.isEmpty()) TweetPendingEntity()
                else gson.fromJson(it, TweetPendingEntity::class.java)
            }

    companion object {
        private const val TWEET_JSON = "tweet_json"
    }
}