package ua.zloydi.recipeapp.ui.core.adapterDecorators

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PaddingDecoratorFactory(res: Resources) {
    private val sdp = res.getDimensionPixelOffset(com.intuit.sdp.R.dimen._1sdp)

    fun create(left: Float, top: Float, right: Float, bottom: Float) =
        object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.set(
                    (left * sdp).toInt(),
                    (top * sdp).toInt(),
                    (right * sdp).toInt(),
                    (bottom * sdp).toInt()
                )
            }
        }
}