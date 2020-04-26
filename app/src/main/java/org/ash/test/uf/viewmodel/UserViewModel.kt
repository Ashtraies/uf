package org.ash.test.uf.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.ash.test.uf.dao.UserDatabase
import org.ash.test.uf.repository.UserRepository
import org.ash.test.uf.util.Users


class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = UserDatabase.getDatabase(application).userDao()

    private val repository = UserRepository(userDao)

    val gender = MutableLiveData<String>()

    fun usersByGender() = repository.getUserByGender(gender.value)

    fun usersFromApi(number: Int) =
        repository.getUserFromApi(number) {
            viewModelScope.launch { insert(it) }
        }

    suspend fun dataCount() = repository.dataCount()

    private fun insert(user: Users) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(user)
    }

    fun clear() = viewModelScope.launch(Dispatchers.IO) {
        repository.clear()
    }
}