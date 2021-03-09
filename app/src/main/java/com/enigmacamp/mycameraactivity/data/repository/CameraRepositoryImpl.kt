package com.enigmacamp.mycameraactivity.data.repository

import com.enigmacamp.mycameraactivity.data.api.CameraApi
import com.enigmacamp.mycameraactivity.data.model.UpdateProfileRequest
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class CameraRepositoryImpl @Inject constructor(val cameraApi: CameraApi) : CameraRepository {
    override suspend fun upload(customerId: String, imageFile: File): Boolean {
        val mediaType = MediaType.parse("multipart/form-data")
        val requestFile = RequestBody.create(mediaType, imageFile);
        val image =
            MultipartBody.Part.createFormData("PhotoProfile", imageFile.getName(), requestFile);

        val customer = RequestBody.create(mediaType, customerId);

        val result = cameraApi.updateProfile(customer, image)
        return result.isSuccessful
    }

    override suspend fun upload64(customerId: String, imageFileBase64: String): Boolean {
        val result = cameraApi.updateProfile64(UpdateProfileRequest(customerId, imageFileBase64))
        return result.isSuccessful
    }

}