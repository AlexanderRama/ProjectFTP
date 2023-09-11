package com.example.projectftp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File

class ResultViewModel : ViewModel() {
        private val mTempFile = MutableLiveData<File>()
        val tempFile: LiveData<File> = mTempFile

        fun setFile(file: File) {
            mTempFile.value = file
        }
}