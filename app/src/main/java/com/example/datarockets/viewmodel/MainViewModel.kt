package com.example.datarockets.viewmodel

import android.graphics.pdf.PdfDocument
import android.media.MediaCodecInfo
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.datarockets.model.BeersList
import com.example.datarockets.repository.Repository
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {

    lateinit var recyclerListData: MutableLiveData<BeersList>

    init {
        recyclerListData = MutableLiveData()

    }

    fun getBeersListObservable(): MutableLiveData<BeersList> {
        return recyclerListData
    }

    private val repository: Repository = Repository()

    private val errorMessage = MutableLiveData<String>()
    var job: Job? = null

    private val loading = MutableLiveData<Boolean>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getBeersList() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getBeersList()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    recyclerListData.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    fun getRefreshBeersList(page: Int, perPage: Int) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getRefreshBeerList(page, perPage)
            withContext(Dispatchers.Main + exceptionHandler) {
                if (response.isSuccessful) {
                        recyclerListData.postValue(response.body())
                        loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        Log.d("TAG", "onError message $message")
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}

