package com.example.datarockets.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datarockets.model.BeersListItem
import com.example.datarockets.repository.Repository
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    private val _beerLiveData: MutableLiveData<List<BeersListItem>>
            by lazy { MutableLiveData<List<BeersListItem>>() }

    val beerLiveData: LiveData<List<BeersListItem>>
        get() = _beerLiveData

    private val repository: Repository = Repository()


    fun getBeerItem(id: Int) {
        viewModelScope.launch {
            val beer = repository.getBeerItem(id)
            _beerLiveData.postValue(beer.body())
        }
    }
}