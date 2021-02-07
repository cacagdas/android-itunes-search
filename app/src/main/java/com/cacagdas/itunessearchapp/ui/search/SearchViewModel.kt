package com.cacagdas.itunessearchapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.cacagdas.itunessearchapp.api.ITunesService
import com.cacagdas.itunessearchapp.db.ITunesDao
import com.cacagdas.itunessearchapp.testing.OpenForTesting
import com.cacagdas.itunessearchapp.util.enqueue
import com.cacagdas.itunessearchapp.vo.ITunesItem
import com.cacagdas.itunessearchapp.vo.Resource
import kotlinx.coroutines.*
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OpenForTesting
class SearchViewModel @Inject constructor(
        val iTunesService: ITunesService,
        private val iTunesDao: ITunesDao) : ViewModel() {

    private val _query = MutableLiveData<String>()
    val query : LiveData<String> = _query
    val results: MutableLiveData<Resource<List<ITunesItem>>> = MutableLiveData()

    val itemsPagedListLiveData: LiveData<PagedList<ITunesItem>> =
            iTunesDao.itemsById().toLiveData(pageSize = 20)

    fun search(mediaType: String) {
        CoroutineScope(Dispatchers.IO).launch {
            iTunesDao.deleteItems()
        }
        iTunesService
                .search(_query.value!!, mediaType)
                .enqueue {
                    onResponse = {
                        //results.value = Resource.success(it.body()?.results)
                        CoroutineScope(Dispatchers.IO).launch {
                            iTunesDao.insertItems(it.body()?.results!!)
                        }
                    }
                }
    }

    fun setQuery(originalInput: String, mediaType: String) {
        val input = originalInput.toLowerCase(Locale.getDefault()).trim()
        _query.value = input
        search(mediaType)
    }

    fun refresh() {
        _query.value?.let {
            _query.value = it
        }
    }
}
