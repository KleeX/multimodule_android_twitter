package com.klex.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.plusAssign
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.klex.presentation.tweets.TweetsPresenter
import com.klex.presentation.tweets.TweetsView
import com.klex.ui.R
import com.klex.ui.mvpx.MvpXFragment
import com.klex.ui.navigation.KeepStateNavigator
import com.klex.ui.show
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
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        presenter.checkTweets()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_tweets, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireActivity(), R.id.main_nav_host_fragment)
        val navHostFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.main_nav_host_fragment)!!
        val navigator = KeepStateNavigator(
            requireContext(),
            navHostFragment.childFragmentManager,
            R.id.main_nav_host_fragment
        )
        navController.navigatorProvider += navigator
        rv_tweets.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            context, RecyclerView.VERTICAL, false
        )
        rv_tweets.adapter = tweetsAdapter
        swipe_to_refresh.setOnRefreshListener {
            presenter.loadTweets()
        }
        fab_compose_tweet.setOnClickListener {
            presenter.composeTweet()
        }
    }

    override fun showComposeTweet() {
        navController.navigate(R.id.composeFragment)
    }

    override fun showTweets() {
        tweetsAdapter.notifyDataSetChanged()
    }

    override fun onErrorLoadingTweets() {
        Toast.makeText(context, getString(R.string.error_loading_tweets), Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        progress.show = true
    }

    override fun hideLoading() {
        progress.show = false
        swipe_to_refresh.isRefreshing = false
    }

    override fun showPendingTweet() {
        tv_uploading.show = true
    }

    override fun hidePendingTweet() {
        tv_uploading.show = false
    }

    override fun notifyTweetAdded() {
        tweetsAdapter.notifyItemInserted(0)
        rv_tweets.scrollToPosition(0)
    }
}