package com.example.one.ui.ViewHolders
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.one.databinding.CardViewBinding


class CardViewHolder(val binding: CardViewBinding) : RecyclerView.ViewHolder(binding.root) { // create a holder to the something that will change in the view
    val cardText: TextView = binding.nameId /*the card Name*/
}
