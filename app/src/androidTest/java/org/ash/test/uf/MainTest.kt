package org.ash.test.uf

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.Config
import androidx.paging.toLiveData
import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.ResponseBody
import org.ash.test.uf.api.GetUserService
import org.ash.test.uf.api.ServiceCreater
import org.ash.test.uf.dao.UserDao
import org.ash.test.uf.dao.UserDatabase
import org.json.JSONObject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RunWith(JUnit4::class)
class MainTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: GetUserService
    private lateinit var dao: UserDao

    @Before
    fun setup() {
        service = ServiceCreater.createService(GetUserService::class.java)
        dao = UserDatabase.getDatabase(InstrumentationRegistry.getInstrumentation().targetContext)
            .userDao()
    }

    @Test
    fun test_init() {
        assert(service != null)
        assert(dao != null)
    }

    @Test
    fun test_getUserFromApi() {
        service.getUsers(10).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                TODO("not implemented")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val responseString: String? = response.body()?.string()
                val json = JSONObject(responseString)
                val list = json.getJSONArray("results")

                val size = list.length()
                assert(size == 10)
            }
        })
    }

    fun test_dao() {
        val size = dao.getPagedUsersByGender("male").toLiveData(
            Config(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 200
            )
        ).value?.size
        if (size != null) {
            assert(size > 0)
        }
    }
}