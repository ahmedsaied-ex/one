package com.example.one.ui.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.one.R
import com.example.one.data.domain.Name
import com.example.one.databinding.CardViewBinding
import com.example.one.ui.ViewHolders.CardViewHolder
class RecyclerCardAdapter(
    val onClickListener: (String, Int) -> Unit,
    val onItemCountChanged: (Int) -> Unit
) : ListAdapter<Name, CardViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = CardViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = getItem(position)
        holder.cardText.text = card.name
        val context = holder.itemView.context
        if (position % 2 == 0) {
            holder.binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.green_card))
        } else {
            holder.binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.blue_card))
        }

        holder.itemView.setOnClickListener {
            onClickListener(card.name ?: "", position)
        }
    }

    fun updateData(newList: List<Name>) {
        submitList(newList.toList())
        onItemCountChanged(newList.size)
    }

    fun deleteItem(position: Int) {
        val current = currentList.toMutableList()
        if (position in current.indices) {
            current.removeAt(position)
            submitList(current)
            onItemCountChanged(current.size)
        }
    }

    inner class SwipeToDelete :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            deleteItem(viewHolder.adapterPosition)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Name>() {
        override fun areItemsTheSame(oldItem: Name, newItem: Name): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Name, newItem: Name): Boolean =
            oldItem == newItem
    }
}




/*
//        return when(viewType){
//            VIEW_TYPE_CARD_ONE -> {
//                val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
////                view.setBackgroundColor(ContextCompat.getColor(parent.context, R.color.green_card))
////                view.setBackgroundResource(R.color.green_card)
//                view.setBackgroundResource(R.drawable.card_background_color_even)
//                CardViewHolder(view) // Creating and returning ViewHolder
//            }
//            VIEW_TYPE_CARD_TWO -> {
//                val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
//                view.setBackgroundResource(R.drawable.card_background_color_odd)
//
//                CardViewHolder(view)
//            }
//            else -> {
//                val view = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.card_view, parent, false)
//                 CardViewHolder(view)
//            }
//        }
*
*/