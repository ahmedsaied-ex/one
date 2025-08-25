package com.example.one.ui.ViewHolders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.one.R

class StringAdapter(private val stringList: List<String> /* the names list */) :
    RecyclerView.Adapter<StringAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvText: TextView = view.findViewById(R.id.name_id) /*the card Name*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context) /* the main screen */
            .inflate(R.layout.card_view, parent, false)
        return ViewHolder(view) // Creating and returning ViewHolder
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvText.text = stringList[position]
    }

    override fun getItemCount() = stringList.size
}