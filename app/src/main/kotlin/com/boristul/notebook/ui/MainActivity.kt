package com.boristul.notebook.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.NavigationUI
import com.boristul.notebook.databinding.ActivityMainBinding
import com.boristul.utils.findNavControllerInOnCreate
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    lateinit var driveService: Drive

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavControllerInOnCreate(binding.navHostFragment.id).apply {
            addOnDestinationChangedListener { _, destination, _ -> title = destination.label }
        }
        NavigationUI.setupWithNavController(binding.navigation, navController)
        requestSignIn()

        //val googleAccount = checkNotNull(GoogleSignIn.getLastSignedInAccount(this))

        //val credential: GoogleAccountCredential = GoogleAccountCredential.usingOAuth2(
        //    this, setOf(DriveScopes.DRIVE_APPDATA)
        //)
        //
        //credential.selectedAccount = googleAccount.account
        //val driveService1 = Drive.Builder(
        //    AndroidHttp.newCompatibleTransport(),
        //    GsonFactory(),
        //    credential
        //)
        //    .setApplicationName("Notebook")
        //    .build()
        //
        //driveService = driveService1
        //
        //GlobalScope.launch(Dispatchers.IO) {
        //    val files = driveService.files().list()
        //        .setSpaces("appDataFolder")
        //        .execute()
        //
        //    files.files.forEach {
        //        Log.d("HelloWorld", "Files:${it.name}")
        //
        //
        //    }
        //}

    }

    fun requestSignIn() {
        val signInOptions: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestScopes(Scope(DriveScopes.DRIVE_APPDATA))
            .build()
        val client: GoogleSignInClient = GoogleSignIn.getClient(this, signInOptions)

        // The result of the sign-in Intent is handled in onActivityResult.
        startActivityForResult(client.signInIntent, 1)
    }

    private fun handleSignInResult(result: Intent) {
        GoogleSignIn.getSignedInAccountFromIntent(result)
            .addOnSuccessListener { googleAccount: GoogleSignInAccount ->
                Log.d("HelloWorld", "Signed in as " + googleAccount.email)

                // Use the authenticated account to sign in to the Drive service.
                val credential: GoogleAccountCredential = GoogleAccountCredential.usingOAuth2(
                    this, setOf(DriveScopes.DRIVE_APPDATA)
                )

                credential.selectedAccount = googleAccount.account
                val driveService1 = Drive.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    GsonFactory(),
                    credential
                )
                    .setApplicationName("Notebook")
                    .build()

                driveService = driveService1

                GlobalScope.launch(Dispatchers.IO) {
                    val files = driveService.files().list()
                        .setSpaces("appDataFolder")
                        .execute()

                    files.files.forEach {
                        Log.d("HelloWorld", "Files:${it.name}")
                    }
                }

                //val fileMetadata = File()
                //fileMetadata.setName("config.json")
                //fileMetadata.setParents(Collections.singletonList("appDataFolder"))
                //
                //GlobalScope.launch(Dispatchers.IO) {
                //    val filePath = java.io.File(getExternalFilesDir(null)?.absolutePath + "/" + java.io.File.separator + "config.json")
                //    filePath.createNewFile()
                //    val data1 = "byteArrayOf(1, 1, 0, 0)".toByteArray()
                //    if (filePath.exists()) {
                //        val fo: OutputStream = FileOutputStream(filePath)
                //        fo.write(data1)
                //        fo.close()
                //    }
                //
                //    val mediaContent = FileContent("application/json", filePath)
                //    val file = driveService.files().create(fileMetadata, mediaContent)
                //        .setFields("id")
                //        .execute()
                //    Log.d("Hello world", file.id)
                //}
            }
            .addOnFailureListener { exception: Exception? ->
                Log.e(
                    "HelloWorld",
                    "Unable to sign in.",
                    exception
                )
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && data != null) {
            handleSignInResult(data)
            Log.d("HelloWorld", "fdsfsfsd")
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}
