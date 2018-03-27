package ll.test_technique.Custom_Adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.model_list_article.view.*
import ll.test_technique.Activity_commentaire
import ll.test_technique.DataObject.Article
import ll.test_technique.R

/**
 * <b> ArticleAdapter </b>
 * gére l'affichage des articles dans le recyclerview_Article
 * Created by Lueng on 22/03/2018.
 */
class ArticleAdapter(
        private val articleList: List<Article>,
        private val email:String,
        private val listener: (Article) -> Unit
): RecyclerView.Adapter<ArticleAdapter.ArticleHolder>() {

    /*Nombre d'article dans la liste*/
    override fun getItemCount() = articleList.size

    /* Gestion du style de chaque item de la liste*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ArticleHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.model_list_article, parent, false))

    /*Gestion pour changer les valeurs*/
    override fun onBindViewHolder(holder: ArticleHolder, position: Int) = holder.bind(articleList[position],email, listener)

    class ArticleHolder(articleView: View): RecyclerView.ViewHolder(articleView) {

        companion object {
            val ID_POST_KEY = "id_post"
            val EMAIL_KEY = "email_user_post"
        }

        fun bind(article: Article, email: String, listener: (Article) -> Unit) = with(itemView) {
            textView_titre_article.text = article.title
            textView_body_article.text=article.body

            /*Réagit au clic sur le bouton commenter*/
            Button_add_comment.setOnClickListener({
                val intent = Intent(Button_add_comment.context, Activity_commentaire::class.java)
                val id_article = article?.id
                intent.putExtra(ID_POST_KEY,(id_article))
                intent.putExtra(EMAIL_KEY,(email))
                Button_add_comment.context.startActivity(intent)
            })
        }


    }
}
