package ll.test_technique.API

import io.reactivex.Observable
import ll.test_technique.BASE_URL
import ll.test_technique.DataObject.Article
import ll.test_technique.DataObject.Utilisateur
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 * <b> UsersApiClient </b>
 * récupère les données des users situées sur le serveur
 * Created by Lueng on 22/03/2018.
 */

interface UsersApiClient {

    /* Obtient la liste des users*/
    @GET("users")
    fun getUsers(): Observable<List<Utilisateur>>

    companion object {
        /*Connecte à la base de données*/
        fun create(): UsersApiClient {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()

            return retrofit.create(UsersApiClient::class.java)
        }
    }

}