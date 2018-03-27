package ll.test_technique


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail__user.*
import ll.test_technique.API.AlbumsApiClient
import ll.test_technique.API.ArticlesApiClient
import ll.test_technique.Custom_Adapter.AlbumAdapter
import ll.test_technique.Custom_Adapter.ArticleAdapter
import ll.test_technique.Custom_Adapter.User_Adapter
import ll.test_technique.DataObject.Album
import ll.test_technique.DataObject.Article

/**
 * <b>  Detail_User </b>
 * permet dans l'activité detail_user d'afficher les albums et les posts d'un user.
 * Donne la posibilité de rajouter un commentaire en cliquant sur le bouton commenter.
 * Donne également la possibilité de visualiser les photos d'un album en cliquant sur son titre.
 * Created by Lueng on 27/03/2018.
 */
class Detail_User : AppCompatActivity() {
    //-------------------------------------------------------------------------------------------------
    // Initialisation des variables
    //-------------------------------------------------------------------------------------------------
    val client_album by lazy {
        AlbumsApiClient.create()
    }

    var disposable_album: Disposable? = null

    val client_article by lazy {
        ArticlesApiClient.create()
    }

    var disposable_article: Disposable? = null

    //-------------------------------------------------------------------------------------------------
    // Fonctions
    //-------------------------------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail__user)

        // Récupère les valeurs transmises par User_Adapter
        val nav_bar_title = intent.getStringExtra(User_Adapter.CustomViewHolder.NAME_USER_KEY)
        val id_user = intent.getIntExtra(User_Adapter.CustomViewHolder.ID_USER_KEY,-1)
        val email_user =intent.getStringExtra(User_Adapter.CustomViewHolder.EMAIL_USER_KEY)

        supportActionBar?.title = nav_bar_title
        // Affiche les albums liés au user
        showAlbum_User(id_user)
        // Affiche les articles liés au user
        showArticle_User(id_user,email_user)
    }

    /* Gère l'affichage des albums dans le recycler*/
    fun setupRecyclerAlbum(albumList: List<Album>) {
        recyclerview_Album.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerview_Album.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerview_Album.layoutManager = layoutManager
        recyclerview_Album.adapter = AlbumAdapter(albumList) {
            Log.v("Album", it.id.toString())
        }

    }


    /*Obtient la liste des albums liés à un identifiant de user et l'affiche*/
    private fun showAlbum_User(userId: Int) {
        disposable_album = client_album.getAlbums_user(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> setupRecyclerAlbum(result) },
                        { error -> Log.e("ERROR", error.message) }
                )
    }

    /* Gère l'affichage des articles dans le recycler*/
    fun setupRecyclerArticle(articleList: List<Article>,email:String) {
     recyclerview_Article.setHasFixedSize(true)
     val layoutManager = LinearLayoutManager(this)
     layoutManager.orientation = LinearLayoutManager.VERTICAL
    recyclerview_Article.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    recyclerview_Article.layoutManager = layoutManager
    recyclerview_Article.adapter = ArticleAdapter(articleList,email) {
         Log.v("Article", it.id.toString())
     }

}

    /*Obtient la liste des articles liés à un identifiant de user et l'affiche*/
    private fun showArticle_User(userId: Int,email:String) {

        disposable_article = client_article.getArticle_user(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> setupRecyclerArticle(result,email) },
                        { error -> Log.e("ERROR", error.message) }
                )

    }

    override fun onPause() {
    super.onPause()
    disposable_article?.dispose()
    disposable_album?.dispose()
}

}
