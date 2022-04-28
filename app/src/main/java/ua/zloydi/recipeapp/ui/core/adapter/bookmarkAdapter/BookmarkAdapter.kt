package ua.zloydi.recipeapp.ui.core.adapter.bookmarkAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import ua.zloydi.recipeapp.databinding.LayoutBookmarkItemBinding
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseDifferNotifyAdapter
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseFingerprint
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseStaticFingerprint
import ua.zloydi.recipeapp.ui.core.adapter.baseAdapter.BaseViewHolder
import ua.zloydi.recipeapp.ui.data.BookmarkUI
import ua.zloydi.recipeapp.utils.CookingTime

class BookmarkAdapter : BaseDifferNotifyAdapter<BookmarkUI>(
    listOf(BookmarkFingerprint) as List<BaseFingerprint<*, BookmarkUI>>,
) {
    override val diff: DiffUtil.ItemCallback<BookmarkUI> = RecipeDiff()

    private class RecipeDiff : DiffUtil.ItemCallback<BookmarkUI>() {
        override fun areItemsTheSame(oldItem: BookmarkUI, newItem: BookmarkUI) =
            oldItem.recipeItemUI.image == newItem.recipeItemUI.image

        override fun areContentsTheSame(oldItem: BookmarkUI, newItem: BookmarkUI) =
            oldItem == newItem
    }
}

object BookmarkFingerprint : BaseStaticFingerprint<LayoutBookmarkItemBinding, BookmarkUI>() {
    override fun inflate(inflater: LayoutInflater, parent: ViewGroup) =
        BookmarkViewHolder(LayoutBookmarkItemBinding.inflate(inflater, parent, false))
}

class BookmarkViewHolder(binding: LayoutBookmarkItemBinding) :
    BaseViewHolder<LayoutBookmarkItemBinding, BookmarkUI>(binding) {

    override fun bind(item: BookmarkUI): Unit = with(binding){
        val recipeItem = item.recipeItemUI
        tvTitle.text = recipeItem.title
        CookingTime.setTime(tvTime, recipeItem.time)

        root.setOnClickListener { recipeItem.onClick() }

        btnBookmark.setOnClickListener { item.onBookmarkClick() }

        Glide.with(ivRecipePreview)
            .load(recipeItem.image)
            .into(ivRecipePreview)
    }
}