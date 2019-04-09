package me.lamprosgk.lastfm.ui.search

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.lamprosgk.lastfm.data.ArtistsRepository

class SearchPresenter(private val view: SearchContract.View, private val artistsRepository: ArtistsRepository) :
    SearchContract.Presenter {

    private var subscription: Disposable? = null

    override fun getArtistsForName(name: String) {
        view.showLoading(true)

        subscription = artistsRepository.getArtists(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                // onNext
                {
                    view.showLoading(false)
                    view.showArtistMatches(it)
                },
                // onError
                {
                    view.showLoading(false)
                    view.showError(it)
                    Log.e("SearchPresenter", it.message)
                })
    }

    override fun onDestroy() {
        subscription?.dispose()
    }


}
