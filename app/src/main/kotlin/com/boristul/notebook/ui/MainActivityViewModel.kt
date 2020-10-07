package com.boristul.notebook.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.boristul.notebook.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.FileContent
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.Collections

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val APP_FOLDER = "appDataFolder"
        private const val FILE_NAME = "test.txt"
    }

    private val resultMessageLiveDataPrivate = MutableLiveData("")
    val resultMessageLiveData: LiveData<String> get() = resultMessageLiveDataPrivate

    val signInOptions: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestScopes(Scope(DriveScopes.DRIVE_APPDATA))
        .build()

    val authorizedAccount = MutableLiveData<GoogleSignInAccount?>(null)
    fun requestGoogleAccount(context: Context) = GoogleSignIn.getLastSignedInAccount(context)

    /**
     * Test method of using google api for drive
     * In the future in app you will be able to back up notes to google drive
     */
    suspend fun testGoogleDrive(context: Context, account: GoogleSignInAccount) {
        val credential = GoogleAccountCredential.usingOAuth2(
            context,
            setOf(DriveScopes.DRIVE_APPDATA)
        ).also { it.selectedAccount = account.account }

        val driveService = Drive.Builder(
            AndroidHttp.newCompatibleTransport(),
            GsonFactory(),
            credential
        )
            .setApplicationName(context.getString(R.string.app_name))
            .build()

        getFiles(driveService).also { files ->
            if (files.isEmpty()) {
                sendFile(driveService, context).run { resultMessageLiveDataPrivate.postValue(id) }
            } else {
                resultMessageLiveDataPrivate.postValue(files.joinToString { it.name })
            }
        }
    }

    private suspend fun getFiles(driveService: Drive) = withContext(Dispatchers.IO) {
        driveService.files().list().setSpaces(APP_FOLDER).execute().files
    }

    private suspend fun sendFile(driveService: Drive, context: Context) = withContext(Dispatchers.IO) {
        val fileMetadata = File().apply {
            name = FILE_NAME
            parents = Collections.singletonList(APP_FOLDER)
        }

        val filePath =
            java.io.File(context.getExternalFilesDir(null)?.absolutePath + "/" + java.io.File.separator + fileMetadata.name)

        if (!filePath.exists()) {
            filePath.createNewFile()
            val fo: OutputStream = FileOutputStream(filePath)
            fo.write("Hello World".toByteArray())
            fo.close()
        }

        driveService.files().create(fileMetadata, FileContent("application/json", filePath))
            .execute()
    }
}
