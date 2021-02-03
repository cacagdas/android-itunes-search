package com.cacagdas.itunessearchapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cacagdas.itunessearchapp.api.ITunesService
import com.cacagdas.itunessearchapp.testing.OpenForTesting
import com.cacagdas.itunessearchapp.util.enqueue
import com.cacagdas.itunessearchapp.vo.ITunesItem
import com.cacagdas.itunessearchapp.vo.Resource
import java.util.Locale
import javax.inject.Inject

@OpenForTesting
class SearchViewModel @Inject constructor(/*@Inject
    lateinit var iTunesService: ITunesService*/val iTunesService: ITunesService) : ViewModel() {

    private val _query = MutableLiveData<String>()
    val query : LiveData<String> = _query
    val results: MutableLiveData<Resource<List<ITunesItem>>> = MutableLiveData()

    fun search() {
        iTunesService
                .search(_query.value!!)
                .enqueue {
                    onResponse = {
                        results.value = Resource.success(it.body()?.results)
                    }
                }
    }

    fun setQuery(originalInput: String) {
        val input = originalInput.toLowerCase(Locale.getDefault()).trim()
        if (input == _query.value) {
            return
        }
        _query.value = input
        search()
    }

    fun refresh() {
        _query.value?.let {
            _query.value = it
        }
    }
}
