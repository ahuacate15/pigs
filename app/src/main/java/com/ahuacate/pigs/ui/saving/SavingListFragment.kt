package com.ahuacate.pigs.ui.saving

import android.content.Context
import android.content.Intent
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
import com.ahuacate.pigs.data.entity.SavingEntity
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
        val rSavingList = view.findViewById<RecyclerView>(R.id.rSavingList)

        /* lambda actions */
        val lambdaLongClick : (View) -> Boolean = {
            itemView ->
                toggleSelectedItem(itemView, view.context)
                true
        }

        val lambdaClick : (View, SavingEntity) -> Unit = {
            itemView, entity ->
            run {

                if(itemSelected == 0) {
                    val intent: Intent = Intent(context, SavingViewActivity::class.java)
                    intent.putExtra("id", entity.idSaving)
                    context?.startActivity(intent)
                } else {
                    toggleSelectedItem(itemView, view.context)
                }

            }
        }


        adapter = activity?.supportFragmentManager?.let { SavingListAdapter(view.context, lambdaClick, lambdaLongClick) }!!

        rSavingList.adapter = adapter
        rSavingList.layoutManager = LinearLayoutManager(view.context)

        /* add observer to saving list */
        savingViewModel.allSaving.observe(viewLifecycleOwner, Observer {
            listSaving -> adapter.setData(listSaving)
        })

        return view
    }

    private fun toggleSelectedItem(view : View, context : Context) {
        if(view.isSelected) {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            view.isSelected = false
            itemSelected++
        } else {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.pink_light_5))
            view.isSelected = true
            itemSelected--
        }
    }

}