package com.ahuacate.pigs.ui.saving

import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.ahuacate.pigs.R
import com.ahuacate.pigs.data.PiggyApplication
import com.ahuacate.pigs.data.entity.SavingEntity
import com.ahuacate.pigs.data.model.SavingViewModel
import com.ahuacate.pigs.data.model.SavingViewModelFactory
import java.math.BigInteger

class SavingFormFragment : DialogFragment(), View.OnClickListener, View.OnFocusChangeListener {

    lateinit var bSaveForm : Button
    lateinit var iCloseForm : ImageView
    lateinit var tTitleForm : TextView
    lateinit var tRealAmount : TextView
    lateinit var tAproxAmount : TextView
    lateinit var tDescriptionForm : TextView

    val TAG : String = "SavingFormFragment"

    private val savingViewModel : SavingViewModel by viewModels {
        SavingViewModelFactory((activity?.application as PiggyApplication).repository)
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
        tAproxAmount = view.findViewById(R.id.tAproxAmount)
        tDescriptionForm = view.findViewById(R.id.tDescriptionForm)

        /* set event of elements */
        iCloseForm.setOnClickListener(this)
        bSaveForm.setOnClickListener(this)
        tRealAmount.onFocusChangeListener = this
        return view
    }

    override fun onClick(view : View?) {
        when(view?.id) {
            R.id.iCloseForm -> {
                dialog?.dismiss()
            }
            R.id.bSaveForm -> {
                savingViewModel.insert(getEntityFromUI())
                dialog?.dismiss()
            }
        }
    }

    private fun getEntityFromUI() : SavingEntity {
        val title : String = tTitleForm.text.toString()
        val realAmount : Int = tRealAmount.text.toString().toInt()
        val aproxAmount : Int = tAproxAmount.text.toString().toInt()
        val description = tDescriptionForm.text.toString()


        return SavingEntity(null, title, realAmount, aproxAmount, description)
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if(!hasFocus) {

            val realAmount : Int? = if(tRealAmount.text.toString().isEmpty()) 0 else tRealAmount.text.toString().toIntOrNull();
            val numberItems : Int = savingViewModel.getNumberItems(realAmount).toInt()
            var aproxAmount = savingViewModel.getAproxAmount(numberItems)

            tAproxAmount.text = aproxAmount.toString()
        }
    }
}