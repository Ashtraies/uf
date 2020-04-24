package org.ash.test.uf.api

import okhttp3.ResponseBody
import org.ash.test.uf.util.Logger.Companion.L
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResponseHandler<T>(
    val converter: (body: ResponseBody) -> T,
    val processer: (t: T) -> Unit
) : Callback<ResponseBody> {

    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
        L(t.message)
    }

    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        if (response.isSuccessful) {
            L("Request is successful.")
            val body = response.body()
            if (body != null) {
                L("Response body is not null, convert and process.")
                val result = converter(body)
                processer(result)
            } else {
                L("Response body is NULL!")
            }
        } else {
            L("Request error: ${response.errorBody()}")
        }
    }
}