package com.example.cocktaildb.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import com.example.cocktaildb.R
import java.io.InputStream
import java.net.URL

class ImageLoader {

    companion object {
        private const val CONNECTION_TIMEOUT = 10000
        private const val READ_TIMEOUT = 10000

        fun loadImage(imageView: ImageView, imageUrl: String) {
            LoadImageTask(imageView).execute(imageUrl)
        }
    }

    private class LoadImageTask(private val imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {

        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageUrl = urls[0]
            return try {
                val url = URL(imageUrl)
                val connection = url.openConnection()
                connection.connectTimeout = CONNECTION_TIMEOUT
                connection.readTimeout = READ_TIMEOUT
                val inputStream: InputStream = connection.getInputStream()
                BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        override fun onPostExecute(result: Bitmap?) {
            if (result != null) {
                imageView.setImageBitmap(result)
            } else {
                // Set placeholder if image loading fails
                imageView.setImageResource(R.drawable.cocktail_placeholder)
            }
        }
    }
}
