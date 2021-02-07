package com.cacagdas.itunessearchapp.ui.common

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.cacagdas.itunessearchapp.AppExecutors
import com.cacagdas.itunessearchapp.R
import com.cacagdas.itunessearchapp.databinding.ItunesItemBinding
import com.cacagdas.itunessearchapp.vo.ITunesItem
import com.cacagdas.itunessearchapp.util.formatDate

/**
 * A RecyclerView adapter for [ITunesItem] class.
 */
class ITunesItemListAdapter(
        private val dataBindingComponent: DataBindingComponent,
        appExecutors: AppExecutors,
        private val itemClickCallback: ((ITunesItem) -> Unit)?
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
                itemClickCallback?.invoke(it)
            }
        }

        return binding
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun bind(binding: ItunesItemBinding, item: ITunesItem) {
        binding.releaseDate = formatDate(item.releaseDate)
        binding.item = item
    }
}
