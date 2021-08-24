package com.example.datarockets.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datarockets.constants.LOG_TAG
import com.example.datarockets.db.RoomDb
import com.example.datarockets.model.BeersListItem
import com.example.datarockets.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel : ViewModel() {

    private val _beerLiveData: MutableLiveData<List<BeersListItem>>
            by lazy { MutableLiveData<List<BeersListItem>>() }

    private val repository: Repository = Repository()

    val beerLiveData: LiveData<List<BeersListItem>>
        get() = _beerLiveData

    fun getBeerItemObservable(context: Context, id: Int) {
        viewModelScope.launch {
            val beer = RoomDb.getAppDatabase(context)!!.beerDao()!!.getBeerItemById(id)
            _beerLiveData.postValue(beer)
        }
    }

    fun getBeerItemById(context: Context, id: Int) {
        viewModelScope.launch {
            val response = repository.getBeerItemById(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Thread(kotlinx.coroutines.Runnable {
                        Log.d(
                            LOG_TAG,
                            "getBeerItemById ${response.body().toString()}"
                        )
                        RoomDb.getAppDatabase(context)!!.beerDao()!!.update(response.body()!![0])
                    }).start()
                }
            }
        }
    }
}


