package me.lamprosgk.lastfm.ui.search.adapter

import android.support.v7.widget.RecyclerView
import android.view.View

class ArtistsAdapterObserver(private val listener: OnResultEmptyListener?, private val recyclerView: RecyclerView,
                             private val emptyView: View) :
    RecyclerView.AdapterDataObserver() {

    init {
        checkIfEmpty()
    }

    interface OnResultEmptyListener {
        fun onResultEmpty()
    }

    private fun checkIfEmpty() {

        with(recyclerView) {
            val showEmptyView = adapter?.itemCount == 0
            emptyView.visibility = if (showEmptyView) View.VISIBLE else View.GONE
            recyclerView.visibility = if (showEmptyView) View.GONE else View.VISIBLE

            if (showEmptyView) {
                listener?.onResultEmpty()
            }
        }
    }

    override fun onChanged() {
        checkIfEmpty()
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        checkIfEmpty()
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        checkIfEmpty()
    }

}