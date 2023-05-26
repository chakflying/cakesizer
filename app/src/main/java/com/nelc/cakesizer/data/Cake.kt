package com.nelc.cakesizer.data

data class Cake(
    val id: String,
    val name: String,
    val weight: Int,
    val size: Int,
    val model_path: String,
    val model_size: Long,
    val thumbnail_path: String,
)
