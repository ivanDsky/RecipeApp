package ua.zloydi.recipeapp.ui.core.adapterLayoutManagers

import androidx.recyclerview.widget.GridLayoutManager

class RetrySpanSizeLookup(private val spanSize: Int, private val isAdditionalItem: (Int) -> Boolean) :
    GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int) = if (isAdditionalItem(position)) spanSize else 1
}