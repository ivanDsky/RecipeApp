package ua.zloydi.recipeapp.data.dto

data class QueryDTO(
    val from: Int,
    val to: Int,
    val count: Int,
    val _links: LinksDTO,
    val hits: Array<HitDTO>?= null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QueryDTO

        if (from != other.from) return false
        if (to != other.to) return false
        if (count != other.count) return false
        if (_links != other._links) return false

        return true
    }

    override fun hashCode(): Int {
        var result = from
        result = 31 * result + to
        result = 31 * result + count
        result = 31 * result + _links.hashCode()
        return result
    }
}
