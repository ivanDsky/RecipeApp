package ua.zloydi.recipeapp.ui.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ua.zloydi.recipeapp.data.local.bookmarks.BookmarkDatabase
import ua.zloydi.recipeapp.models.entity.BookmarkEntity
import ua.zloydi.recipeapp.ui.data.RecipeItemUI
import ua.zloydi.recipeapp.ui.main.IChildNavigation
import ua.zloydi.recipeapp.ui.mappers.toUI

class BookmarkViewModel(
    private val database: BookmarkDatabase,
    private val childNavigation: IChildNavigation,
) : ViewModel(){
    val bookmarks:Flow<List<RecipeItemUI>> = database.bookmark()
        .queryAll()
        .map(::toUI)

    private suspend fun toUI(bookmarks: List<BookmarkEntity>) = withContext(Dispatchers.IO){
        bookmarks.mapNotNull {
            val item = database.recipeItem().query(it.id) ?: return@mapNotNull null
            item.toUI{childNavigation.openDetail(item)}
        }
    }

    class Factory(
        private val database: BookmarkDatabase,
        private val childNavigation: IChildNavigation,
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(BookmarkViewModel::class.java))
                return BookmarkViewModel(database, childNavigation) as T
            else
                throw TypeCastException()
        }
    }

}