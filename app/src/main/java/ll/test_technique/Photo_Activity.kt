package ll.test_technique

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_photo_.*
import ll.test_technique.API.ArticlesApiClient
import ll.test_technique.API.PhotosApiClient
import ll.test_technique.Custom_Adapter.AlbumAdapter
import ll.test_technique.Custom_Adapter.PhotoAdapter
import ll.test_technique.DataObject.Photo

/**
 * <b>  Photo_Activity </b>
 * affiche les photos d'un album
 * Created by Lueng on 22/03/2018.
 */
class Photo_Activity : AppCompatActivity() {
    //-------------------------------------------------------------------------------------------------
    // Initialisation des variables
    //-------------------------------------------------------------------------------------------------
    val client by lazy {
        PhotosApiClient.create()
    }

    var disposable: Disposable? = null

    //-------------------------------------------------------------------------------------------------
    // Fonctions
    //-------------------------------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_)

        //Récupère les valeurs transmises par AlbumAdapter
        val id_album = intent.getIntExtra(AlbumAdapter.AlbumHolder.ID_ALBUM_KEY,-1)
        val name_album = intent.getStringExtra(AlbumAdapter.AlbumHolder.TITLE_ALBUM_KEY)

        supportActionBar?.title = name_album

        //Affiche les photos de l'album
        showphotos(id_album)

    }

    /* Gère l'affichage des photos dans le recycler*/
    fun setupRecycler(PhotoList: List<Photo>) {
        recycler_view_photo.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(applicationContext, 4)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recycler_view_photo.layoutManager = layoutManager
        recycler_view_photo.adapter = PhotoAdapter(PhotoList){
            Log.v("Photo", it.id.toString())
        }
    }

    /*Obtient la liste des photos liés à un identifiant d'album et l'affiche*/
    private fun showphotos(id_album:Int) {
        disposable = client.getPhoto_album(id_album)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> setupRecycler(result) },
                        { error -> Log.e("ERROR", error.message) }
                )

    }


    /* Gère le retour arrière*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}
