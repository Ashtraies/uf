/*
 * User Fetcher 
 * org.ashtray.uf.viewmodel 
 * 
 * Created on 13/04/20 10:49 AM.
 */
package org.ashtray.uf.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import org.ash.test.uf.dao.UserDatabase
import org.ash.test.uf.repository.UserRepository


class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = UserDatabase.getDatabase(application).userDao()

    private val repository = UserRepository(userDao)

    fun getUserById(id: Int) = repository.getUserById(id)
}