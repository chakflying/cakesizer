package com.nelc.cakesizer.welcomeactivity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nelc.cakesizer.data.Cake
import com.nelc.cakesizer.data.CakesService
import com.nelc.cakesizer.provider.ToastProvider
import com.nelc.cakesizer.ui.NavEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val cakesService: CakesService,
    private val toastProvider: ToastProvider,
) : ViewModel() {

    var cakes by mutableStateOf<List<Cake>>(emptyList(), neverEqualPolicy())
    var filteredCakes by mutableStateOf<List<Cake>>(emptyList(), neverEqualPolicy())

    var downloadProgressState = mutableStateOf<Long>(0)
    var downloading by mutableStateOf(false)

    var sizeList by mutableStateOf<List<Int>>(emptyList(), neverEqualPolicy())

    var selectedSize by mutableStateOf<Int?>(null)
    var selectedCake by mutableStateOf<Cake?>(null)

    var navEvents: NavEvents? = null

    init {
        Timber.i("WelcomeViewModel created")

        fetchCakesList()
    }

    private fun fetchCakesList() {
        viewModelScope.launch {
            cakes = cakesService.getCakes().getOrDefault(emptyList())
            filteredCakes = cakes
            sizeList = cakes.map { it.size }.toSet().toList().sorted()
        }
    }

    fun setSelectedSize(size: Int) {
        selectedSize = if (selectedSize == size) {
            null
        } else {
            size
        }
        updateCakesFilter()
    }

    private fun updateCakesFilter() {
        selectedCake = null
        filteredCakes = if (selectedSize != null) {
            cakes.filter { it.size == selectedSize }
        } else {
            cakes
        }
    }

    fun selectCake(cake: Cake) {
        selectedCake = if (selectedCake == cake) {
            null
        } else {
            cake
        }
    }

    fun start() {
        if (selectedCake == null) return
        downloading = true

        viewModelScope.launch {
            Timber.i(selectedCake!!.id)
            Timber.i(selectedCake!!.model_path)
            val getModelResult = cakesService.getCakeModel(
                selectedCake!!.id,
                selectedCake!!.model_path,
                downloadProgressState
            )

            downloading = false

            if (getModelResult.isSuccess) {
                navEvents?.startArEvents?.tryEmit(getModelResult.getOrThrow())
            } else {
                toastProvider.showToast(
                    getModelResult.exceptionOrNull()?.message
                        ?: "Failed to download model: Unknown error"
                )
            }
        }
    }
}
