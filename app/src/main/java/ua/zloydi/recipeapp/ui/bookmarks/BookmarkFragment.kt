package ua.zloydi.recipeapp.ui.bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import ua.zloydi.recipeapp.data.local.bookmarks.BookmarksProvider
import ua.zloydi.recipeapp.databinding.FragmentRecyclerViewBinding
import ua.zloydi.recipeapp.ui.core.BaseFragment
import ua.zloydi.recipeapp.ui.core.adapter.bookmarkAdapter.BookmarkAdapter
import ua.zloydi.recipeapp.ui.core.adapterDecorators.PaddingDecoratorFactory
import ua.zloydi.recipeapp.ui.data.BookmarkUI
import ua.zloydi.recipeapp.ui.main.MainFragment
import kotlin.properties.Delegates

class BookmarkFragment : BaseFragment<FragmentRecyclerViewBinding>() {
    override fun inflate(inflater: LayoutInflater) = FragmentRecyclerViewBinding.inflate(layoutInflater)

    private val viewModel: BookmarkViewModel by viewModels {
        BookmarkViewModel.Factory(
            BookmarksProvider.database,
            (parentFragment as MainFragment).childNavigation
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindStable()
        lifecycleScope.launchWhenStarted {
            viewModel.bookmarks.collect (::bindBookmarks)
        }
    }

    private var adapter: BookmarkAdapter by Delegates.notNull()

    private fun bindStable() = with(binding){
        adapter = BookmarkAdapter()
        rvItems.adapter = adapter
        rvItems.layoutManager = LinearLayoutManager(requireContext())
        PaddingDecoratorFactory(resources).apply(rvItems, 4f, 8f)
    }

    private fun bindBookmarks(bookmarks: List<BookmarkUI>) {
        adapter.setItems(bookmarks)
    }
}