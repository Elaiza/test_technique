package ll.test_technique

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import ll.test_technique.API.UsersApiClient
import ll.test_technique.Custom_Adapter.User_Adapter
import ll.test_technique.DataObject.Utilisateur
import android.support.v7.widget.DividerItemDecoration




/**
 * <b>  MainActivity </b>
 * Activité principale qui liste les utilisateurs et au tap sur un utilisateur, afficher ses albums et ses posts
 * Created by Lueng on 22/03/2018.
 */
class MainActivity : AppCompatActivity() {
    //-------------------------------------------------------------------------------------------------
    // Initialisation des variables
    //-------------------------------------------------------------------------------------------------
    val client by lazy {
        UsersApiClient.create()
    }

    var disposable: Disposable? = null

    //-------------------------------------------------------------------------------------------------
    // Fonctions
    //-------------------------------------------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Affiche les utilisateurs
        showUsers()
    }

    /* Gère l'affichage des utilisateurs dans le recycler*/
    fun setupRecycler(userList: List<Utilisateur>) {
            recyclerview_user.setHasFixedSize(true)
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            recyclerview_user.layoutManager = layoutManager
            recyclerview_user.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            recyclerview_user.adapter = User_Adapter(userList) {
                Log.v("Utilisateur", it.id.toString())
            }
    }

    /*Obtient la liste des utilisateurs et l'affiche*/
    private fun showUsers() {

        disposable = client.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> setupRecycler(result) },
                        { error -> Log.e("ERROR", error.message) }
                )

    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

}
