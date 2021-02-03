package com.cacagdas.itunessearchapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.cacagdas.itunessearchapp.AppExecutors
import com.cacagdas.itunessearchapp.R
import com.cacagdas.itunessearchapp.binding.FragmentDataBindingComponent
import com.cacagdas.itunessearchapp.util.autoCleared
import com.cacagdas.itunessearchapp.MainActivity
import com.cacagdas.itunessearchapp.databinding.DetailFragmentBinding
import com.cacagdas.itunessearchapp.di.Injectable
import com.cacagdas.itunessearchapp.vo.ITunesItem
import javax.inject.Inject

class DetailFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val detailViewModel: DetailViewModel by viewModels {
        viewModelFactory
    }

    @Inject
    lateinit var appExecutors: AppExecutors

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<DetailFragmentBinding>()
    private val params by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.detail_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.item = ITunesItem(0, params.title, params.imageUrl, params.releaseDate)
        (activity as MainActivity).supportActionBar?.title =
                if (!params.title.isNullOrEmpty()) params.title
                else getString(R.string.detail_fragment_name)
    }
}
