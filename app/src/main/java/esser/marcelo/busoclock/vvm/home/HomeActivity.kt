package esser.marcelo.busoclock.vvm.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.box.Bus
import esser.marcelo.busoclock.service.vicasaServices.VicasaService
import esser.marcelo.busoclock.vvm.favorite.FavoriteActivity
import esser.marcelo.busoclock.vvm.sogal.lines.SogalLinesActivity
import esser.marcelo.busoclock.vvm.vicasa.lines.VicasaLinesActivity
import kotlinx.android.synthetic.main.activity_home.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class HomeActivity : AppCompatActivity() {

    val updateScreen = 1001

    private val activityContext by lazy {
        this@HomeActivity
    }

    private val vicasaString: VicasaService by lazy {
        VicasaService()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        cv_home_activity_vicasa.setOnClickListener {
            startActivityForResult(Intent(activityContext, VicasaLinesActivity::class.java), updateScreen)
        }

        cv_home_activity_sogal.setOnClickListener {
            startActivityForResult(Intent(activityContext, SogalLinesActivity::class.java), updateScreen)
        }

        cv_home_activity_favorities.setOnClickListener {
            startActivityForResult(Intent(activityContext, FavoriteActivity::class.java), updateScreen)
        }

        loadFavorites()
    }

    private fun loadFavorites () {

        val favoritesSize = Bus.getLines().size
        if (favoritesSize > 0) {
            tv_favorite_count.text = "$favoritesSize Linha(s) salva(s)"
            cv_home_activity_favorities.visibility = View.VISIBLE
        } else {
            cv_home_activity_favorities.visibility = View.GONE
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
            updateScreen -> {
                loadFavorites()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

}
