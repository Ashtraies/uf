package org.ash.test.uf.repository

import androidx.paging.Config
import androidx.paging.toLiveData
import org.ash.test.uf.dao.UserDao
import org.ash.test.uf.util.Logger
import org.ash.test.uf.util.PagedUsers
import org.ash.test.uf.util.Users

class UserRepository(private val userDao: UserDao) {

    fun getUserByGender(gender: String?): PagedUsers {
        return userDao.getPagedUsersByGender(gender).toLiveData(
            Config(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 200
            )
        )
    }

    fun getUserById(id : Int) = userDao.getUserById(id)

    suspend fun dataCount() = userDao.dataCount()

    suspend fun insert(user: Users) {
        Logger.L("Insert ${user.size} users on ${Thread.currentThread().name}")
        userDao.insert(user)
    }

    suspend fun clear() {
        Logger.L("Clear database on ${Thread.currentThread().name}")
        userDao.clear()
    }
}