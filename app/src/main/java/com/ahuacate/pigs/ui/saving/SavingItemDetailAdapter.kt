package com.ahuacate.pigs.ui.saving

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ahuacate.pigs.R
import com.ahuacate.pigs.data.entity.SavingDetailEntity
import com.ahuacate.pigs.data.model.SavingViewModel
import com.ahuacate.pigs.util.SystemCall

class SavingItemDetailAdapter(private val savingViewModel: SavingViewModel, private val context: Context) : RecyclerView.Adapter<SavingItemDetailAdapter.SavingItemDetailViewHolder>() {

    private var list : MutableList<SavingDetailEntity> = ArrayList()
    private val TAG : String = "SavingItemDetailAdapter"
    private val systemCall : SystemCall = SystemCall(context)

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
        systemCall.vibrate()

    }

    class SavingItemDetailViewHolder(itemView : View, private val context: Context) : RecyclerView.ViewHolder(itemView) {

        private val TAG : String = "SavingItemDetailViewHolder";
        private val tAmountBubble : TextView = itemView.findViewById(R.id.tAmountBubble);

        fun bind(entity: SavingDetailEntity) {
            tAmountBubble.text = entity.sequence.toString()

            if(entity.selected) {
                tAmountBubble.background = ContextCompat.getDrawable(context, R.drawable.s_circle_selected) 
                tAmountBubble.setTextColor(Color.WHITE)
            } else {
                tAmountBubble.background = ContextCompat.getDrawable(context, R.drawable.s_circle)
                tAmountBubble.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
            }
        }
    }
}