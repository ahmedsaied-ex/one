package com.example.one


import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.one.data.domain.Name
import com.example.one.data.domain.Names
import com.example.one.databinding.ActivityMainBinding
import com.example.one.ui.Adapter.RecyclerCardAdapter
import java.util.Locale
import kotlin.collections.mutableListOf

class MainActivity : AppCompatActivity() {
    lateinit var itemNumber : TextView
    lateinit var recyclerView : RecyclerView
    var isArabic: Boolean = false
    var names =mutableListOf<Name>()


    lateinit var binding : ActivityMainBinding
    lateinit var translateButton  : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        loadLanguagePreference()
        listBuilder()
        initViews()
        setContentView(binding.root)
        initRecycleView()
        callBack()

    }

    private fun listBuilder() {
        for (x in Names().stringList){
            names.add(Name(x))
        }
    }

    private fun switchLanguage() {
        isArabic=!isArabic // switch the boolean value when ever the button is clicked
        chaneAppLanguage(if(isArabic) "ar" else "en")
        saveLanguagePreference()
        recreate()

    }
    private fun saveLanguagePreference() {
        val sharedPref = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("is_arabic", isArabic)
            apply()
        }
    }
    private fun loadLanguagePreference() {
        val sharedPref = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        isArabic = sharedPref.getBoolean("is_arabic", false)
        chaneAppLanguage(if (isArabic) "ar" else "en")
    }




private fun chaneAppLanguage(language :String) {
        val local = Locale(language)
        Locale.setDefault(local)
        val config = Configuration()
        config.locale=local
        resources.updateConfiguration(config,resources.displayMetrics)
    }

    private fun initRecycleView() {
        recyclerView.layoutManager= LinearLayoutManager(this) // by default is vertically
        recyclerView.adapter = RecyclerCardAdapter(
            names,
            { name, positon ->
                makeToast(name,positon)
            })
    }
    private fun makeToast(name :String , positon:Int){
        Toast.makeText(
            this,
            "${getString(R.string.main_message_en)} $name ${getString(R.string.pos_message_en)} ${positon + 1}",
            Toast.LENGTH_SHORT
        ).show()
    }
    private fun initViews() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        recyclerView =binding.recyclerView
        itemNumber = binding.itemsNumber
        translateButton =binding.translateButton

    }

    private fun callBack() {
        val itemCount =recyclerView.adapter?.itemCount.toString()
        itemNumber.text = "$itemCount in the list"

        translateButton.setOnClickListener {
            switchLanguage()
        }
    }
}