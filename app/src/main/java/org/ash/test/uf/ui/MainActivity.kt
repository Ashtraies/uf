package org.ash.test.uf.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import org.ash.test.uf.R
import org.ash.test.uf.ui.adapter.PagedUserAdapter
import org.ash.test.uf.util.Logger
import org.ash.test.uf.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: PagedUserAdapter

    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.L("onCreate")
        setContentView(R.layout.activity_main)
        adapter = PagedUserAdapter()
        user_list.adapter = adapter

        gender.setOnCheckedChangeListener { _, isChecked ->
            showDefault(isChecked)
        }
        clear.setOnClickListener {
            userViewModel.clear()
        }
        fetch.setOnClickListener {
            try {
                val number = number.text.toString().toInt()
                if(number > 5000) {
                    Toast.makeText(this,R.string.result_exceeds,Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                userViewModel.usersFromApi(number)
            } catch (e: NumberFormatException) {
                Logger.L("${e.message}, return.")
                return@setOnClickListener
            }
        }

        lifecycleScope.launch {
            val count = userViewModel.dataCount()
            if (count > 0) {
                Logger.L("Database has data.")
            } else {
                Logger.L("No data, get 1000 users.")
                userViewModel.usersFromApi(1000)
            }
        }
        showDefault(gender.isChecked)
    }

    private fun observer() {
        userViewModel.usersByGender()
            .observe(this, Observer(adapter::submitList))
    }

    private fun showDefault(isChecked: Boolean) {
        val gender = if (isChecked) "female" else "male"
        userViewModel.gender.value = gender
        observer()
    }
}
