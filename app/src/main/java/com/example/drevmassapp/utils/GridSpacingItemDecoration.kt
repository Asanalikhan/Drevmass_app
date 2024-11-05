package com.example.drevmassapp.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // get item position
        val column = position % spanCount // item column

        outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
        outRect.right = spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f / spanCount) * spacing)
        if (position >= spanCount) {
            outRect.top = spacing // item top
        }

        if (includeEdge) {
            outRect.right = (column + 1) * spacing / spanCount
        }
    }
}
