package ua.zloydi.recipeapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import ua.zloydi.recipeapp.R
import ua.zloydi.recipeapp.data.ErrorProvider
import ua.zloydi.recipeapp.ui.main.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.commit { replace(R.id.container, MainFragment()) }
        lifecycleScope.launchWhenStarted {
            ErrorProvider.service.getErrors().receiveAsFlow().collect {
                Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT)
                    .show()
            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {

                    var intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                        Uri.parse("package:" + packageName))
                    startActivityForResult(intent, 0)
            }
        }

    }
}