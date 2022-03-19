package ua.zloydi.recipeapp.data.dto

import kotlin.math.min

data class LinkDTO(
    val href: String? = null,
    val title: String? = null
){
    val hash: String?
        get(){
            if(href == null) return null
            val start = href.indexOf("_cont=")
            if (start == -1) return null
            var end = min(href.indexOf('&', start), href.indexOf('%',start))
            if (end == -1) end = href.length
            return href.substring(start + 6, end)
        }
}