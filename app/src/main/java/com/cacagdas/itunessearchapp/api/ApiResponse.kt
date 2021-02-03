package com.cacagdas.itunessearchapp.api

import retrofit2.Response
import timber.log.Timber
import com.cacagdas.itunessearchapp.util.tryCatch
import com.cacagdas.itunessearchapp.vo.ITunesItem

class ApiResponse {

    var code: Int? = 0
    var resultCount: Int? = 0
    var results : List<ITunesItem>? = null

    constructor(error: Throwable) {
        code = 500
        results = null
    }

    constructor(response : Response<List<ITunesItem>>) {
        code = response.code()
        if (response.isSuccessful) {
            results = response.body()!!
        } else {
            var message: String? = null
            if (response.errorBody() != null) {
                tryCatch(tryBlock = {
                    message = response.errorBody()!!.string()
                } , catchBlock = { Timber.e("$it")})
            }

            if (message == null || message?.trim { it <= ' ' }!!.isEmpty()) {
                message = response.message()
            }

            results = null
        }
    }

    constructor(body : List<ITunesItem>){
        results = body
    }
}