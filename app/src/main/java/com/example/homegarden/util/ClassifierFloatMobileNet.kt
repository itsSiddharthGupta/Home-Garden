package com.example.homegarden.util

import android.app.Activity
import org.tensorflow.lite.support.common.TensorOperator
import org.tensorflow.lite.support.common.ops.NormalizeOp

/** This TensorFlowLite classifier works with the float MobileNet model.  */
class ClassifierFloatMobileNet(activity: Activity?, device: ImageClassifier.Device?, numThreads: Int) :
    ImageClassifier(activity, device, numThreads) {

    companion object {
        /** Float MobileNet requires additional normalization of the used input.  */
        private const val IMAGE_MEAN = 0f
        private const val IMAGE_STD = 255f

        /**
         * Float model does not need dequantization in the post-processing. Setting mean and std as 0.0f
         * and 1.0f, repectively, to bypass the normalization.
         */
        private const val PROBABILITY_MEAN = 0.0f
        private const val PROBABILITY_STD = 1.0f
    }

    override fun getModelPath() = "model.tflite"

    override fun getLabelPath() = "labels.txt"

    override fun getPreprocessNormalizeOp(): TensorOperator? = NormalizeOp(IMAGE_MEAN, IMAGE_STD)

    override fun getPostprocessNormalizeOp(): TensorOperator? = NormalizeOp(PROBABILITY_MEAN, PROBABILITY_STD)
}