package org.ash.test.uf.repository

import androidx.paging.Config
import androidx.paging.toLiveData
import okhttp3.ResponseBody
import org.ash.test.uf.api.GetUserService
import org.ash.test.uf.api.ResponseHandler
import org.ash.test.uf.api.ServiceCreater
import org.ash.test.uf.dao.UserDao
import org.ash.test.uf.model.User
import org.ash.test.uf.util.Logger
import org.ash.test.uf.util.PagedUsers
import org.ash.test.uf.util.Users
import org.json.JSONObject

class UserRepository(private val userDao: UserDao) {

    fun getUserByGender(gender: String?): PagedUsers {
        Logger.L("Query gender: $gender")
        return userDao.getPagedUsersByGender(gender).toLiveData(
            Config(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 200
            )
        )
    }

    fun getUserFromApi(resultNum: Int, processor: (List<User>) -> Unit) {
        Logger.L("Try to get $resultNum users.")
        val handler = ResponseHandler(::convert, processor)
        ServiceCreater.createService(GetUserService::class.java).getUsers(
            resultNum
        ).enqueue(handler)
    }

    private fun convert(body: ResponseBody): Users {
        val responseString: String = body.string()
        val json = JSONObject(responseString)
        val list = json.getJSONArray("results")

        val size = list.length()
        val resultList = ArrayList<User>(size)
        Logger.L("Result list size: $size")
        for (index in 0 until size) {
            resultList.add(convertSingle(list.getJSONObject(index)))
        }
        return resultList
    }

    private fun convertSingle(json: JSONObject): User {
        val picture = json.getJSONObject("picture")
        val thumbnail = picture.getString("thumbnail")
        val large = picture.getString("large")

        val name = json.getJSONObject("name")
        val title = name.getString("title")
        val first = name.getString("first")
        val last = name.getString("last")

        val gender = json.getString("gender")

        val dob = json.getJSONObject("dob").getString("date")

        val location = json.getJSONObject("location")
        val country = location.getString("country")
        val state = location.getString("state")
        val city = location.getString("city")
        val street = location.getJSONObject("street")
        val streetName = street.getString("name")
        val number = street.getInt("number")
        val postcode = location.getString("postcode")

        val email = json.getString("email")
        val cell = json.getString("cell")

        return User(
            null,
            thumbnail,
            title,
            first,
            last,
            gender,
            dob,
            large,
            country,
            state,
            city,
            streetName,
            number,
            postcode,
            email,
            cell
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