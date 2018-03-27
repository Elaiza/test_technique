package ll.test_technique.API

import io.reactivex.Observable
import ll.test_technique.BASE_URL
import ll.test_technique.DataObject.Commentaire
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import retrofit2.http.POST



/**
 * <b> CommentairesApiClient </b>
 * récupère les données des commentaires situées sur le serveur
 * Created by Lueng on 22/03/2018.
 */

interface CommentairesApiClient {

    /* Obtient la liste des commentaires*/
    @GET("comments")
    fun getComments(): Observable<List<Commentaire>>

    /* Obtient un commentaire en fonction de son identifiant*/
    @GET("comments/{id}")
    fun getComment(@Path("id") id: Int): Observable<Commentaire>

    /* Obtient un commentaire en fonction de l'identifiant d'un post*/
    @GET("comments/")
    fun getComment_post(@Query("postId") postId: Int): Observable<List<Commentaire>>

    /* Ajoute un nouveau commentaire*/
    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("comments")
    fun addComment(@Body commentaire: Commentaire): Observable<Commentaire>


    companion object {
        /*Connecte à la base de données*/
        fun create(): CommentairesApiClient {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
            return retrofit.create(CommentairesApiClient::class.java)
        }
    }

}