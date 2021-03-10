package com.enigmacamp.mycameraactivity.presentation

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.enigmacamp.mycameraactivity.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var binding: ActivityMainBinding
    private var currentPhotoPath: String? = null
    lateinit var viewModel: MainActivityViewModel
    private lateinit var photoFile: File

    val writeStoragePermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                photoFile = createImageFile()
                takePictureIntent()
            }
        }
    val askCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                writeStoragePermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            } else {
                Log.d("CameraActivity", "permission denied")
            }
        }

    val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            showPhoto()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.apply {
            takePictureButton.setOnClickListener {
                askCameraPermission.launch(Manifest.permission.CAMERA)
            }
            uploadButton.setOnClickListener {
                currentPhotoPath?.let {
                    viewModel.upload(File(currentPhotoPath))
                }
            }
        }
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    private fun takePictureIntent() {
        val photoURI: Uri = FileProvider.getUriForFile(
            this,
            "com.enigmacamp.mycameraactivity.fileprovider",
            photoFile
        )
//                Log.d("CameraActivity", "Photo Uri: $photoURI")
        takePicture.launch(photoURI)
    }

    private fun showPhoto() {
//        Log.d("CameraActivity", "Photo Path: $currentPhotoPath")
        val imageBitmap = BitmapFactory.decodeFile(currentPhotoPath)
        binding.photoImageView.setImageBitmap(imageBitmap)
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//        val storageDir: File? = getExternalFilesDir(DIRECTORY_PICTURES)
        val imageDir = filesDir
        val storageDir = File(imageDir, "photo_${timeStamp}.jpg")

//        return File.createTempFile(
//                "photo_${timeStamp}_",
//                ".jpg",
//                storageDir
//        ).apply {
//            currentPhotoPath = absolutePath
//        }
//        Log.d("CameraActivity", "StorageDir: $storageDir")
        return storageDir.apply {
            currentPhotoPath = absolutePath
        }
    }
}