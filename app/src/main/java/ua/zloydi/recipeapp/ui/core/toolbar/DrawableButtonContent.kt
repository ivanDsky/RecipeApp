package ua.zloydi.recipeapp.ui.core.toolbar

import android.view.View.OnClickListener
import androidx.annotation.DrawableRes

data class DrawableButtonContent(
    @DrawableRes val icon: Int,
    val clickListener: OnClickListener,
)
