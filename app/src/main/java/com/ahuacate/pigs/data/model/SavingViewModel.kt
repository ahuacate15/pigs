package com.ahuacate.pigs.data.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ahuacate.pigs.data.entity.SavingEntity
import com.ahuacate.pigs.data.repository.SavingRepository
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import kotlin.math.absoluteValue
import kotlin.math.sqrt

class SavingViewModel(private val repository : SavingRepository) : ViewModel() {

    var allSaving = repository?.allSaving?.asLiveData()

    fun insert(savingEntity: SavingEntity) = viewModelScope.launch {
        repository.insert(savingEntity)
    }

    /**
     * inverse gauss summatory
     * the function recieves a integer number and calculates the number of consecutive sums needed to calculate the closest number
     * we know that adding from 1 to 100, the result is 5050
     * inversely, 5050 needs 100 consecutive sums to get this value
     * 5051 needs 100.0074624 consecutive sums, then we take the nearest integer (101)
     */
    fun getNumberItems(amount: Int?) : BigInteger {

        if(amount == null)
            return BigInteger.ZERO;

        var inverseGaussSum : BigDecimal = ((-1 + sqrt(-1 + 8*amount.absoluteValue.toDouble())) / 2).toBigDecimal().setScale(4, RoundingMode.HALF_EVEN);

        //integer value without decimal part
        var gaussIntValue : BigInteger = inverseGaussSum.toBigInteger()

        //just a decimal part of amount
        var gaussDecimal : BigDecimal = inverseGaussSum.subtract(gaussIntValue.toBigDecimal())

        //aprouch to nearest integer, if decimals are greatess than zero
        return gaussIntValue.add(if (gaussDecimal > BigDecimal.ZERO) BigInteger.ONE else BigInteger.ZERO );
    }

    fun getAproxAmount(numberItems : Int) : Int {
        return numberItems.absoluteValue * (numberItems.absoluteValue + 1) / 2
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