package org.wit.cara.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.cara.R
import org.wit.cara.databinding.ActivityPlacemarkBinding
import org.wit.cara.helpers.showImagePicker
import org.wit.cara.main.MainApp
import org.wit.cara.models.CaraImageModel
import timber.log.Timber.i

class CaraImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlacemarkBinding
        private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>

    var caraImage = CaraImageModel()
    var edit = false
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityPlacemarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        setSupportActionBar(binding.toolbarAdd)

        if (intent.hasExtra("placemark_edit")) {
            edit = true
            caraImage = intent.extras?.getParcelable("placemark_edit")!!
            binding.caraImageTitle.setText(caraImage.title)

            binding.btnAdd.text = getString(R.string.button_edit_cara_image);

            if(caraImage.image != Uri.EMPTY)
                binding.chooseImage.text = getString(R.string.button_edit_image);

            Picasso.get()
                .load(caraImage.image)
                .into(binding.image)
        }

        binding.btnAdd.setOnClickListener() {
            caraImage.title = binding.caraImageTitle.text.toString()
            if (caraImage.title.isEmpty()) {
                Snackbar.make(it,R.string.hint_cara_image_title   , Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.placemarks.update(caraImage.copy())
                } else {
                    app.placemarks.create(caraImage.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        registerImagePickerCallback();
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

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            caraImage.image = result.data!!.data!!
                            Picasso.get()
                                .load(caraImage.image)
                                .into(binding.image)
                            binding.chooseImage.setText(R.string.button_edit_image)
                        }
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}