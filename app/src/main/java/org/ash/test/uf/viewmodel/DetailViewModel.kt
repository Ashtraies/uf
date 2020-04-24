package org.ash.test.uf.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import org.ash.test.uf.dao.UserDatabase
import org.ash.test.uf.repository.UserRepository


class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = UserDatabase.getDatabase(application).userDao()

    private val repository = UserRepository(userDao)

    fun getUserById(id: Int) = repository.getUserById(id)
}