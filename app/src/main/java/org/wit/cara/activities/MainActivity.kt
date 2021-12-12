package org.wit.cara.activities

import org.wit.cara.databinding.ActivityMainBinding

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import org.wit.cara.main.MainApp
import timber.log.Timber.i

class MainActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityMainBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAbove.title = title
        setSupportActionBar(binding.toolbarAbove)

        app = application as MainApp
        binding.groupButton.setOnClickListener {
            if (!app.userStore.signedIn) return@setOnClickListener;

            val launcherIntent = Intent(this, CaraGroupListActivity::class.java)
            refreshIntentLauncher.launch(launcherIntent)
        }

        binding.imagesButton.setOnClickListener {
            if (!app.userStore.signedIn) return@setOnClickListener;
            val launcherIntent = Intent(this, CaraImageListActivity::class.java)
            refreshIntentLauncher.launch(launcherIntent)
        }

        binding.signInButton.setOnClickListener {
            if (app.userStore.signedIn) return@setOnClickListener;
            val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())

            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
            signInLauncher.launch(signInIntent)
        }

        registerRefreshCallback()
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            val user = FirebaseAuth.getInstance().currentUser
            if(user != null)
                app.userStore.signIn(user);
        }
    }
}