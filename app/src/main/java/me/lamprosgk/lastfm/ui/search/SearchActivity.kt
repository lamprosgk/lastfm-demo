package me.lamprosgk.lastfm.ui.search

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.widget.EditText
import android.widget.ImageView
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import me.lamprosgk.lastfm.R
import me.lamprosgk.lastfm.di.Injector
import me.lamprosgk.lastfm.model.Artist
import me.lamprosgk.lastfm.ui.detail.DetailActivity
import me.lamprosgk.lastfm.ui.search.SearchContract.Presenter
import me.lamprosgk.lastfm.ui.search.adapter.ArtistsAdapter
import me.lamprosgk.lastfm.ui.search.adapter.ArtistsAdapterObserver
import java.util.concurrent.TimeUnit


class SearchActivity : AppCompatActivity(), SearchContract.View, ArtistsAdapterObserver.OnResultEmptyListener {

    override lateinit var mPresenter: Presenter
    lateinit var mSearchViewDisposable: Disposable

    companion object {
        internal const val TAG = "MainActivity"
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPresenter = SearchPresenter(this, Injector.provideArtistsRepository())
        setupRecyclerView()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        setupSearchView(menu, searchManager)
        return true
    }

    private fun setupSearchView(menu: Menu, searchManager: SearchManager) {
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.apply {
            queryHint = getString(R.string.search_hint)
            setSearchableInfo(searchManager.getSearchableInfo(componentName))

            val topSearchEditText = findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text)

            mSearchViewDisposable = topSearchEditText.textChanges()
                .filter {
                    if (it.isEmpty()) {
                        showArtistMatches(null)
                    }
                    it.length > 1
                }
                .debounce(250, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.d(TAG, "Input: $it")
                    mPresenter.getArtistsForName(it.toString())
                }

        }
    }

    private fun setupRecyclerView() {
        artistsRecyclerView.layoutManager = LinearLayoutManager(this)

        artistsRecyclerView.adapter = ArtistsAdapter(object : ArtistsAdapter.ItemClickListener {
            override fun onItemClick(artist: Artist, artistIcon: ImageView) {

                val transition = ViewCompat.getTransitionName(artistIcon)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@SearchActivity, artistIcon,
                    transition!!
                )
                DetailActivity.navigate(this@SearchActivity, artist, transition, options.toBundle())
            }

        })

        (artistsRecyclerView.adapter as ArtistsAdapter).registerAdapterDataObserver(
            ArtistsAdapterObserver(this, artistsRecyclerView, emptyView))

    }

    override fun showLoading(loading: Boolean) {
        emptyView.text =  if (loading) getString(R.string.search_message_searching)
                            else getString(R.string.search_message_empty)
    }

    override fun showError(error: Throwable) {
        emptyView.text = getString(R.string.search_message_error, error.localizedMessage)
    }

    override fun onResultEmpty() {
        emptyView.text = getString(R.string.search_message_empty)
    }

    override fun showArtistMatches(artists: List<Artist>?) {
        (artistsRecyclerView.adapter as ArtistsAdapter).setItems(artists)
    }

    override fun onDestroy() {
        super.onDestroy()
        mSearchViewDisposable.dispose()
        mPresenter.onDestroy()
    }


}
