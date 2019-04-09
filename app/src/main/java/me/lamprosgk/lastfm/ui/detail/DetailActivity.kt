package me.lamprosgk.lastfm.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import me.lamprosgk.lastfm.R
import me.lamprosgk.lastfm.di.Injector
import me.lamprosgk.lastfm.model.Artist
import me.lamprosgk.lastfm.model.Track
import me.lamprosgk.lastfm.ui.detail.adapter.TracksAdapter
import me.lamprosgk.lastfm.util.getImageUrl
import me.lamprosgk.lastfm.util.share


class DetailActivity : AppCompatActivity(), DetailContract.View {


    override lateinit var mPresenter: DetailContract.Presenter
    private lateinit var artist: Artist

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(me.lamprosgk.lastfm.R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        with(intent?: return) {
            artist = getParcelableExtra(EXTRA_ARTIST) as Artist
            val transitionName = getStringExtra(EXTRA_ARTIST_IMAGE_TRANSITION_NAME)
            supportActionBar?.title = artist.name
            artistImageDetail.transitionName = transitionName
        }

        setupViews()
        mPresenter = DetailPresenter(this, Injector.provideTracksRepository())
        mPresenter.getTopTracksForArtist(artist.name)
        loadArtistImage("extralarge")
    }

    private fun setupViews() {
        fab.setOnClickListener { share(artist.url) }

        tracksRecyclerView.layoutManager = LinearLayoutManager(this)
        tracksRecyclerView.adapter = TracksAdapter()

        detailListenersTextView.text = getString(R.string.detail_listeners_template, artist.listeners)
    }

    private fun loadArtistImage(size: String) {
        Picasso.get()
            .load(artist.getImageUrl(size))
            .fit()
            .centerCrop()
            .into(artistImageDetail, object : Callback {
                override fun onSuccess() {
                    supportStartPostponedEnterTransition()
                }

                override fun onError(e: Exception?) {
                    supportStartPostponedEnterTransition()
                }
            })
    }


    override fun showLoading(loading: Boolean) {
        detailTracksEmptyTextView.setText(R.string.detail_message_loading)
        detailTracksEmptyTextView.visibility = if (loading) View.VISIBLE else View.GONE
    }

    override fun showError(error: Throwable) {
        detailTracksEmptyTextView.setText(R.string.detail_message_error)
        detailTracksEmptyTextView.visibility = View.VISIBLE
    }

    override fun showTracks(tracks: List<Track>?) {
        (tracksRecyclerView.adapter as TracksAdapter).setItems(tracks)
        Log.d(TAG, tracks?.get(0)?.name)

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }



    companion object {

        const val TAG = "DetailActivity"
        const val EXTRA_ARTIST = "me.lamprosgk.lastfm.DetailActivity.EXTRA_ARTIST"
        const val EXTRA_ARTIST_IMAGE_TRANSITION_NAME = "me.lamprosgk.lastfm.DetailActivity.EXTRA_ARTIST_IMAGE_TRANSITION_NAME"

        fun navigate(context: Context, artist: Artist, transition: String, bundle: Bundle?) {
            val intent = Intent(context, DetailActivity::class.java)
                .putExtra(EXTRA_ARTIST, artist)
                .putExtra(EXTRA_ARTIST_IMAGE_TRANSITION_NAME, transition)
                context.startActivity(intent, bundle)
        }

    }

}
