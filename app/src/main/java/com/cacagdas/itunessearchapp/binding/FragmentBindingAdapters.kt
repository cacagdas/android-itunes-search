package com.cacagdas.itunessearchapp.binding

import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.cacagdas.itunessearchapp.testing.OpenForTesting
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import javax.inject.Inject

/**
 * Binding adapters that work with a fragment instance.
 */
@OpenForTesting
class FragmentBindingAdapters @Inject constructor(val fragment: Fragment) {
    @BindingAdapter(value = ["imageUrl", "imageRequestListener"], requireAll = false)
    fun bindImage(imageView: ImageView, url: String?, listener: RequestListener<Drawable?>?) {
        Glide.with(fragment).load(url).listener(listener).into(imageView)
    }
}

