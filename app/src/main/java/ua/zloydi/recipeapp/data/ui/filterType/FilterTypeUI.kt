package ua.zloydi.recipeapp.data.ui.filterType

import java.io.Serializable

abstract class FilterTypeUI : Serializable {
    open val name: String? = null
}

