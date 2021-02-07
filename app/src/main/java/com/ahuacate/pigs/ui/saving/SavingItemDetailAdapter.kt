package com.ahuacate.pigs.ui.saving

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ahuacate.pigs.R
import com.ahuacate.pigs.data.entity.SavingDetailEntity
import com.ahuacate.pigs.data.model.SavingViewModel

class SavingItemDetailAdapter(private val savingViewModel: SavingViewModel, private val context: Context?) : RecyclerView.Adapter<SavingItemDetailAdapter.SavingItemDetailViewHolder>() {

    private var list : MutableList<SavingDetailEntity> = ArrayList()
    private val TAG : String = "SavingItemDetailAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavingItemDetailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_saving_item_detail, parent, false)
        return SavingItemDetailViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return list?.size
    }

    override fun onBindViewHolder(holder: SavingItemDetailViewHolder, position: Int) {
        var entity = list[position]
        holder.bind(entity)
        holder.itemView.setOnClickListener(View.OnClickListener {
            toogleSelected(entity, holder.adapterPosition)
        })
    }

    fun setData(list : MutableList<SavingDetailEntity>) {
        this.list = list
        notifyDataSetChanged()
    }

    private fun toogleSelected(entity : SavingDetailEntity, position : Int) {


        entity.selected = !entity.selected
        this.list[position] = entity
        savingViewModel.updateDetail(entity)
        notifyItemChanged(position)
        vibratePhone()

    }

    fun vibratePhone() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }
    }

    class SavingItemDetailViewHolder(itemView : View, private val context: Context?) : RecyclerView.ViewHolder(itemView) {

        private val TAG : String = "SavingItemDetailViewHolder";
        private val tAmountBubble : TextView = itemView.findViewById(R.id.tAmountBubble);

        fun bind(entity: SavingDetailEntity) {
            tAmountBubble.text = entity.sequence.toString()

            if(entity.selected) {
                itemView.background = context?.let { ContextCompat.getDrawable(it, R.drawable.s_circle_selected) }
            } else {
                itemView.background = context?.let { ContextCompat.getDrawable(it, R.drawable.s_circle) }
            }
        }
    }
}