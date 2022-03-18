package ua.zloydi.recipeapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.data.error.ErrorProvider

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launchWhenStarted {
            ErrorProvider.service.getErrors().receiveAsFlow().collect {
                Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}