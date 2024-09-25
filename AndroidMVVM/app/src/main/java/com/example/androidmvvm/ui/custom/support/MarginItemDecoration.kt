package com.example.androidmvvm.ui.custom.support

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(
    private val spaceSize: Int,
    private val leftMargin: Int = spaceSize,
    private val topMargin: Int = spaceSize,
    private val rightMargin: Int = spaceSize,
    private val bottomMargin: Int = spaceSize,
    private val spanCount: Int = 1,
    private val orientation: Int = GridLayoutManager.VERTICAL
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            val position = parent.getChildAdapterPosition(view)

            if(orientation == GridLayoutManager.VERTICAL) {
                if(position < spanCount) {
                    top = topMargin
                }
                if(position % spanCount == 0) {
                    left = leftMargin
                }
            }
            else {
                if(position < spanCount) {
                    left = leftMargin
                }
                if(position % spanCount == 0) {
                    top = topMargin
                }
            }

            right = rightMargin
            bottom = bottomMargin
        }
    }
}