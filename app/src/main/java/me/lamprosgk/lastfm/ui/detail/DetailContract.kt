package me.lamprosgk.lastfm.ui.detail

import me.lamprosgk.lastfm.BasePresenter
import me.lamprosgk.lastfm.BaseView
import me.lamprosgk.lastfm.model.Track

/**
 * MVP contract between presenter and view for detail screen
 */
interface DetailContract {

    interface View : BaseView<Presenter> {

        fun showTracks(tracks: List<Track>?)

    }

    interface Presenter : BasePresenter {

        fun getTopTracksForArtist(name: String)
    }
}