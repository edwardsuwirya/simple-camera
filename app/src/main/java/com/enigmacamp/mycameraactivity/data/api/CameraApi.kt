package com.enigmacamp.mycameraactivity.data.api

import com.enigmacamp.mycameraactivity.data.model.UpdateProfileRequest
import com.enigmacamp.mycameraactivity.data.model.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CameraApi {
    @Multipart
    @POST("/upload")
    suspend fun updateProfile(
        @Part("customer_id") id: RequestBody,
        @Part idCard: MultipartBody.Part
    ): Response<UploadResponse>

    @POST("/upload64")
    suspend fun updateProfile64(
        @Body updateRequest: UpdateProfileRequest
    ): Response<UploadResponse>
}