package me.lamprosgk.lastfm.ui.detail.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.track_item.*
import me.lamprosgk.lastfm.R
import me.lamprosgk.lastfm.model.Track

class TracksAdapter : RecyclerView.Adapter<TracksAdapter.ViewHolder>() {

    private var tracks: List<Track>? = null

    fun setItems(tracks: List<Track>?) {
        this.tracks = tracks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tracks?.get(position), position)
    }

    override fun getItemCount() = tracks?.size ?: 0


    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(track: Track?, position: Int) {
            with(track) {
                trackName.text = itemView.context.getString(R.string.detail_tracks_template, position + 1, this?.name)
            }
        }


    }
}