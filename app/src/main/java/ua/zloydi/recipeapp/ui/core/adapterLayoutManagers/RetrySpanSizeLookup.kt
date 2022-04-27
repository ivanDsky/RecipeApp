package ua.zloydi.recipeapp.ui.core.adapterLayoutManagers

import androidx.recyclerview.widget.GridLayoutManager

class RetrySpanSizeLookup(private val spanSize: Int, private val collectionSize: () -> Int?) :
    GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int {
        val isLast = position + 1 == collectionSize()
        return if (isLast) spanSize else 1
    }
}