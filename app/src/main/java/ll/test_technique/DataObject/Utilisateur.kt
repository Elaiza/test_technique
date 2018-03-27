package ll.test_technique.DataObject
/**
 * <b> Utilisateur </b>
 *
 * Created by Lueng on 22/03/2018.
 */
data class Utilisateur(
        val id: Int,
        val name: String,
        val username: String,
        val email: String,
        val address: Adresse,
        val phone: String,
        val website: String,
        val company: Compagny
)

/*Adresse*/
data class Adresse(
        val street : String,
        val suite : String,
        val city : String,
        val zipcode : String,
        val geo : Geo
)

/*Localisation*/
data class Geo(
        val lat: String,
        val lng: String
)

/*Compagny*/
data class Compagny(
        val name : String,
        val catchPhrase : String,
        val bs : String
)
