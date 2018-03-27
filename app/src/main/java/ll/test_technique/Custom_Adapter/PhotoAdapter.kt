package ll.test_technique.Custom_Adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.model_list_photo.view.*
import ll.test_technique.DataObject.Photo
import ll.test_technique.Detail_photo
import ll.test_technique.R

/**
 * <b>  PhotoAdapter </b>
 * gére l'affichage des photos dans le recycler_view_photo
 * Created by Lueng on 22/03/2018.
 */
class PhotoAdapter(
        private val photoList: List<Photo>,
        private val listener: (Photo) -> Unit
): RecyclerView.Adapter<PhotoAdapter.PhotoHolder>() {

    /*Nombre de photos dans la liste*/
    override fun getItemCount() = photoList.size

    /*Gestion du style de chaque item de la liste*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PhotoHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.model_list_photo, parent, false))

    /*Gestion pour changer les valeurs*/
    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val photo=photoList[position]
        holder.bind(photo, listener)
        holder.photo=photo
        val thumbnailUrl_ImageView = photo.thumbnailUrl
        Picasso.get().load(thumbnailUrl_ImageView).into(holder.photoView.image_view_photo);
    }

    class PhotoHolder(val photoView: View, var photo: Photo?=null): RecyclerView.ViewHolder(photoView) {
        fun bind(photo: Photo, listener: (Photo) -> Unit) = with(itemView) {
        }
        companion object {
            val TITLE_PHOTO_KEY = "Title_Photo"
            val URL_PHOTO_KEY = "URL_Photo"
        }
        init {
            //Réagit au clic sur une photo
            photoView.setOnClickListener {
                val intent = Intent(photoView.context,Detail_photo::class.java)
                intent.putExtra(TITLE_PHOTO_KEY,photo?.title)
                intent.putExtra(URL_PHOTO_KEY,photo?.thumbnailUrl)
                photoView.context.startActivity(intent)
            }
        }
    }
}