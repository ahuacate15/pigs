package com.ahuacate.pigs.model.dao

import android.util.Log
import com.ahuacate.pigs.data.model.SavingViewModel
import com.ahuacate.pigs.data.repository.SavingRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SavingViewModelTest {

    private lateinit var savingViewModel: SavingViewModel

    @Mock
    private lateinit var savingRepository : SavingRepository

    @Before
    fun setUp() {
        savingViewModel = SavingViewModel(savingRepository)
    }

    @Test
    fun `Calculate correct number of items`() {
        assertEquals(6, savingViewModel.getNumberItems(20).toInt())
        assertEquals(37, savingViewModel.getNumberItems(669).toInt())
        assertEquals(45, savingViewModel.getNumberItems(1020).toInt())
        assertEquals(100, savingViewModel.getNumberItems(5000).toInt())
        assertEquals(78, savingViewModel.getNumberItems(3013).toInt())
        assertEquals(100, savingViewModel.getNumberItems(5050).toInt())
        assertEquals(100, savingViewModel.getNumberItems(5001).toInt())
    }

    @Test
    fun `Apply absolute value to negative number of getNumberItems`() {
        assertEquals(savingViewModel.getNumberItems(10), savingViewModel.getNumberItems(-10))
    }

    @Test(timeout = 900)
    fun `Verify if approach amounts are greatest than input amount`() {
        var allOutputAreaGreatest : Boolean = true
        var numberItems : Int
        var aproxValue : Int

        for (savingAmount in 1..5050) {

            numberItems = savingViewModel.getNumberItems(savingAmount).toInt()
            aproxValue = savingViewModel.getAproxAmount(numberItems)

            if(savingAmount > aproxValue) {
                allOutputAreaGreatest = false
                break
            }
        }

        assertTrue(allOutputAreaGreatest)
    }

    @Test
    fun `Calculate correct gauss approach sum`() {
        assertEquals(105, savingViewModel.getAproxAmount(savingViewModel.getNumberItems(100).toInt()))
        assertEquals(5050, savingViewModel.getAproxAmount(savingViewModel.getNumberItems(5050).toInt()))
        assertEquals(5050, savingViewModel.getAproxAmount(savingViewModel.getNumberItems(5000).toInt()))
    }

    @Test
    fun `Apply absolute value to negative number of gauss sum`() {
        assertEquals(105, savingViewModel.getAproxAmount(savingViewModel.getNumberItems(-105).toInt()))
        assertEquals(5050, savingViewModel.getAproxAmount(savingViewModel.getNumberItems(-5050).toInt()))
    }
}