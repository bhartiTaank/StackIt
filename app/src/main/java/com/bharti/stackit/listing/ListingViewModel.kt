package com.bharti.stackit.listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharti.stackit.retrofit.model.StackItem
import kotlinx.coroutines.launch

class ListingViewModel : ViewModel() {
    private val dataSource: ListingDataSource = ListingDataSource()

    private val _listingLiveData: MutableLiveData<List<StackItem>> = MutableLiveData()
    val listingLiveData: LiveData<List<StackItem>> get() = _listingLiveData

    private val _errorLiveData: MutableLiveData<Unit> = MutableLiveData()
    val errorLiveData: LiveData<Unit> get() = _errorLiveData

    fun getStackData() {
        viewModelScope.launch {
            try {
                val response = dataSource.fetchStackDataFromNetwork()
                _listingLiveData.postValue(response.items.filter { it.isAnswered && it.answerCount > 1 && it.acceptedAnswerId != null})
            } catch (e: Exception) {
                _errorLiveData.postValue(Unit)
            }
        }
    }
}