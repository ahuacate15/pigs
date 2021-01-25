package com.ahuacate.pigs.data.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ahuacate.pigs.data.entity.PiggyEntity
import com.ahuacate.pigs.data.repository.PiggyRepository
import kotlinx.coroutines.launch

class PiggyViewModel(private val repository : PiggyRepository) : ViewModel() {

    var allPiggies = repository.allPiggies.asLiveData()

    fun insert(piggyEntity : PiggyEntity) = viewModelScope.launch {
        var id = repository.insert(piggyEntity);
    }
}

class PiggyViewModelFactory(private val repository : PiggyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PiggyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PiggyViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}