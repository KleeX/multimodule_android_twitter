package com.klex.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.klex.presentation.tweets.TweetsPresenter
import com.klex.presentation.tweets.TweetsView
import com.klex.ui.R
import com.klex.ui.mvpx.MvpXFragment
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_tweets.*
import javax.inject.Inject

class TweetsFragment : MvpXFragment(), TweetsView {

    @Inject
    @InjectPresenter
    lateinit var presenter: TweetsPresenter

    @ProvidePresenter
    fun providePresenter(): TweetsPresenter = presenter

    private val tweetsAdapter = TweetsAdapter { presenter.tweets }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_tweets, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_tweets.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            context, RecyclerView.VERTICAL, false
        )
        rv_tweets.adapter = tweetsAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun showTweets() {
        tweetsAdapter.notifyDataSetChanged()
    }

    override fun openCreation() {

    }

    override fun onErrorLoadingTweets() {
        Toast.makeText(context, "Error loading tweets", Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }
}