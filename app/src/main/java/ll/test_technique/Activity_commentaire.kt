package ll.test_technique

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_commentaire.*
import ll.test_technique.API.CommentairesApiClient
import ll.test_technique.Custom_Adapter.ArticleAdapter
import ll.test_technique.Custom_Adapter.Commentaire_Adapter
import ll.test_technique.DataObject.Commentaire
import android.text.Editable
import android.text.TextWatcher
import android.view.View

/**
 * <b>  Activity_commentaire </b>
 * permet dans l'activité commentaire d'afficher les commentaires
 * en rapport avec le post et de poster un nouveau commentaire
 * Created by Lueng on 27/03/2018.
 */
class Activity_commentaire : AppCompatActivity() {
    //-------------------------------------------------------------------------------------------------
    // Initialisation des variables
    //-------------------------------------------------------------------------------------------------

    val client by lazy {
        CommentairesApiClient.create()
    }

    var disposable: Disposable? = null

    public var nb_c : Int = 0

    //-------------------------------------------------------------------------------------------------
    // Fonctions
    //-------------------------------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commentaire)

        // Récupère les valeurs transmises par ArticleAdapter
        val id_post = intent.getIntExtra(ArticleAdapter.ArticleHolder.ID_POST_KEY,-1)
        val email_user = intent.getStringExtra(ArticleAdapter.ArticleHolder.EMAIL_KEY)

        //Appel de la fonction affichant les commentaires en rapport avec le post sélectionné
        showCommentaires_post(id_post)

        // Récupère le text tapé par l'utilisateur
        val body_comment = editText_comment.getText().toString()
        btn_publier.setEnabled(false) //

        // Si le champs de l'edit text n'est pas vide => donne l'accès au bouton
        editText_comment.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                if (s.toString().trim { it <= ' ' }.length == 0) {
                    btn_publier.setEnabled(false)
                } else {
                    btn_publier.setEnabled(true)
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        sizeCommentaire()

        //Réagit au tap sur le bouton
        btn_publier.setOnClickListener(View.OnClickListener(){
            //determine le nombre de commentaire présent dans la base
            val comment = Commentaire(id_post,nb_c + 1 , "Commentaire" , email_user, body_comment)
            // post le commentaire
            //A noter : id = nb_c+1
            // En considérant que id est identique au nombre de commentaire et en sachant que l'application ne supprime pas de commentaire
            postCommentaire(comment)
            //Réactualise la liste de commentaire
            showCommentaires_post(id_post)
            btn_publier.visibility=View.GONE
            editText_comment.visibility=View.GONE
        })

    }

    /* Gère l'affichage des commenatires dans le recycler*/
    fun setupRecycler(CommentaireList: List<Commentaire>) {
        recyclerview_commentaire.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerview_commentaire.layoutManager = layoutManager
        recyclerview_commentaire.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerview_commentaire.adapter = Commentaire_Adapter(CommentaireList){
            Log.v("Commentaire", it.id.toString())
        }
    }

    /*Obtient la liste des commentaires liés à un identifiant de post et l'affiche*/
    private fun showCommentaires_post(PostId: Int) {
        disposable = client.getComment_post(PostId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> setupRecycler(result) },
                        { error -> Log.e("ERROR", error.message) }
                )

    }

    /*Permet de poster un nouveau commentaire*/
    private fun postCommentaire(commentaire: Commentaire) {
        disposable = client.addComment(commentaire)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> Log.v("POST COMMENTAIRE", "" + commentaire )
                            Toast.makeText(this, R.string.post_comment, Toast.LENGTH_LONG).show()},
                        { error -> Log.e("ERROR", error.message )
                                Toast.makeText(this, R.string.post_comment_error, Toast.LENGTH_LONG).show()}               )
    }


    /* Récupère le nombre de commentaires dans la base de données */
    private fun sizeCommentaire() {
        disposable = client.getComments()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> nb_c = result.size},
                        { error -> Log.e("ERROR", error.message )}               )
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
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
