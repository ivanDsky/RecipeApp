package ua.zloydi.recipeapp.ui.core.adapterDecorators

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private class SdpDelegate(private val sdp: Int) : ReadWriteProperty<Any?, Int> {
    private var value = 0
    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        this.value = value * sdp
    }
}

class SpaceDecorator(res: Resources) {
    companion object{
        inline fun builder(res: Resources, block: SpaceDecorator.() -> Unit) = SpaceDecorator(res).apply(block).build()
    }

    private val sdp = res.getDimensionPixelOffset(com.intuit.sdp.R.dimen._1sdp)
    var verticalSpace by SdpDelegate(sdp)
    var horizontalSpace by SdpDelegate(sdp)
    var marginStart by SdpDelegate(sdp)
    var marginEnd by SdpDelegate(sdp)
    var marginTop by SdpDelegate(sdp)
    var marginBottom by SdpDelegate(sdp)

    fun build() = object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            when (parent.getChildAdapterPosition(view)) {
                RecyclerView.NO_POSITION -> return
                0 -> outRect.set(marginStart, marginTop, horizontalSpace, verticalSpace)
                state.itemCount - 1 -> outRect.set(0, 0, marginEnd, marginBottom)
                else -> outRect.set(0, 0, horizontalSpace, verticalSpace)
            }
        }
    }
}