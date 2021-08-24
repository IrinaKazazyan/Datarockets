package com.example.datarockets.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.datarockets.constants.LOG_TAG
import com.example.datarockets.db.RoomDb
import com.example.datarockets.model.BeersListItem
import com.example.datarockets.repository.Repository
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {

    val progressbarObservable: MutableLiveData<Boolean> = MutableLiveData()

    private val repository: Repository = Repository()

    private val errorMessage = MutableLiveData<String>()
    var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getBeerItemListByPageObservable(
        context: Context,
        page: Int
    ): LiveData<List<BeersListItem>> {
        progressbarObservable!!.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getBeerListByPage(page)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    progressbarObservable.value = false
                    Thread(Runnable {
                        Log.d(
                            LOG_TAG,
                            "getBeerItemListByPageObservable ${response.body().toString()}"
                        )
                        RoomDb.getAppDatabase(context)!!.beerDao()!!.deleteAllImageItems()
                        RoomDb.getAppDatabase(context)!!.beerDao()!!
                            .insertBeerListItems(response.body()!!)
                    }).start()
                } else {
                    progressbarObservable.value = false
                    onError("Error : ${response.message()} ")
                }
            }
        }
        return RoomDb.getAppDatabase(context)!!.beerDao()!!.getBeerList()
    }

    fun getBeersList(context: Context, bFetchFromServer: Boolean) {
        if (bFetchFromServer) {
            job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val response = repository.getBeerList()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Thread(Runnable {
                            Log.d(
                                LOG_TAG,
                                "getBeerItemListObservable ${response.body().toString()}"
                            )
                            RoomDb.getAppDatabase(context)!!.beerDao()!!
                                .insertBeerListItems(response.body()!!)
                        }).start()
                    } else {
                        onError("Error : ${response.message()} ")
                    }
                }
            }
        }
    }

    fun getBeerItemListObservable(context: Context): LiveData<List<BeersListItem>> {
        return RoomDb.getAppDatabase(context)!!.beerDao()!!.getBeerList()
    }

    private fun onError(message: String) {
        Log.d(LOG_TAG, "onError message $message")
        errorMessage.value = message
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}

