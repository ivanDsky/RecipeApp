package ua.zloydi.recipeapp.data.local.cache

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import ua.zloydi.recipeapp.models.dto.QueryDTO
import ua.zloydi.recipeapp.models.dto.recipes.RecipeDetailDTO

private val gson = GsonBuilder().create()
abstract class CacheConverter<T>(private val clazz: Class<T>){
    @TypeConverter
    fun stringToType(input: String): T? = gson.fromJson(input, clazz)

    @TypeConverter
    fun typeToString(input: T): String = gson.toJson(input)
}

class QueryDTOConverter : CacheConverter<QueryDTO>(QueryDTO::class.java)
class RecipeDetailDTOConverter : CacheConverter<RecipeDetailDTO>(RecipeDetailDTO::class.java)
