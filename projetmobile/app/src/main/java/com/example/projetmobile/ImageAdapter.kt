package com.example.projetmobile

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class ImageAdapter(private val context: Context) : BaseAdapter() {

    private val images = mutableListOf<Int>()
    private val revealedPositions = mutableListOf<Int>()
    private val matchedPositions = mutableListOf<Int>()

    init {
        for (i in 0 until GRID_SIZE / 8) {
            images.add(R.drawable.image1)
            images.add(R.drawable.image2)
            images.add(R.drawable.ensa)
            images.add(R.drawable.fleura)
            images.add(R.drawable.cat1)
            images.add(R.drawable.cat2)
            images.add(R.drawable.sky1)
            images.add(R.drawable.sky2)
        }

        images.shuffle()
    }

    override fun getCount(): Int {
        return GRID_SIZE
    }

    override fun getItem(position: Int): Int {
        return images[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView: ImageView

        if (convertView == null) {
            imageView = ImageView(context)
            imageView.layoutParams = ViewGroup.LayoutParams(150, 150) // Adjust the size as needed
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setPadding(8, 8, 8, 8)
        } else {
            imageView = convertView as ImageView
        }

        imageView.setImageResource(if (revealedPositions.contains(position)) getItem(position) else R.drawable.hidden_image)

        return imageView
    }

    fun showImage(position: Int) {
        revealedPositions.add(position)
        notifyDataSetChanged()
    }

    fun hideImage(position: Int) {
        revealedPositions.remove(position)
        notifyDataSetChanged()
    }

    fun setMatched(position: Int) {
        matchedPositions.add(position)
        notifyDataSetChanged()
    }

    fun isGameOver(): Boolean {
        return matchedPositions.size == GRID_SIZE
    }
    fun isMatched(position: Int): Boolean {
        return matchedPositions.contains(position)
    }

    fun resetGame() {
        revealedPositions.clear()
        matchedPositions.clear()
        images.shuffle()
        notifyDataSetChanged()
    }

    fun showAllImages() {
        // Show all images in the grid
        revealedPositions.clear()
        for (position in 0 until GRID_SIZE) {
            revealedPositions.add(position)
        }
        notifyDataSetChanged()
    }

    fun hideAllImages() {
        // Hide all images in the grid
        revealedPositions.clear()
        notifyDataSetChanged()
    }

    companion object {
        private const val GRID_SIZE = 16
    }
}
