package com.ahuacate.pigs.data.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ahuacate.pigs.data.entity.SavingEntity
import com.ahuacate.pigs.data.repository.SavingRepository
import kotlinx.coroutines.launch

class SavingViewModel(private val repository : SavingRepository) : ViewModel() {

    var allSaving = repository.allSaving.asLiveData();

    fun insert(savingEntity: SavingEntity) = viewModelScope.launch {
        repository.insert(savingEntity)
    }
}

class SavingViewModelFactory(private val repository: SavingRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SavingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SavingViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknow ViewModel class")
    }

}