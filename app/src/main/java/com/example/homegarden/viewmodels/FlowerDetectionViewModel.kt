package com.example.homegarden.viewmodels

import android.graphics.Bitmap
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homegarden.util.ImageClassifier

class FlowerDetectionViewModel(private val classifier: ImageClassifier) : ViewModel() {
    val imageSelected: MutableLiveData<Bitmap> = MutableLiveData()
    val flowerDetected: MutableLiveData<String> = MutableLiveData()
    val isModelRunning: MutableLiveData<Boolean> = MutableLiveData()

    fun detectFlower() {
        if (imageSelected.value != null) {
            Log.e("Bitmap", imageSelected.value.toString())
            try {
                isModelRunning.value = true
                val startTime = SystemClock.uptimeMillis()
                val results: List<ImageClassifier.Recognition?>? =
                    classifier.recognizeImage(imageSelected.value!!, 0)
                val lastProcessingTimeMs = SystemClock.uptimeMillis() - startTime
                flowerDetected.value = results.toString() + " , Time : " + lastProcessingTimeMs
            } catch (e: Exception) {
                Log.e("DetectionException", e.toString())
                flowerDetected.value = "Failed to detect. Try again."
            } finally {
                isModelRunning.value = false
            }
        }
    }
}