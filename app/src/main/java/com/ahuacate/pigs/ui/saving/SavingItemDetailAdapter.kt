package com.ahuacate.pigs.ui.saving

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ahuacate.pigs.R
import com.ahuacate.pigs.data.entity.SavingDetailEntity

class SavingItemDetailAdapter : RecyclerView.Adapter<SavingItemDetailAdapter.SavingItemDetailViewHolder>()  {

    private var list : List<SavingDetailEntity> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavingItemDetailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_saving_item_detail, parent, false)
        return SavingItemDetailViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.size
    }

    override fun onBindViewHolder(holder: SavingItemDetailViewHolder, position: Int) {
        var entity = list[position]
        holder.bind(entity)
    }

    fun setData(list : List<SavingDetailEntity>) {
        this.list = list
        notifyDataSetChanged()
    }

    class SavingItemDetailViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private val tAmountBubble : TextView = itemView.findViewById(R.id.tAmountBubble);

        fun bind(entity: SavingDetailEntity) {
            tAmountBubble.text = entity.sequence.toString()
        }
    }
}