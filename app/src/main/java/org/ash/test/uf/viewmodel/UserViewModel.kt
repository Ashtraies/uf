/*
 * User Fetcher 
 * org.ashtray.uf.viewmodel 
 * 
 * Created on 7/04/20 12:16 PM.
 */
package org.ashtray.uf.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.ash.test.uf.dao.UserDatabase
import org.ash.test.uf.repository.UserRepository
import org.ash.test.uf.util.Users


class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = UserDatabase.getDatabase(application).userDao()

    private val repository = UserRepository(userDao)

    fun usersByGender(gender: String?) = repository.getUserByGender(gender)

    suspend fun dataCount() = repository.dataCount()

    fun insert(user: Users) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(user)
    }

    fun clear() = viewModelScope.launch(Dispatchers.IO) {
        repository.clear()
    }
}