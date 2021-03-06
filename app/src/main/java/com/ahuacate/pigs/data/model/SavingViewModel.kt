package com.ahuacate.pigs.data.model

import androidx.lifecycle.*
import com.ahuacate.pigs.data.entity.SavingDetailEntity
import com.ahuacate.pigs.data.entity.SavingEntity
import com.ahuacate.pigs.data.repository.SavingDetailRepository
import com.ahuacate.pigs.data.repository.SavingRepository
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import kotlin.math.absoluteValue
import kotlin.math.sqrt

class SavingViewModel(private val repository : SavingRepository, private val detailRepository: SavingDetailRepository) : ViewModel() {

    var allSaving = repository?.allSaving?.asLiveData()

    fun findById(id : Int) : LiveData<SavingEntity> {
        return repository.findById(id).asLiveData()
    }

    fun insert(savingEntity: SavingEntity) = viewModelScope.launch {
        val id = repository.insert(savingEntity)

        var listDetail : MutableList<SavingDetailEntity> = mutableListOf<SavingDetailEntity>()
        val numberItems = getNumberItems(savingEntity.realAmount).toInt()

        for(sequence in 1..numberItems) {
            var detail = SavingDetailEntity(null, sequence, false, id)
            listDetail.add(detail)
        }

        detailRepository.insertAll(listDetail)
    }

    fun findDetailOfSaving(idSavingEntity : Int? = 0) : LiveData<MutableList<SavingDetailEntity>> {
        return detailRepository.findByIdSaving(idSavingEntity).asLiveData()
    }

    /**
     * toogle selected state of detail entity
     * when selected fields is true, the method will update to false
     * likewise, when the field is false, the method will be true
     * */
    fun updateDetail(idSaving : Int, detailEntity: SavingDetailEntity) = viewModelScope.launch {
        //update detail
        detailRepository.update(detailEntity.selected, detailEntity.sequence, detailEntity.idSaving)

        //add/substract acumulated amount
        val amount = if(detailEntity.selected) {
            detailEntity.sequence
        } else {
            detailEntity.sequence * -1
        }

        repository.update(idSaving, amount)
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

    /**
     * get accumulated % of saving
     * $1000.00/$5050.00 => 19.08% accumulated
     * $ 505.00/$5050.00 => 10.00% accumulated
     */
    fun getPercent(total : Int, acumulated : Int) : Float {
        return 100 * acumulated.toFloat() / total.toFloat();
    }

}

class SavingViewModelFactory(private val repository: SavingRepository, private val detailRepository: SavingDetailRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SavingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SavingViewModel(repository, detailRepository) as T
        }

        throw IllegalArgumentException("Unknow ViewModel class")
    }

}