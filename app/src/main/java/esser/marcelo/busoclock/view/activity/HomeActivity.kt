package esser.marcelo.busoclock.view.activity

import android.content.Intent
import android.net.Uri
import android.net.Uri.fromParts
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.view.activity.favorite.FavoriteLinesActivity
import esser.marcelo.busoclock.view.activity.sogal.SogalLinesActivity
import esser.marcelo.busoclock.view.activity.vicasa.VicasaLinesActivity
import esser.marcelo.busoclock.viewModel.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/09/20
 */

class HomeActivity : BaseActivity(R.layout.activity_home) {

    private val activityContext by lazy {
        this@HomeActivity
    }

    private val viewModel: HomeViewModel by viewModel()

    override fun observers() {
        favoriteQuantityObserver()
    }

    override fun onInitValues() {
        events()
        cardListeners()

        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this@HomeActivity, R.color.grey400)
    }

    private fun favoriteQuantityObserver() {
        val favSizeObserver = Observer<Int> { value ->
            if (value != null && value > 0) {
                cv_home_activity_favorities.visibility = VISIBLE
                cl_favorites_button_content.visibility = VISIBLE
                if (value > 1) {
                    tv_favorite_count.text = "$value linhas salvas"
                } else {
                    tv_favorite_count.text = "$value linha salva"
                }
            } else {
                cv_home_activity_favorities.visibility = GONE
                cl_favorites_button_content.visibility = GONE
            }
        }

        viewModel.favSize.observe(this, favSizeObserver)
    }

    private fun cardListeners() {
        cv_home_activity_vicasa.setOnClickListener {
            startActivity(Intent(activityContext, VicasaLinesActivity::class.java))
        }

        cv_home_activity_sogal.setOnClickListener {
            startActivity(Intent(activityContext, SogalLinesActivity::class.java))
        }

        cv_home_activity_favorities.setOnClickListener {
            startActivity(Intent(activityContext, FavoriteLinesActivity::class.java))
        }
    }

    private fun events() {
        //cardsEvents()
        esserEvents()
    }

    private fun esserEvents() {
        esserGitHubEvent()

        esserEmailEvent()

        esserLinkedInEvent()
    }

    private fun esserGitHubEvent() {
        llEsserGitHub.setOnClickListener {
            openAUrl("https://github.com/MarceloEsser")
        }
    }

    private fun esserEmailEvent() {
        llEsserEmail.setOnClickListener {
            val emailIntent = Intent(
                Intent.ACTION_SENDTO, fromParts(
                    "mailto", "marcelo.v.esser@gmail.com", null
                )
            )
            startActivity(Intent.createChooser(emailIntent, "Enviar email..."))
        }
    }

    private fun esserLinkedInEvent() {
        llEsserLinkedIn.setOnClickListener {
            openAUrl("https://www.linkedin.com/in/marcelo-esser/")
        }
    }

    private fun openAUrl(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
}