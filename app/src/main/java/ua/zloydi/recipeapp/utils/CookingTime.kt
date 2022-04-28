package ua.zloydi.recipeapp.utils

import android.widget.TextView
import androidx.core.view.isVisible
import ua.zloydi.recipeapp.App
import ua.zloydi.recipeapp.R

object CookingTime {
    private val resources = App.instance.resources
    fun setTime(tvTime: TextView, time: Float?){
        tvTime.isVisible = time != null && time > 1f
        if (time == null || time < 1f) return
        tvTime.text = if (time > 240f) resources.getString(R.string.time_over_2hr)
            else resources.getString(R.string.time, time)
    }
}