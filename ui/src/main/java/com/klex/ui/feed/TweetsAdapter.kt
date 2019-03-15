package com.klex.ui.feed

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.klex.presentation.TimePointer
import com.klex.presentation.Tweet
import com.klex.ui.R
import com.klex.ui.fromUrl
import com.klex.ui.fromUrlCircle
import com.klex.ui.show
import java.text.SimpleDateFormat

class TweetsAdapter(val dataSource: () -> List<Tweet>) :
    RecyclerView.Adapter<TweetsAdapter.TweetsViewHolder>() {

    @SuppressLint("SimpleDateFormat")
    private val dateFormatYesterday = SimpleDateFormat("HH:mm")
    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("EEE MMM d HH:mm")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetsViewHolder =
        TweetsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_tweet,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = dataSource().size

    override fun onBindViewHolder(holder: TweetsViewHolder, position: Int) {
        val tweet = dataSource()[position]

        holder.name.text = tweet.username
        holder.nickname.text = tweet.nickname
        holder.content.text = tweet.textContent

        holder.age.text = when (tweet.time.timePointer) {
            TimePointer.Seconds -> "${tweet.time.time} ${holder.age.context.getString(R.string.seconds)}"
            TimePointer.Minutes -> "${tweet.time.time} ${holder.age.context.getString(R.string.minutes)}"
            TimePointer.Hours -> "${tweet.time.time} ${holder.age.context.getString(R.string.hours)}"
            TimePointer.Yesterday -> "${holder.age.context.getString(R.string.yesterday)} " +
                    dateFormatYesterday.format(tweet.time.time * 1000)
            TimePointer.Later -> dateFormat.format(tweet.time.time * 1000)
        }

        if (tweet.userAvatar.isNotEmpty())
            holder.avatar.fromUrlCircle(tweet.userAvatar)

        if (tweet.pictureUrl.isNotEmpty()) {
            holder.picture.show = true
            holder.picture.fromUrl(tweet.pictureUrl)
        } else {
            holder.picture.show = false
        }
    }

    inner class TweetsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar: ImageView = view.findViewById(R.id.iv_avatar)
        val name: TextView = view.findViewById(R.id.tv_username)
        val nickname: TextView = view.findViewById(R.id.tv_nickname)
        val age: TextView = view.findViewById(R.id.tv_age)
        val content: TextView = view.findViewById(R.id.tv_content)
        val picture: ImageView = view.findViewById(R.id.iv_media_picture)
    }
}