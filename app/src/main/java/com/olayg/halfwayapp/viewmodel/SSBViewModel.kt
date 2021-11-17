package com.olayg.halfwayapp.viewmodel

import androidx.lifecycle.*
import com.olayg.halfwayapp.model.custom.Character
import com.olayg.halfwayapp.repo.DataStoreRepo
import com.olayg.halfwayapp.repo.SSBRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SSBViewModel(private val dataStoreRepo: DataStoreRepo) : ViewModel() {

    val categories = liveData { emit(SSBRepo.getAllCharacters()) }

    val dataPref = dataStoreRepo.readFromDataStore.asLiveData()

    fun saveDataPref(data: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.saveToDataStore(data)
        }
    }

    fun clearUserPref() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepo.clearDataStore()
        }
    }
}

class ViewModelFactory(
    private val dataStoreRepo: DataStoreRepo)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SSBViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SSBViewModel(dataStoreRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

