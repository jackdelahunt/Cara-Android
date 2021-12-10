package org.wit.cara.activities

import org.wit.cara.databinding.ActivityMainBinding

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import org.wit.cara.main.MainApp
import timber.log.Timber.i

class MainActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityMainBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAbove.title = title
        setSupportActionBar(binding.toolbarAbove)

        app = application as MainApp
        binding.groupButton.setOnClickListener {
            val launcherIntent = Intent(this, CaraGroupListActivity::class.java)
            refreshIntentLauncher.launch(launcherIntent)
        }

        binding.imagesButton.setOnClickListener {
            i("########### image button ###########")
        }

        registerRefreshCallback()
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
    }
}