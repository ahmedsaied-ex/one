package com.example.one

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.one.data.domain.Names
import com.example.one.ui.ViewHolders.StringAdapter

class MainActivity : AppCompatActivity() {
    lateinit var itemNumber : TextView
    lateinit var recyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        itemNumber = findViewById(R.id.items_number)

        recyclerView.layoutManager= LinearLayoutManager(this) // by default is vertically
        recyclerView.adapter = StringAdapter(
            Names().stringList,
            {
            name ,positon ->
            Toast.makeText(this, "you have pressed $name in pos: ${positon+1}", Toast.LENGTH_SHORT).show()
        })
        callBack()

    }

    private fun callBack() {
        val itemCount =recyclerView.adapter?.itemCount.toString()
        itemNumber.text = "$itemCount in the list"
    }
}