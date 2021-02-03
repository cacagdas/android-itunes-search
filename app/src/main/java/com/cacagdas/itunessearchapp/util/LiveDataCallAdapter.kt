package com.cacagdas.itunessearchapp.util

import androidx.lifecycle.LiveData
import com.cacagdas.itunessearchapp.api.ApiResponse
import com.cacagdas.itunessearchapp.vo.ITunesItem
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A Retrofit adapter that converts the Call into a LiveData of com.cacagdas.itunessearchapp.api.ApiResponse.
 * @param <R>
</R> */
class LiveDataCallAdapter(private val responseType: Type) :
    CallAdapter<List<ITunesItem>, LiveData<ApiResponse>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<List<ITunesItem>>): LiveData<ApiResponse> {
        return object : LiveData<ApiResponse>() {
            private var started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<List<ITunesItem>> {
                        override fun onResponse(call: Call<List<ITunesItem>>, response: Response<List<ITunesItem>>) {
                            postValue(ApiResponse(response))
                        }

                        override fun onFailure(call: Call<List<ITunesItem>>, throwable: Throwable) {
                            postValue(ApiResponse(throwable))
                        }
                    })
                }
            }
        }
    }
}
