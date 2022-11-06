import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsphotos.databinding.GridViewItemBinding
import com.example.android.marsphotos.network.MarsPhoto

class PhotoGridAdapter : ListAdapter<MarsPhoto,
        PhotoGridAdapter.MarsPhotoViewHolder>(DiffCallback) {

    /**
     * You need the GridViewItemBinding variable for binding the MarsPhoto to the layout
     *  The base ViewHolder class requires a view in its constructor, you pass it the binding root view
     * */
    class MarsPhotoViewHolder(private var binding:
                              GridViewItemBinding
    ):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(MarsPhoto: MarsPhoto) {
            binding.photo = MarsPhoto
//            executePendingBindings() causes the update to execute immediately
            binding.executePendingBindings()
        }
    }

    /**
     * onCreateViewHolder() method needs to return a new MarsPhotoViewHolder, created by inflating
     * the GridViewItemBinding and using the LayoutInflater from your parent ViewGroup context
     * */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoGridAdapter.MarsPhotoViewHolder {
        return MarsPhotoViewHolder(GridViewItemBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    /**
     * call getItem() to get the MarsPhoto object associated with the current RecyclerView position,
     * and then pass that property to the bind() method in the MarsPhotoViewHolder
     * */
    override fun onBindViewHolder(holder: PhotoGridAdapter.MarsPhotoViewHolder, position: Int) {
        val marsPhoto = getItem(position)
        holder.bind(marsPhoto)
    }

    /**
     *You will compare two Mars photo objects inside this implementation
     **/
    companion object DiffCallback : DiffUtil.ItemCallback<MarsPhoto>() {
        /**
         *  This method is called by DiffUtil to decide whether two objects represent the same Item.
         *  DiffUtil uses this method to figure out if the new MarsPhoto object is the same as the
         *  old MarsPhoto object. The ID of every item(MarsPhoto object) is unique. Compare the IDs
         *  of oldItem and newItem, and return the result*/
        override fun areItemsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {
                return oldItem.id == newItem.id
        }

        /**
         * This method is called by DiffUtil when it wants to check whether two items have the same
         * data. The important data in the MarsPhoto is the image URL. Compare the URLs of oldItem
         * and newItem, and return the result.*/
        override fun areContentsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {
                return oldItem.imgSrcUrl == newItem.imgSrcUrl
        }
    }
}