package org.ash.test.uf.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.ash.test.uf.model.User
import org.ash.test.uf.util.Constants
import org.ash.test.uf.util.Users

@Dao
interface UserDao {

    @Query("SELECT * FROM ${Constants.USER_TABLE} WHERE ID = :id")
    fun getUserById(id : Int) : LiveData<User?>

    @Query("SELECT * FROM ${Constants.USER_TABLE} WHERE GENEDER = :gender")
    fun getPagedUsersByGender(gender: String?): DataSource.Factory<Int, User>

    @Query("SELECT count(*) FROM ${Constants.USER_TABLE}")
    suspend fun dataCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: Users)

    @Query("DELETE FROM ${Constants.USER_TABLE}")
    suspend fun clear()
}