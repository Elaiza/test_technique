package ll.test_technique.Custom_Adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.model_list_user.view.*
import ll.test_technique.DataObject.Utilisateur
import ll.test_technique.Detail_User
import ll.test_technique.MainActivity
import ll.test_technique.R


/**
 * <b>  User_Adapter </b>
 * gére l'affichage des utilisateurs dans le recyclerview_user
 * Created by Lueng on 22/03/2018.
 */
class User_Adapter(
    private val list_users: List<Utilisateur>,
    private val listener: (Utilisateur) -> Unit
    ): RecyclerView.Adapter<User_Adapter.CustomViewHolder>() {

    /*Nombre d'utilisateur dans la liste*/
    override fun getItemCount(): Int {
        return list_users.size
    }

    /* Gestion du style de chaque item de la liste*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= CustomViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.model_list_user, parent, false))

    /*Gestion pour changer les valeurs*/
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val utilisateur=list_users[position]
        holder.bind(utilisateur, listener)
        holder.utilisateur=utilisateur
    }

    class CustomViewHolder(view: View, var utilisateur:Utilisateur?=null) : RecyclerView.ViewHolder(view) {
        fun bind(utilisateur: Utilisateur, listener: (Utilisateur) -> Unit) = with(itemView) {
            textView_Name_user.text = utilisateur.name
        }
        companion object {
            val NAME_USER_KEY = "Name_user"
            val ID_USER_KEY = "id_user"
            val EMAIL_USER_KEY = "Email_user"
        }
        init {
            //Réagit au clic sur un user
            view.setOnClickListener {
                val intent = Intent(view.context,Detail_User::class.java)
                intent.putExtra(NAME_USER_KEY,utilisateur?.name)
                intent.putExtra(ID_USER_KEY,(utilisateur?.id))
                intent.putExtra(EMAIL_USER_KEY,(utilisateur?.email))
                view.context.startActivity(intent)
            }
        }

    }


}


