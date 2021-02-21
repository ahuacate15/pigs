package com.ahuacate.pigs.ui.saving

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahuacate.pigs.R
import com.ahuacate.pigs.data.PiggyApplication
import com.ahuacate.pigs.data.model.SavingViewModel
import com.ahuacate.pigs.data.model.SavingViewModelFactory

class SavingListFragment : Fragment()  {

    private val TAG : String = "SavingListFragment"
    private lateinit var adapter : SavingListAdapter
    private var itemSelected : Int = 0

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

        val view : View = inflater.inflate(R.layout.fragment_saving, container, false)

        /* rSavingList is referenced automated by synthetic package */
        val rSavingList = view.findViewById<RecyclerView>(R.id.rSavingList)



        adapter = activity?.supportFragmentManager?.let { SavingListAdapter(view.context) {

            model ->
                context?.let {safeContext ->
                    if(model.isSelected) {
                        model.setBackgroundColor(ContextCompat.getColor(safeContext, R.color.white))
                        model.isSelected = false
                        itemSelected--
                    } else {
                        model.setBackgroundColor(ContextCompat.getColor(safeContext, R.color.pink_light_5))
                        model.isSelected = true
                        itemSelected++
                    }
                }

            Log.d(TAG, "LONG click " + itemSelected);
            return@SavingListAdapter true
        } }!!

        rSavingList.adapter = adapter
        rSavingList.layoutManager = LinearLayoutManager(view.context)

        /* add observer to saving list */
        savingViewModel.allSaving.observe(viewLifecycleOwner, Observer {
            listSaving -> adapter.setData(listSaving)
        })

        return view
    }

}