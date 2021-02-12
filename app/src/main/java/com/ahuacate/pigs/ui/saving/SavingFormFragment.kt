package com.ahuacate.pigs.ui.saving

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.ahuacate.pigs.R
import com.ahuacate.pigs.data.PiggyApplication
import com.ahuacate.pigs.data.entity.SavingEntity
import com.ahuacate.pigs.data.model.SavingViewModel
import com.ahuacate.pigs.data.model.SavingViewModelFactory

class SavingFormFragment : DialogFragment(), View.OnClickListener {

    lateinit var bSaveForm : Button
    lateinit var iCloseForm : ImageView
    lateinit var tTitleForm : TextView
    lateinit var tRealAmount : TextView
    lateinit var tDescriptionForm : TextView

    val TAG : String = "SavingFormFragment"

    private val savingViewModel : SavingViewModel by viewModels {
        SavingViewModelFactory(
            (activity?.application as PiggyApplication).repository,
            (activity?.application as PiggyApplication).detailRepository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_saving_form, container, false)

        bSaveForm = view.findViewById(R.id.bSaveForm)
        iCloseForm = view.findViewById(R.id.iCloseForm)
        tTitleForm = view.findViewById(R.id.tTitleForm)
        tRealAmount = view.findViewById(R.id.tRealAmount)
        tDescriptionForm = view.findViewById(R.id.tDescriptionForm)

        /* set event of elements */
        iCloseForm.setOnClickListener(this)
        bSaveForm.setOnClickListener(this)
        return view
    }

    override fun onClick(view : View?) {
        when(view?.id) {
            R.id.iCloseForm -> {
                dialog?.dismiss()
            }
            R.id.bSaveForm -> {
                val entity : SavingEntity? = getEntityFromUI()
                if(entity != null) {
                    savingViewModel.insert(entity)
                    dialog?.dismiss()
                }
            }
        }
    }

    private fun getEntityFromUI() : SavingEntity? {

        if(tTitleForm.text.isEmpty()) {
            Toast.makeText(context, "Ingresa el titulo", Toast.LENGTH_SHORT).show()
            return null;
        }

        if(tRealAmount.text.isEmpty()) {
            Toast.makeText(context, "Ingresa la cantidad", Toast.LENGTH_SHORT).show()
            return null;
        }

        val title : String = tTitleForm.text.toString()
        val realAmount : Int = tRealAmount.text.toString().toInt()
        val description = tDescriptionForm.text.toString()

        val numberItems : Int = savingViewModel.getNumberItems(realAmount).toInt()
        var aproxAmount = savingViewModel.getAproxAmount(numberItems)

        return SavingEntity(null, title, realAmount, aproxAmount, description, 0)
    }


}