package ua.zloydi.recipeapp.models.dto

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
            val amp = href.indexOf('&', start)
            val per = href.indexOf('%', start)
            var end = href.length
            if(amp != -1)end = min(end, amp)
            if(per != -1)end = min(end, per)
            return href.substring(start + 6, end)
        }
}