package org.wit.cara.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import org.wit.cara.R
import org.wit.cara.adapters.CaraImageAdapter
import org.wit.cara.adapters.CaraImageListener
import org.wit.cara.databinding.ActivityCaraImageListBinding
import org.wit.cara.main.MainApp
import org.wit.cara.models.CaraImageModel
import org.wit.cara.models.GroupModel
import timber.log.Timber.i

class CaraImageListActivity : AppCompatActivity(), CaraImageListener {

    lateinit var app: MainApp
    private var group: GroupModel? = null
    private lateinit var binding: ActivityCaraImageListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaraImageListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        binding.toolbar.title = resources.getString(R.string.image_list_title);
        setSupportActionBar(binding.toolbar)

        if (intent.hasExtra("group")) {
            group = intent.extras?.getParcelable("group")!!
            group = app.groups.findById(group!!.id)!!
        }

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        if (group != null) {
            binding.recyclerView.adapter = CaraImageAdapter(group!!.caraImages, this)
        } else {
            binding.recyclerView.adapter = CaraImageAdapter(app.caraImages.findAll(), this)
        }

        registerRefreshCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if(group != null) {
            menuInflater.inflate(R.menu.menu_main, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, CreateCaraImageActivity::class.java)
                launcherIntent.putExtra("group", group)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCaraImageClick(caraImage: CaraImageModel) {
        val launcherIntent = Intent(this, CreateCaraImageActivity::class.java)
        launcherIntent.putExtra("placemark_edit", caraImage)
        refreshIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { binding.recyclerView.adapter?.notifyDataSetChanged() }
    }
}