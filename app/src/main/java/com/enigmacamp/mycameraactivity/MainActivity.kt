package com.enigmacamp.mycameraactivity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var photoImageView: ImageView
    private lateinit var takePictureButton: Button

    lateinit var currentPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        photoImageView = findViewById(R.id.photo_imageView)
        takePictureButton = findViewById(R.id.takePicture_button)
        takePictureButton.setOnClickListener {
            takePictureIntent()
        }
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
                println(photoURI.toString())
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
            val sourceBitmap =
                    ImageDecoder.createSource(contentResolver, Uri.fromFile(File(currentPhotoPath)))
            val imageBitmap = ImageDecoder.decodeBitmap(sourceBitmap)
            photoImageView.setImageBitmap(imageBitmap)
        }

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
        return storageDir.apply {
            currentPhotoPath = absolutePath
        }
    }
}