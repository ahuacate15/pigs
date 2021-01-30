package com.ahuacate.pigs.ui.saving

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.ahuacate.pigs.R
import com.ahuacate.pigs.data.entity.SavingEntity

class SavingViewFragment(val savingEntity : SavingEntity) : DialogFragment(), View.OnClickListener {

    private lateinit var iCloseVF : ImageView
    private lateinit var tTitleVF : TextView
    private lateinit var tAmountVF : TextView
    private lateinit var tCollectedAmountVF : TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_saving_view, container, false)

        iCloseVF = view.findViewById(R.id.iCloseVF)
        tTitleVF = view.findViewById(R.id.tTitleVF)
        tAmountVF = view.findViewById(R.id.tAmountVF)
        tCollectedAmountVF = view.findViewById(R.id.tCollectedAmountVF)

        iCloseVF.setOnClickListener(this)

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
                dialog?.dismiss()
            }
        }
    }

    private fun setData() {
        tTitleVF.text = savingEntity.title
        tAmountVF.text = savingEntity.aproxAmount.toString()

    }


}