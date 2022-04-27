package ua.zloydi.recipeapp.data.local.bookmarks

import ua.zloydi.recipeapp.data.local.cache.CacheConverter
import ua.zloydi.recipeapp.models.dto.recipes.RecipeItemDTO

class RecipeItemDTOConverter : CacheConverter<RecipeItemDTO>(RecipeItemDTO::class.java)