package me.lamprosgk.lastfm.util

import android.content.Context
import android.content.Intent
import android.widget.Toast
import me.lamprosgk.lastfm.R

fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.share(text: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text, text))
        type = "text/plain"
    }
    startActivity(sendIntent)
}

