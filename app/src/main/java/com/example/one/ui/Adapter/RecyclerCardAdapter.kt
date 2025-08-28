package com.example.one.ui.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.one.R
import com.example.one.data.domain.Name
import com.example.one.ui.ViewHolders.CardViewHolder

class RecyclerCardAdapter(
    private var namesList: MutableList<Name> , /* the names list */
    val onClickListener : (String, Int) -> Unit,
    val onItemCountChanged: (Int) -> Unit
)  // function to get the index and the name that return nothing
    : RecyclerView.Adapter<CardViewHolder>() {

        val swipeToDelete = SwipeToDelete()
    fun updateData(newList: MutableList<Name>) {
        namesList = newList
        notifyDataSetChanged() // Or use DiffUtil for better performance
    }

    private var tempList : MutableList<Name> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view,parent,false)
        return CardViewHolder(view)
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
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) { // todo change background in onBinding
        val card = namesList[position]
        holder.cardText.text = card.name // put name in the card
        val context = holder.itemView.context
        if (position %2==0) holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.green_card))
    else holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.blue_card))

    holder.itemView.setOnClickListener {
            onClickListener(card.name?:"",position)  // for safety
        }
    }


    fun addData(list: List<Name>){
        tempList = list.toMutableList()
        notifyItemMoved(0,list.size.minus(1))
    }
    companion object { // static in Kotlin
        const val VIEW_TYPE_CARD_ONE = 0
        const val VIEW_TYPE_CARD_TWO = 1
    }

    fun deleteItem(position: Int){
      namesList.removeAt(position)
        notifyItemRemoved(position)
        onItemCountChanged(itemCount)

    }
    override fun getItemViewType(position: Int): Int {
        return when{
            position % 2 ==0 -> VIEW_TYPE_CARD_ONE
            else ->VIEW_TYPE_CARD_TWO
        }
    }

    override fun getItemCount() = namesList.size //

    inner class SwipeToDelete(): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(
            viewHolder: RecyclerView.ViewHolder,
            direction: Int
        ) {
           val position =viewHolder.adapterPosition
            deleteItem(position)
        }

    }
}