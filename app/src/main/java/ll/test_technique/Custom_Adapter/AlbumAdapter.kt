package ll.test_technique.Custom_Adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.model_list_album.view.*
import ll.test_technique.DataObject.Album
import ll.test_technique.Photo_Activity
import ll.test_technique.R

/**
 * <b> AlbumAdapter </b>
 * gére l'affichage des albums dans le recyclerview_Album
 * Created by Lueng on 22/03/2018.
 */
class AlbumAdapter(
        private val albumList: List<Album>,
        private val listener: (Album) -> Unit
): RecyclerView.Adapter<AlbumAdapter.AlbumHolder>() {

    /*Nombre d'album dans la liste*/
    override fun getItemCount() = albumList.size

    /* Gestion du style de chaque item de la liste*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AlbumHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.model_list_album, parent, false))

    /*Gestion pour changer les valeurs*/
    override fun onBindViewHolder(holder: AlbumHolder, position: Int) {
        holder.bind(albumList[position], listener)
        val album=albumList[position]
        holder.bind(album, listener)
        holder.album=album
    }

    class AlbumHolder(val albumView: View, var album: Album?=null): RecyclerView.ViewHolder(albumView) {

        fun bind(album: Album, listener: (Album) -> Unit) = with(itemView) {
            textView_titre_album.text = album.title
        }

        companion object {
            val ID_ALBUM_KEY = "id_album"
            val TITLE_ALBUM_KEY="title_album"
        }

        init {
               //Réagit au clic sur le titre d'un album
                albumView.setOnClickListener {
                    val intent = Intent(albumView.context, Photo_Activity::class.java)
                    intent.putExtra(ID_ALBUM_KEY,album?.id)
                    intent.putExtra(TITLE_ALBUM_KEY,album?.title)
                    albumView.context.startActivity(intent)
            }
        }
    }


}