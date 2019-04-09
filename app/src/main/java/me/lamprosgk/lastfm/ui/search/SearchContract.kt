package me.lamprosgk.lastfm.ui.search

import me.lamprosgk.lastfm.BasePresenter
import me.lamprosgk.lastfm.BaseView
import me.lamprosgk.lastfm.model.Artist

/**
 * MVP contract between presenter and view for search screen
 */
interface SearchContract {

    interface View : BaseView<Presenter> {

        fun showArtistMatches(artists: List<Artist>?)
    }

    interface Presenter : BasePresenter {

        fun getArtistsForName(name: String)
    }
}