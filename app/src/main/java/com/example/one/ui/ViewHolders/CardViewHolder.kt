package com.example.one.ui.ViewHolders
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.one.R


class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) { // create a holder to the something that will change in the view
    val cardText: TextView = view.findViewById(R.id.name_id) /*the card Name*/
}
