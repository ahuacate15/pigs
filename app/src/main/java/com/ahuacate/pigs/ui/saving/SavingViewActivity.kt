package com.ahuacate.pigs.ui.saving

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahuacate.pigs.R
import com.ahuacate.pigs.data.PiggyApplication
import com.ahuacate.pigs.data.entity.SavingDetailEntity
import com.ahuacate.pigs.data.entity.SavingEntity
import com.ahuacate.pigs.data.model.SavingViewModel
import com.ahuacate.pigs.data.model.SavingViewModelFactory
import com.ahuacate.pigs.util.SystemCall
import com.google.android.material.progressindicator.ProgressIndicator

class SavingViewActivity : AppCompatActivity() {

    private lateinit var tAmountVF : TextView
    private lateinit var tCollectedAmountVF : TextView
    private lateinit var rSavingItemDetail : RecyclerView
    private lateinit var progressIndicator : ProgressIndicator

    private lateinit var adapter : SavingItemDetailAdapter
    private lateinit var listData : LiveData<MutableList<SavingDetailEntity>>
    private lateinit var systemCall : SystemCall

    private var id : Int = 0

    private val savingViewModel : SavingViewModel by viewModels {
        SavingViewModelFactory(
            (application as PiggyApplication).repository,
            (application as PiggyApplication).detailRepository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_saving_view)

        id = intent.getIntExtra("id", 0)

        systemCall = SystemCall(this)

        /* bind UI elements */
        tAmountVF = findViewById(R.id.tAmountVF)
        tCollectedAmountVF = findViewById(R.id.tCollectedAmountVF)
        rSavingItemDetail = findViewById(R.id.rSavingItemDetail)
        progressIndicator = findViewById(R.id.progressIndicator)

        /* define adapter to show bubbles */
        adapter = SavingItemDetailAdapter(savingViewModel, this)
        rSavingItemDetail.adapter = adapter
        rSavingItemDetail.layoutManager = GridLayoutManager(this, 5)

        /* load entity by ID */
        savingViewModel.findById(id).observe(this, Observer {
            entity -> setData(entity)
        })

        /* load detail from entity  */
        listData = savingViewModel.findDetailOfSaving(id)

        listData.observe(this,  Observer {
            list -> adapter.setData(list)
            listData.removeObservers(this)

            progressIndicator.visibility = View.GONE
            Log.d("SavingViewFragment", "Actualizando datos");
        })

    }


    private fun setData(savingEntity: SavingEntity) {
        //tTitleVF.text = savingEntity.title
        tAmountVF.text = savingEntity.aproxAmount.toString()
    }


}