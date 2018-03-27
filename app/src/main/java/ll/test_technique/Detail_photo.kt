package ll.test_technique

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_photo.*
import ll.test_technique.Custom_Adapter.PhotoAdapter

/**
 * <b>  Detail_photo </b>
 * affiche dans l'activité detail_photo la photo qui a été sélectionnée
 * Created by Lueng on 27/03/2018.
 */

class Detail_photo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_photo)

        // Récupère les valeurs transmises par PhotoAdapter
        val title_photo = intent.getStringExtra(PhotoAdapter.PhotoHolder.TITLE_PHOTO_KEY)
        val url_photo =intent.getStringExtra(PhotoAdapter.PhotoHolder.URL_PHOTO_KEY)

        supportActionBar?.title = title_photo

        //Charge l'image
        Picasso.get().load(url_photo).into(imageView_detail_photo);
    }

    /* Gère le retour arrière*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}
