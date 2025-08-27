package com.example.one.ui.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.one.R
import com.example.one.data.domain.Name
import com.example.one.ui.ViewHolders.CardViewHolder

class RecyclerCardAdapter(
    private var namesList: MutableList<Name> , /* the names list */
    val onClickListener : (String, Int) -> Unit)  // function to get the index and the name that return nothing
    : RecyclerView.Adapter<CardViewHolder>() {

    fun updateData(newList: MutableList<Name>) {
        namesList = newList
        notifyDataSetChanged() // Or use DiffUtil for better performance
    }

    private var tempList : MutableList<Name> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return when(viewType){
            VIEW_TYPE_CARD_ONE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
//                view.setBackgroundColor(ContextCompat.getColor(parent.context, R.color.green_card))
//                view.setBackgroundResource(R.color.green_card)
                view.setBackgroundResource(R.drawable.card_background_color_even)
                CardViewHolder(view) // Creating and returning ViewHolder
            }
            VIEW_TYPE_CARD_TWO -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
                view.setBackgroundResource(R.drawable.card_background_color_odd)

                CardViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.card_view, parent, false)
                 CardViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = namesList[position]
        holder.cardText.text = card.name // put name in the card
        holder.itemView.setOnClickListener {
            onClickListener(card.name.toString(),position)
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


    override fun getItemViewType(position: Int): Int {
        return when{
            position % 2 ==0 -> VIEW_TYPE_CARD_ONE
            else ->VIEW_TYPE_CARD_TWO
        }
    }

    override fun getItemCount() = namesList.size //
}