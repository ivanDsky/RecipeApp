package ua.zloydi.recipeapp.utils

import androidx.core.view.isVisible
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.databinding.FragmentRecyclerViewBinding

sealed class SearchRecycler{
    object Loading : SearchRecycler()
    class Retry(val onClick: () -> Unit) : SearchRecycler()
    object Empty : SearchRecycler()
    object Content : SearchRecycler()
}

fun FragmentRecyclerViewBinding.setState(state: CombinedLoadStates, adapter: PagingDataAdapter<*,*>){
    val searchState = when{
        state.refresh is LoadState.Loading -> SearchRecycler.Loading
        state.refresh is LoadState.NotLoading && state.append.endOfPaginationReached && adapter.itemCount < 1 -> SearchRecycler.Empty
        state.refresh is LoadState.Error && !state.append.endOfPaginationReached -> SearchRecycler.Retry { adapter.retry() }
        else -> SearchRecycler.Content
    }
    setState(searchState)
}

fun FragmentRecyclerViewBinding.setState(state: SearchRecycler){
    rvItems.isVisible = state is SearchRecycler.Content
    actionView.isVisible = state !is SearchRecycler.Content

    btnRetry.isVisible = state is SearchRecycler.Retry
    if(state is SearchRecycler.Retry)
        btnRetry.setOnClickListener { state.onClick() }

    val animation = when(state){
        SearchRecycler.Empty -> R.raw.empty
        SearchRecycler.Loading -> R.raw.loading
        is SearchRecycler.Retry -> R.raw.empty
        else -> null
    }
    if (animation != null) {
        lotti.setAnimation(animation)
        lotti.playAnimation()
    }
    lotti.isVisible = animation != null

    val text = when(state){
        SearchRecycler.Empty -> R.string.empty
        is SearchRecycler.Retry -> R.string.something_went_wrong
        else -> null
    }
    if (text != null) tvText.setText(text)
    tvText.isVisible = text != null

}