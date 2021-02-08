package com.ahuacate.pigs.ui.saving

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahuacate.pigs.R
import com.ahuacate.pigs.data.PiggyApplication
import com.ahuacate.pigs.data.entity.SavingDetailEntity
import com.ahuacate.pigs.data.entity.SavingEntity
import com.ahuacate.pigs.data.model.SavingViewModel
import com.ahuacate.pigs.data.model.SavingViewModelFactory
import com.ahuacate.pigs.util.SystemCall
import com.google.android.material.progressindicator.ProgressIndicator

class SavingViewFragment(val savingEntity : SavingEntity) : DialogFragment(), View.OnClickListener {

    private lateinit var iCloseVF : ImageView
    private lateinit var iOpenMenuVF : ImageView
    private lateinit var tTitleVF : TextView
    private lateinit var tAmountVF : TextView
    private lateinit var tCollectedAmountVF : TextView
    private lateinit var rSavingItemDetail : RecyclerView
    private lateinit var progressIndicator : ProgressIndicator

    private lateinit var adapter : SavingItemDetailAdapter
    private lateinit var listData : LiveData<MutableList<SavingDetailEntity>>
    private lateinit var systemCall : SystemCall

    private val savingViewModel : SavingViewModel by viewModels {
        SavingViewModelFactory(
            (activity?.application as PiggyApplication).repository,
            (activity?.application as PiggyApplication).detailRepository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_saving_view, container, false)

        systemCall = SystemCall(context)

        /* bind UI elements */
        iCloseVF = view.findViewById(R.id.iCloseVF)
        iOpenMenuVF = view.findViewById(R.id.iOpenMenuVF)
        tTitleVF = view.findViewById(R.id.tTitleVF)
        tAmountVF = view.findViewById(R.id.tAmountVF)
        tCollectedAmountVF = view.findViewById(R.id.tCollectedAmountVF)
        rSavingItemDetail = view.findViewById(R.id.rSavingItemDetail)
        progressIndicator = view.findViewById(R.id.progressIndicator)

        /* define adapter to show bubbles */
        adapter = SavingItemDetailAdapter(savingViewModel, context)
        rSavingItemDetail.adapter = adapter
        rSavingItemDetail.layoutManager = GridLayoutManager(view.context, 5)

        listData = savingViewModel.findDetailOfSaving(savingEntity.idSaving)

        listData.observe(viewLifecycleOwner,  Observer {
            list -> adapter.setData(list)
            listData.removeObservers(viewLifecycleOwner)

            progressIndicator.visibility = View.GONE
            Log.d("SavingViewFragment", "Actualizando datos");
        })


        iCloseVF.setOnClickListener(this)
        iOpenMenuVF.setOnClickListener(this)
        setData()

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.iCloseVF -> {
                systemCall.vibrate()
                dialog?.dismiss()
            }
            R.id.iOpenMenuVF -> {
                systemCall.vibrate()
            }
        }
    }

    private fun setData() {
        tTitleVF.text = savingEntity.title
        tAmountVF.text = savingEntity.aproxAmount.toString()

    }


}