package com.enigmacamp.mycameraactivity.presentation

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
    lateinit var currentPhotoPath: String

    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.apply {
            takePictureButton.setOnClickListener {
                takePictureIntent()
            }
            uploadButton.setOnClickListener {
                viewModel.upload(File(currentPhotoPath))
            }
        }
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }


    private fun takePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.resolveActivity(packageManager)?.also {
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                null
            }
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    this,
                    "com.enigmacamp.mycameraactivity.fileprovider",
                    it
                )
//                Log.d("CameraActivity", "Photo Uri: $photoURI")
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    /*
        Gambar yang didapat dari "data" hanya cukup sebagai thumbnail/icon.
        Untuk full size image nya membutuhkan kode lain.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            val imageBitmap = data?.extras?.get("data") as Bitmap
            showPhoto()
        }
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