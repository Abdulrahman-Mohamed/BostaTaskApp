package com.abdullrahman.bostataskapp.feature_gallery.domain.dataBinding

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdullrahman.bostataskapp.feature_gallery.domain.models.AlbumsItem
import com.abdullrahman.bostataskapp.feature_gallery.domain.models.ImagesItem
import com.abdullrahman.bostataskapp.feature_gallery.domain.models.User
import com.abdullrahman.bostataskapp.feature_gallery.domain.recycler.AlbumsAdapter
import com.abdullrahman.bostataskapp.feature_gallery.domain.recycler.ImagesAdapter
import com.abdullrahman.bostataskapp.feature_gallery.domain.recycler.onCLick.AlbumsOnClick
import com.abdullrahman.bostataskapp.feature_gallery.domain.recycler.onCLick.ImagesOnClick
import com.abdullrahman.bostataskapp.feature_gallery.presentation.gallery.GalleryViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.ortiz.touchview.TouchImageView

@BindingAdapter("cnc_text")
fun ConcatStrings(text: TextView, user: User?) {
    if (user != null) {
        var addres = ""
        addres =
            user.address!!.street + " " + user.address.suite + " " + user.address.city + " " + user.address.zipcode
        text.text = addres

    }
}
@BindingAdapter("search")
fun Search(et: EditText, viewModel: GalleryViewModel) {
    et.addTextChangedListener(object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (p0 != null)
            viewModel.search(p0.toString())
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    })
}
@BindingAdapter("albums_adapter", "albums_listener")
fun setAdapterAlbums(view: RecyclerView, list: MutableList<AlbumsItem?>?, listener: AlbumsOnClick) {
    if (list != null) {
        val layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        view.layoutManager = layoutManager
        view.setHasFixedSize(true)
        view.addItemDecoration(
            DividerItemDecoration(
                view.context,
                layoutManager.orientation
            )
        )
        view.adapter = AlbumsAdapter(list!!, view.context, listener)
    }
}

@BindingAdapter("images_adapter", "images_listener")
fun setAdapterImages(view: RecyclerView, list: MutableList<ImagesItem?>?, listener: ImagesOnClick) {
    if (list != null) {
        val layoutManager = GridLayoutManager(view.context, 3)
        view.layoutManager = layoutManager
        view.setHasFixedSize(true)
        view.adapter = ImagesAdapter(list!!, view.context, listener)
    }
}

@BindingAdapter("photos_src")
fun setImage(imageView: ImageView, url: String) {
    val url =
        GlideUrl(url, LazyHeaders.Builder().addHeader("User-Agent", "your-user-agent").build())

    Glide.with(imageView.context)
        .load(url)
        .fitCenter()
        .into(imageView)
}
@BindingAdapter("text_res")
fun setText(tv: TextView, resource: String?) {
    if (resource !=  null)
        tv.text = resource
}
//@BindingAdapter("touch_src")
//fun setTouch(imageView: TouchImageView, url: String) {
//    val url =
//        GlideUrl(url, LazyHeaders.Builder().addHeader("User-Agent", "your-user-agent").build())
//
//    Glide.with(imageView.context)
//        .load(url)
//        .fitCenter()
//        .into(imageView)
//}

