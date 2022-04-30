package ua.zloydi.recipeapp.ui.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ua.zloydi.recipeapp.data.local.bookmarks.BookmarkDatabase
import ua.zloydi.recipeapp.data.local.cache.CacheDatabase
import ua.zloydi.recipeapp.models.entity.BookmarkEntity
import ua.zloydi.recipeapp.ui.data.BookmarkUI
import ua.zloydi.recipeapp.ui.main.IChildNavigation
import ua.zloydi.recipeapp.ui.mappers.toUI

class BookmarkViewModel(
    private val bookmarkDatabase: BookmarkDatabase,
    private val cacheDatabase: CacheDatabase,
    private val childNavigation: IChildNavigation,
) : ViewModel(){
    val bookmarks:Flow<List<BookmarkUI>> = bookmarkDatabase.bookmark()
        .queryAll()
        .map(::toUI)

    private suspend fun toUI(bookmarks: List<BookmarkEntity>) = withContext(Dispatchers.IO){
        bookmarks.mapNotNull {
            val item = cacheDatabase.recipeItem().query(it.id) ?: return@mapNotNull null
            val recipeItemUI = item.toUI{childNavigation.openDetail(item)}
            BookmarkUI(recipeItemUI){removeBookmark(it.id)}
        }
    }

    private fun removeBookmark(id: String) = viewModelScope.launch(Dispatchers.IO){
        bookmarkDatabase.bookmark().delete(id)
    }

    class Factory(
        private val bookmarkDatabase: BookmarkDatabase,
        private val cacheDatabase: CacheDatabase,
        private val childNavigation: IChildNavigation,
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(BookmarkViewModel::class.java))
                return BookmarkViewModel(bookmarkDatabase, cacheDatabase, childNavigation) as T
            else
                throw TypeCastException()
        }
    }

}