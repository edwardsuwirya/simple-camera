package com.enigmacamp.mycameraactivity.presentation

import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enigmacamp.mycameraactivity.data.repository.CameraRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(val cameraRepository: CameraRepository) :
    ViewModel() {

    fun upload(photo: File) {
        viewModelScope.launch {
//            cameraRepository.upload("123", photo)
            val file64 = Base64.encodeToString(photo.readBytes(), Base64.NO_WRAP)
            cameraRepository.upload64("234", file64)
        }
    }
}