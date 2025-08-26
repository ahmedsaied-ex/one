package com.example.one.ui.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.one.R
import com.example.one.data.domain.Name
import com.example.one.ui.ViewHolders.CardViewHolder

class RecyclerCardAdapter(
    private val namesList: MutableList<Name> , /* the names list */
    val onClickListener : (String, Int) -> Unit)  // function to get the index and the name that return nothing
    : RecyclerView.Adapter<CardViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return CardViewHolder(view) // Creating and returning ViewHolder
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = namesList[position]
        holder.cardText.text = card.name // put name in the card
        holder.itemView.setOnClickListener {
            onClickListener(card.name.toString(),position)
        }
    }

    override fun getItemCount() = namesList.size //
}