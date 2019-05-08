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

        subscription = artistsRepository.getArtists(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showLoading(true) }
            .doOnTerminate { view.showLoading(false) }
            .subscribe(
                // onNext
                { view.showArtistMatches(it) },
                // onError
                {
                    view.showError(it)
                    Log.e("SearchPresenter", it.message)
                })
    }

    override fun onDestroy() {
        subscription?.dispose()
    }


}
