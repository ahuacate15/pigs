package com.ahuacate.pigs.ui.saving

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ahuacate.pigs.R
import com.ahuacate.pigs.data.entity.SavingEntity
import kotlinx.android.synthetic.main.recycler_saving_item.view.*
import org.w3c.dom.Text

class SavingListAdapter() : RecyclerView.Adapter<SavingListAdapter.SavingListViewHolder>() {

    private var listSaving : List<SavingEntity> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SavingListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_saving_item, parent, false)
        return SavingListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listSaving?.size
    }

    override fun onBindViewHolder(holder: SavingListViewHolder, position: Int) {
        val saving = listSaving[position]
        holder.bind(saving)
    }

    fun setData(listSaving : List<SavingEntity>) {
        this.listSaving = listSaving
        notifyDataSetChanged()
    }

    class SavingListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        private val tTitleSaving : TextView = itemView.findViewById(R.id.tTitleSaving)
        private val tAmountProgressSaving : TextView = itemView.findViewById(R.id.tAmountProgressSaving)
        private val tDescriptionSaving : TextView = itemView.findViewById(R.id.tDescriptionSaving)

        fun bind(savingEntity : SavingEntity) {
            tTitleSaving.text = savingEntity.title
            tAmountProgressSaving.text = "$${savingEntity.realAmount} de $${savingEntity.aproxAmount}"
            tDescriptionSaving.text = savingEntity.description
        }
    }
}