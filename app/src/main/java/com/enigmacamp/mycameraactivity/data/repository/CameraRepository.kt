package com.enigmacamp.mycameraactivity.data.repository

import java.io.File

interface CameraRepository {
    suspend fun upload(customerId: String, imageFile: File): Boolean
    suspend fun upload64(customerId: String, imageFileBase64: String): Boolean
}