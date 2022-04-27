package ua.zloydi.recipeapp.data.local.room

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import ua.zloydi.recipeapp.models.dto.QueryDTO
import ua.zloydi.recipeapp.models.dto.recipes.RecipeDetailDTO

private val gson = GsonBuilder().create()
class QueryDTOConverter {
    @TypeConverter
    fun stringToQueryDTO(input: String): QueryDTO? = gson.fromJson(input, QueryDTO::class.java)

    @TypeConverter
    fun queryDTOToString(input: QueryDTO): String = gson.toJson(input)
}

class RecipeDetailDTOConverter{
    @TypeConverter
    fun stringToRecipeDetailDTO(input: String): RecipeDetailDTO? = gson.fromJson(input, RecipeDetailDTO::class.java)

    @TypeConverter
    fun recipeDetailDTOToString(input: RecipeDetailDTO): String = gson.toJson(input)
}