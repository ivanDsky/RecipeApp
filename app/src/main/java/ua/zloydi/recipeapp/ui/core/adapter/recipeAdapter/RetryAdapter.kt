package ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.databinding.LayoutRetryBinding

class RetryAdapter(
    private val adapter: RecipePagerAdapter
) : LoadStateAdapter<RetryAdapter.RetryItemViewHolder>() {

    override fun getStateViewType(loadState: LoadState) = R.layout.layout_retry

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        RetryItemViewHolder(
            LayoutRetryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ) { adapter.retry() }

    override fun onBindViewHolder(holder: RetryItemViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    class RetryItemViewHolder(
        private val binding: LayoutRetryBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retryCallback() }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                progressBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState is LoadState.Error
            }
        }
    }
}