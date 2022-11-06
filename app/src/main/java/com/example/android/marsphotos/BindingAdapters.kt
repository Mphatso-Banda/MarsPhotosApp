package com.example.android.marsphotos

import PhotoGridAdapter
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.android.marsphotos.network.MarsPhoto
import com.example.android.marsphotos.overview.MarsApiStatus

//The @BindingAdapter annotation tells data binding to execute this binding adapter when a
// View item has the imageUrl attribute
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?){

//    let is one of Kotlin's Scope functions which lets you execute a code block within the context
//    of an object
    /**
     * Inside the let{} block, add the following line to convert the URL string to a Uri object
     * using the toUri() method. To use the HTTPS scheme, append buildUpon.scheme("https") to the
     * toUri builder. Call build() to build the object.
     * */
// this is going to execute only if imgUrl is not empty
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
//         use the load(){} from Coil, to load the image from the imgUri object into the imgView
        imgView.load(imgUri){
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }

}

/**
 * use a BindingAdapter to initialize the PhotoGridAdapter with the list of MarsPhoto objects.
 * Using a BindingAdapter to set the RecyclerView data causes data binding to automatically observe
 * the LiveData for the list of MarsPhoto objects. Then the binding adapter is called automatically
 * when the MarsPhoto list changes.*/

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     data: List<MarsPhoto>?) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
//    call adapter.submitList() with the Mars photos list data. This tells the RecyclerView when a
//    new list is available.
    adapter.submitList(data)
}
/**
 * To connect everything together, open res/layout/fragment_overview.xml. Add the app:listData
 * attribute to the RecyclerView element and set it to viewmodel.photos using data binding.
 * This is similar to what you have done for ImageView in a previous task.*/


/**
 * Add a new binding adapter called bindStatus() that takes an ImageView and a MarsApiStatus value
 * as arguments, for the status Image view*/
@BindingAdapter("marsApiStatus")
fun bindStatus(statusImageView: ImageView,
               status: MarsApiStatus?) {

//    Add a when {} block inside the bindStatus() method to switch between the different statuses
    when (status) {
        MarsApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MarsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
//        Here you have a successful response, so set the visibility of the status ImageView
    //        to View.GONE to hide it.
        MarsApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}