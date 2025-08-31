package com.example.one


import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.one.data.domain.Name
import com.example.one.data.domain.Names
import com.example.one.databinding.ActivityMainBinding
import com.example.one.ui.Adapter.RecyclerCardAdapter
import java.util.Locale
import kotlin.collections.mutableListOf


class MainActivity : AppCompatActivity() {

    var isArabic: Boolean = false
    var names = mutableListOf<Name>()
    var itemCount = 0

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        loadLanguagePreference()
        initViews()
        listBuilder()
        initRecycleView()
        insertData()
        eventUI()

    }

    private fun insertData() {
        itemCount = binding.rvCardNames.adapter?.itemCount ?: 0
        binding.tvItemsNumber.text = getString(R.string.number_of_items ,itemCount )
    }

    private fun listBuilder() {
        names.addAll(
            Names().stringList.mapIndexed { index, item ->
                Name(item, index) // id = index
            }
        )
    }

    private fun switchLanguage() {
        isArabic = !isArabic // switch the boolean value when ever the button is clicked
        chaneAppLanguage(if (isArabic) "ar" else "en")
        saveLanguagePreference()
        recreate()

    }

    private fun saveLanguagePreference() {
        val sharedPref = getSharedPreferences("app_prefs", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("is_arabic", isArabic)
            apply()
        }
    }

    private fun loadLanguagePreference() {
        val sharedPref = getSharedPreferences("app_prefs", MODE_PRIVATE)
        isArabic = sharedPref.getBoolean("is_arabic", false)
        chaneAppLanguage(if (isArabic) "ar" else "en")
    }


    private fun chaneAppLanguage(language: String) {
        val local = Locale(language)
        Locale.setDefault(local)
        val config = Configuration()
        config.locale = local
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun initRecycleView() {
        binding.rvCardNames.layoutManager = LinearLayoutManager(this) // by default is vertically
         val adapter = RecyclerCardAdapter(
            { name, pos ->
               makeToast(name,pos)
            },
            { count ->
                updateItemCount(count)
                Log.d("Recycler", "Item count = $count")
            }
        )
        adapter.updateData(names)
        binding.rvCardNames.adapter = adapter
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString().trim()
                val filteredList = if (searchText.isEmpty()) {
                    names // Show all items when search is empty
                } else {
                    filterData(names, searchText)
                }
                adapter.updateData(filteredList)
            }
        })
        // now we initiated a item touchHelper to swipe and give it the function we had created it in the adapter
        val itemTouchHelper = ItemTouchHelper(adapter.SwipeToDelete())
        //here we attached the item touchHelper to our recyclerView
        itemTouchHelper.attachToRecyclerView(binding.rvCardNames)
        updateItemCount(adapter.itemCount)

    }

    private fun updateItemCount(count : Int) {
        itemCount = count
        binding.tvItemsNumber.text = getString(R.string.number_of_items,itemCount)

    }

    fun filterData(namesList: MutableList<Name> , searchText : String): MutableList<Name> {
          val filtered  = namesList.filter{
            it.name.contains(searchText, true)
        }
        return filtered as MutableList<Name>
    }




    private fun makeToast(name: String, positon: Int) {
        Toast.makeText(
            this,
            "${getString(R.string.main_message_en)} $name ${getString(R.string.pos_message_en)} ${positon + 1}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun initViews() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvItemsNumber.text = names.size.toString()
    }

    private fun eventUI() {
        binding.btnTranslate.setOnClickListener {
            switchLanguage()
        }
        binding.btnAddName.setOnClickListener {
            val newItem = binding.etAddName.text.toString().trim()
            if (newItem.isNullOrEmpty()) {
                binding.etAddName.error = getString(R.string.please_enter_name_first)
                binding.etAddName.hint = getString(R.string.please_enter_name_first)

            } else {
                addToRecycler(newItem)
                binding.etAddName.setText(null)
                binding.etAddName.clearFocus()

            }

        }


    }

    private fun addToRecycler(new_name: String) {
        val newId = if (names.isEmpty()) 0 else names.maxOf { it.id } + 1
        val newItem = Name(new_name,  newId)
        names.add(newItem)

        // Instead of notifyItemInserted (old RecyclerView way)
        // we should use ListAdapter’s submitList
        val adapter = binding.rvCardNames.adapter as RecyclerCardAdapter
        adapter.updateData(names.toList())

        binding.rvCardNames.scrollToPosition(names.size - 1)



    }


}