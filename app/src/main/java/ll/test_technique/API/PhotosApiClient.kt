package ll.test_technique.API

import io.reactivex.Observable
import ll.test_technique.BASE_URL
import ll.test_technique.DataObject.Album
import ll.test_technique.DataObject.Photo
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * <b> PhotosApiClient</b>
 * récupère les données des photos situées sur le serveur
 * Created by Lueng on 22/03/2018.
 */

interface PhotosApiClient {

    /* Obtient la liste des photos*/
    @GET("photos")
    fun getPhotos(): Observable<List<Photo>>

    /* Obtient une photo en fonction de son identifiant*/
    @GET("photos/{id}")
    fun getPhoto(@Path("id") id: Int): Observable<Photo>

    /* Obtient une photo en fonction de l'identifiant de l'album*/
    @GET("photos/")
    fun getPhoto_album(@Query("albumId") albumId: Int): Observable<List<Photo>>

    companion object {
        /*Connecte à la base de données*/
        fun create(): PhotosApiClient {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
            return retrofit.create(PhotosApiClient::class.java)
        }
    }

}