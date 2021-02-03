package com.cacagdas.itunessearchapp.ui.common

import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.cacagdas.itunessearchapp.AppExecutors
import com.cacagdas.itunessearchapp.R
import com.cacagdas.itunessearchapp.databinding.ItunesItemBinding
import com.cacagdas.itunessearchapp.vo.ITunesItem

/**
 * A RecyclerView adapter for [ITunesItem] class.
 */
class MovieListAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val movieClickCallback: ((ITunesItem) -> Unit)?
) : DataBoundListAdapter<ITunesItem, ItunesItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<ITunesItem>() {
        override fun areItemsTheSame(oldItem: ITunesItem, newItem: ITunesItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ITunesItem, newItem: ITunesItem): Boolean {
            return oldItem.title == newItem.title
        }
    }
) {

    override fun createBinding(parent: ViewGroup): ItunesItemBinding {
        val binding = DataBindingUtil.inflate<ItunesItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.itunes_item,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.item?.let {
                movieClickCallback?.invoke(it)
            }
        }

        return binding
    }

    override fun bind(binding: ItunesItemBinding, item: ITunesItem) {
        binding.item = item
        //binding.posterImage.loadImage(item.posterPath, getProgressDrawable(binding.root.context))
    }
}
