package org.ash.test.uf.util

import okhttp3.ResponseBody
import org.ash.test.uf.api.GetUserService
import org.ash.test.uf.api.ResponseHandler
import org.ash.test.uf.api.ServiceCreater.Companion.createService
import org.ash.test.uf.model.User
import org.ash.test.uf.util.Logger.Companion.L
import org.json.JSONObject

class UserGetter {

    fun get(resultNum: Int, processor: (List<User>) -> Unit) {
        L("Try to get $resultNum users.")
        val handler = ResponseHandler(this::convert, processor)
        createService(GetUserService::class.java).getUsers(
            resultNum
        ).enqueue(handler)
    }

    private fun convert(body: ResponseBody): List<User> {
        val responseString: String = body.string()
        val json = JSONObject(responseString)
        val list = json.getJSONArray("results")

        val size = list.length()
        val resultList = ArrayList<User>(size)
        L("Result list size: $size")
        for (index in 0 until size) {
            resultList.add(convert(list.getJSONObject(index)))
        }
        return resultList
    }

    private fun convert(json: JSONObject): User {
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
}