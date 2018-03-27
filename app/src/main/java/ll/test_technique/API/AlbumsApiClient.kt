package ll.test_technique.API

import io.reactivex.Observable
import ll.test_technique.BASE_URL
import ll.test_technique.DataObject.Album
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 * <b> AlbumsApiClient </b>
 * récupère les données des albums situées sur le serveur
 * Created by Lueng on 22/03/2018.
 */
interface AlbumsApiClient {

    /* Obtient la liste des albums*/
    @GET("albums")
    fun getAlbums(): Observable<List<Album>>

    /* Obtient un album en fonction de son identifiant*/
    @GET("albums/{id}")
    fun getAlbum(@Path("id") id: Int): Observable<Album>

    /* Obtient un album en fonction de l'identifiant de l'utilisateur*/
    @GET("albums/")
    fun getAlbums_user(@Query("userId") userId: Int): Observable<List<Album>>

    companion object {
        /*Connecte à la base de données*/
        fun create(): AlbumsApiClient {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
            return retrofit.create(AlbumsApiClient::class.java)
        }
    }
}