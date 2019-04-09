package me.lamprosgk.lastfm.ui.detail

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.lamprosgk.lastfm.data.TracksRepository

class DetailPresenter(private val view: DetailContract.View, private val tracksRepository: TracksRepository) : DetailContract.Presenter {

    private var subscription: Disposable? = null

    override fun getTopTracksForArtist(name: String) {
        view.showLoading(true)

        subscription = tracksRepository.getTracks(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                // onSuccess
                {   view.showLoading(false)
                    view.showTracks(it) },
                // onError
                { view.showError(it) })
    }


    override fun onDestroy() {
        subscription?.dispose()
    }
}