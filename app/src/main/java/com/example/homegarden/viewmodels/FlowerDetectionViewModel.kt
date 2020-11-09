package com.example.homegarden.viewmodels

import android.content.Intent
import android.graphics.Bitmap
import android.os.SystemClock
import android.util.Log
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
                flowerDetected.value = results?.get(0)?.title?.toUpperCase()
                val resultString = results?.get(0)?.title?.toUpperCase() + " flower ,Confidence: ${
                        String.format(
                            "%.1f%%",
                            results?.get(0)?.confidence?.times(100.0f)
                        )
                    }, Time : " + lastProcessingTimeMs
                Log.d("Flower Detected", resultString)
            } catch (e: Exception) {
                Log.e("DetectionException", e.toString())
                flowerDetected.value = "Failed to detect. Try again."
            } finally {
                isModelRunning.value = false
            }
        }
    }
}