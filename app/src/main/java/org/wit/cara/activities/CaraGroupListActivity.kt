package org.wit.cara.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.cara.R
import org.wit.cara.adapters.GroupAdapter
import org.wit.cara.adapters.GroupListener
import org.wit.cara.databinding.ActivityGroupListBinding
import org.wit.cara.main.MainApp
import org.wit.cara.models.GroupModel
import timber.log.Timber.i

class CaraGroupListActivity : AppCompatActivity(), GroupListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityGroupListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroupListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = R.string.group_list_title.toString()
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = GroupAdapter(app.groups.findAll(),this)

        registerRefreshCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                i("Open Create group window")
                val launcherIntent = Intent(this, CreateGroupActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onGroupClick(group: GroupModel) {
        val launcherIntent = Intent(this, CreateGroupActivity::class.java)
        launcherIntent.putExtra("placemark_edit", group)
        refreshIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { binding.recyclerView.adapter?.notifyDataSetChanged() }
    }
}