package org.ash.test.uf.api

import org.ash.test.uf.util.Constants
import org.ash.test.uf.util.Logger
import retrofit2.Retrofit

class ServiceCreater {

    companion object {

        fun <T> createService(cls: Class<T>): T {
            Logger.L("Create remote service: ${cls.simpleName}.")
            return Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .build()
                .create(cls)
        }
    }
}
