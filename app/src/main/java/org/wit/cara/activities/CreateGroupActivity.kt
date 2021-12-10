package org.wit.cara.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import com.google.android.material.snackbar.Snackbar
import org.wit.cara.R
import org.wit.cara.databinding.ActivityCreateGroupBinding
import org.wit.cara.main.MainApp
import org.wit.cara.models.CaraImageModel
import org.wit.cara.models.GroupModel

class CreateGroupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateGroupBinding
        private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>

    var group = GroupModel()
    var edit = false
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityCreateGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        binding.toolbarAdd.title =  resources.getString(R.string.group_activity_title);

        setSupportActionBar(binding.toolbarAdd)

        if (intent.hasExtra("placemark_edit")) {
            edit = true
            group = intent.extras?.getParcelable("placemark_edit")!!
            binding.groupName.setText(group.name)
        }

        binding.btnAdd.setOnClickListener() {
            group.name = binding.groupName.text.toString()
            if (group.name.isEmpty()) {
                Snackbar.make(it,R.string.hint_cara_image_title   , Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.groups.update(group.copy())
                } else {
                    app.groups.create(group.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_placemark, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setResult(RESULT_CANCELED)
        finish()
        return super.onOptionsItemSelected(item)
    }
}