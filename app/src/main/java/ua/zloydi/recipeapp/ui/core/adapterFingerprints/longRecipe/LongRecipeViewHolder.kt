package ua.zloydi.recipeapp.ui.core.adapterFingerprints.longRecipe

import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.databinding.LayoutLongRecipeItemBinding
import ua.zloydi.recipeapp.ui.core.adapter.labelAdapter.LabelAdapter
import ua.zloydi.recipeapp.ui.core.adapter.recipeAdapter.RecipeViewHolder
import ua.zloydi.recipeapp.ui.core.adapterFingerprints.label.CuisineFingerprint
import ua.zloydi.recipeapp.ui.core.adapterFingerprints.label.DishFingerprint
import ua.zloydi.recipeapp.ui.core.adapterFingerprints.label.MealFingerprint
import ua.zloydi.recipeapp.ui.data.RecipeItemUI
import ua.zloydi.recipeapp.ui.data.filterType.DishUI
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class LongRecipeViewHolder(binding: LayoutLongRecipeItemBinding) :
    RecipeViewHolder<LayoutLongRecipeItemBinding, RecipeItemUI>(binding) {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun bind(item: RecipeItemUI) = with(binding){
        ivRecipePreview.transitionName = "ivRecipePreview$adapterPosition"
        tvTitle.text = item.title
        if(item.time == null || item.time < 1 || item.time > 240) {
            tvTime.isVisible = false
        }else{
            tvTime.isVisible = true
            tvTime.text = root.resources.getString(R.string.time, item.time)
        }

        root.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val name = item.types.filterIsInstance<DishUI>().first().name + ".webp"
                saveImage(name,
                    Glide.with(ivRecipePreview)
                        .asBitmap()
                        .load(item.image)
                        .submit()
                        .get()
                )
            }
            item.onClick()
        }

        Glide.with(ivRecipePreview)
            .load(item.image)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(ivRecipePreview)

        rvLabels.adapter = LabelAdapter(listOf(CuisineFingerprint, DishFingerprint, MealFingerprint))
            .also { it.setItems(item.types)}
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun saveImage(imageFileName: String, image: Bitmap): String? {
        var savedImagePath: String? = null
        val storageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString() + "/Temp"
        )
        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }
        if (success) {
            val imageFile = File(storageDir, imageFileName)
            savedImagePath = imageFile.getAbsolutePath()
            try {
                val fOut: OutputStream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.WEBP_LOSSLESS, 100, fOut)
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }


        }
        return savedImagePath
    }
}