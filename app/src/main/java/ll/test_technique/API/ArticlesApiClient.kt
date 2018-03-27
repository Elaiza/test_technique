package ll.test_technique.API

import io.reactivex.Observable
import ll.test_technique.BASE_URL
import ll.test_technique.DataObject.Article
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


/**
 * <b> ArticlesApiClientt </b>
 * récupère les données des posts situées sur le serveur
 * Created by Lueng on 22/03/2018.
 */

interface ArticlesApiClient {

    /* Obtient la liste des articles/posts*/
    @GET("posts")
    fun getArticles(): Observable<List<Article>>


    /* Obtient un article en fonction de son identifiant*/
    @GET("posts/{id}")
    fun getArticle(@Path("id") id: Int): Observable<Article>

    /* Obtient un article en fonction de l'identifiant de l'utilisateur*/
    @GET("posts/")
    fun getArticle_user(@Query("userId") userId: Int): Observable<List<Article>>

    companion object {
        /*Connecte à la base de données*/
        fun create(): ArticlesApiClient {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
            return retrofit.create(ArticlesApiClient::class.java)
        }
    }

}