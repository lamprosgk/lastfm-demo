package me.lamprosgk.lastfm.ui.search.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.artist_item.*
import me.lamprosgk.lastfm.R
import me.lamprosgk.lastfm.model.Artist
import me.lamprosgk.lastfm.util.getImageUrl

class ArtistsAdapter(private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<ArtistsAdapter.ViewHolder>() {

    interface ItemClickListener {
        fun onItemClick(artist: Artist, artistIcon: ImageView)
    }

    private var artists: List<Artist>? = null

    fun setItems(artists: List<Artist>?) {
        this.artists = artists
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.artist_item, parent, false)
        return ViewHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        artists?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount() = artists?.size ?: 0

    /**
     * Using experimental LayoutContainer
     * https://kotlinlang.org/docs/tutorials/android-plugin.html#layoutcontainer-support
     */
    class ViewHolder(override val containerView: View, private val itemClickListener: ItemClickListener) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(artist: Artist) {
            with(artist) {
                artistName.text = artist.name
                artistImage.transitionName = id

                val url = getImageUrl("medium")
                if (url.isNotBlank()) {
                    Picasso.get()
                        .load(url)
                        .transform(CropCircleTransformation())
                        .noFade()
                        .into(artistImage)
                }

                itemView.setOnClickListener {
                    itemClickListener.onItemClick(this, artistImage)
                }
            }
        }
    }
}