package ll.test_technique.Custom_Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.model_list_commentaire.view.*
import ll.test_technique.DataObject.Commentaire
import ll.test_technique.R

/**
 * <b> CommentaireAdapter </b>
 * gére l'affichage des commentaire dans le recyclerview_commentaire
 * Created by Lueng on 22/03/2018.
 */
class Commentaire_Adapter(
        private val list_commmentaires: List<Commentaire>,
        private val listener: (Commentaire) -> Unit
    ): RecyclerView.Adapter<Commentaire_Adapter.CustomViewHolder>() {

    /*Nombre de commentaire dans la liste*/
    override fun getItemCount(): Int {
        return list_commmentaires.size
    }

    /*Gestion du style de chaque item de la liste*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= CustomViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.model_list_commentaire, parent, false))

    /*Gestion pour changer les valeurs*/
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val commentaire=list_commmentaires[position]
        holder.bind(commentaire, listener)
        holder.commentaire=commentaire
    }

    class CustomViewHolder(view: View, var commentaire: Commentaire?=null) : RecyclerView.ViewHolder(view) {
        fun bind(commentaire: Commentaire, listener: (Commentaire) -> Unit) = with(itemView) {
            textView_Commentaire_name.text = commentaire.name
            textView_Commentaire_body.text = commentaire.body
        }

    }


}