package com.nelc.cakesizer.data

import androidx.compose.runtime.MutableState

interface CakesService {
    suspend fun getCakes(): Result<List<Cake>>
    suspend fun getCakeModel(
        modelId: String,
        modelPath: String,
        progress: MutableState<Long>
    ): Result<String>
}