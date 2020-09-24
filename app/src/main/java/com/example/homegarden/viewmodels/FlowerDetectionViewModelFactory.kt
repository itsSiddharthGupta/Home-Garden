package com.example.homegarden.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.homegarden.util.ImageClassifier

class FlowerDetectionViewModelFactory(private val classifier: ImageClassifier): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ImageClassifier::class.java).newInstance(classifier)
    }
}