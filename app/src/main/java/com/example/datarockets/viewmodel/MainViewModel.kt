package com.example.datarockets.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.datarockets.db.RoomDb
import com.example.datarockets.model.BeersListItem
import com.example.datarockets.repository.Repository
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {

    private var recyclerListData: MutableLiveData<ArrayList<BeersListItem>> = MutableLiveData()

    private val repository: Repository = Repository()

    private val errorMessage = MutableLiveData<String>()
    var job: Job? = null

    private val loading = MutableLiveData<Boolean>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getBeerItemListObservable(
        context: Context,
        bFetchFromServer: Boolean,
        page: Int,
        perPage: Int
    ): LiveData<List<BeersListItem>> {
        if (bFetchFromServer) {
            job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val response = repository.getBeerList(page, perPage)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Thread(Runnable {
                            Log.d("LOG_TAG", response.body().toString())
                            RoomDb.getAppDatabase(context)!!.beerDao()!!.deleteAllImageItems()
                            RoomDb.getAppDatabase(context)!!.beerDao()!!
                                .insertBeerListItems(response.body()!!)
                        }).start()
                        recyclerListData.postValue(response.body())
                        loading.value = false
                    } else {
                        onError("Error : ${response.message()} ")
                    }
                }
            }
        }
        return RoomDb.getAppDatabase(context)!!.beerDao()!!.getBeerList()
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

