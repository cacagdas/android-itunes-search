package com.cacagdas.itunessearchapp.ui.search

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.cacagdas.itunessearchapp.AppExecutors
import com.cacagdas.itunessearchapp.R
import com.cacagdas.itunessearchapp.binding.FragmentDataBindingComponent
import com.cacagdas.itunessearchapp.databinding.SearchFragmentBinding
import com.cacagdas.itunessearchapp.di.Injectable
import com.cacagdas.itunessearchapp.ui.common.ITunesItemListAdapter
import com.cacagdas.itunessearchapp.ui.common.RetryCallback
import com.cacagdas.itunessearchapp.util.autoCleared
import com.cacagdas.itunessearchapp.MainActivity
import com.cacagdas.itunessearchapp.api.ITunesService
import com.cacagdas.itunessearchapp.api.MediaType
import com.cacagdas.itunessearchapp.db.ITunesDao
import com.cacagdas.itunessearchapp.vo.ITunesItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var iTunesDao: ITunesDao

    private var mediaType: MediaType = MediaType.All
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<SearchFragmentBinding>()
    var adapter by autoCleared<ITunesItemListAdapter>()
    val searchViewModel: SearchViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.search_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.app_name)
        binding.lifecycleOwner = viewLifecycleOwner
        initRecyclerView()
        val rvAdapter = ITunesItemListAdapter(
                dataBindingComponent = dataBindingComponent,
                appExecutors = appExecutors,
        ) { item ->
            findNavController().navigate(
                    SearchFragmentDirections.showMovie(item.title, item.posterPath!!, item.releaseDate!!)
            )
        }

        deleteAllItemsFromDb()

        binding.query = searchViewModel.query
        binding.movieList.adapter = rvAdapter
        adapter = rvAdapter

        initSearchInputListener()
        initRadioGroupListener()

        binding.callback = object : RetryCallback {
            override fun retry() {
                searchViewModel.refresh()
            }
        }
    }

    private fun initRadioGroupListener() {
        binding.radioGroup.setOnCheckedChangeListener { _, i ->
            setMediaType(i)
        }
    }
    private fun setMediaType(i: Int) {
        mediaType = when (i) {
            MediaType.Movies.id -> MediaType.Movies
            MediaType.Music.id -> MediaType.Music
            MediaType.Apps.id -> MediaType.Apps
            MediaType.Books.id -> MediaType.Books
            else -> MediaType.All
        }
        if (binding.input.text.length > 2)
            doSearch()
    }

    private fun initSearchInputListener() {
        binding.input.setOnEditorActionListener { view: View, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearchDismissKeyboard(view)
                true
            } else {
                false
            }
        }
        binding.input.setOnKeyListener { view: View, keyCode: Int, event: KeyEvent ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                doSearchDismissKeyboard(view)
                true
            } else {
                false
            }
        }
        binding.input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count >= 2)
                    doSearch()
            }
        })
    }

    private fun doSearchDismissKeyboard(v: View) {
        val query = binding.input.text.toString()
        // Dismiss keyboard
        dismissKeyboard(v.windowToken)
        searchViewModel.setQuery(query, mediaType.queryString)
    }

    private fun doSearch() {
        deleteAllItemsFromDb()
        val query = binding.input.text.toString()
        searchViewModel.setQuery(query, mediaType.queryString)
    }

    private fun initRecyclerView() {
        binding.movieList.layoutManager = GridLayoutManager(context, 2)
        binding.searchResult = searchViewModel.results
        searchViewModel.itemsPagedListLiveData.observe(viewLifecycleOwner, Observer { result ->
            adapter.submitList(result)
        })
        //searchViewModel.itemsPagedListLiveData.observe(viewLifecycleOwner, PagedList(adapter::submitList))
    }

    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun deleteAllItemsFromDb() {
        CoroutineScope(Dispatchers.IO).launch {
            iTunesDao.deleteItems()
        }
    }
}
