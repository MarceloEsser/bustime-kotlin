package esser.marcelo.busoclock.view.activity

import android.content.Intent
import android.net.Uri
import android.net.Uri.fromParts
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import esser.marcelo.busoclock.R
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

    override fun onInitValues() {

        events()

        cardListeners()

        favoriteCardValidations()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLinesQuantity()
    }

    private fun favoriteCardValidations() {
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
        todeschiniEvents()
        esserEvents()
        wottrichEvents()
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

    private fun wottrichEvents() {
        wottrichGitHubEvent()

        wottrichEmailEvent()

        wottrichLinkedInEvent()
    }

    private fun wottrichGitHubEvent() {
        llWottrichGitHub.setOnClickListener {
            val url = "https://github.com/Wottrich"
            openAUrl(url)
        }
    }

    private fun wottrichLinkedInEvent() {
        llWottrichLinkedIn.setOnClickListener {
            val url = "https://www.linkedin.com/in/lucas-c-wottrich/"
            openAUrl(url)
        }
    }

    private fun wottrichEmailEvent() {
        llWottrichEmail.setOnClickListener {

            val emailIntent = Intent(
                Intent.ACTION_SENDTO, fromParts(
                    "mailto", "wottrich78@gmail.com", null
                )
            )
            startActivity(Intent.createChooser(emailIntent, "Enviar email..."))
        }
    }

    private fun todeschiniEvents() {
        todeschiniGitHubEvent()

        todeschiniEmailEvent()

        todeschiniLinkedInEvent()
    }

    private fun todeschiniGitHubEvent() {
        llTodeschiniGitHub.setOnClickListener {
            val url = "https://github.com/brunotodeschini"
            openAUrl(url)
        }
    }

    private fun todeschiniLinkedInEvent() {
        llTodeschiniLinkedIn.setOnClickListener {
            val url = "https://www.linkedin.com/in/bruno-todeschini/"
            openAUrl(url)
        }
    }

    private fun todeschiniEmailEvent() {
        llTodeschiniEmail.setOnClickListener {

            val emailIntent = Intent(
                Intent.ACTION_SENDTO, fromParts(
                    "mailto", "obruno1997@gmail.com", null
                )
            )
            startActivity(Intent.createChooser(emailIntent, "Enviar email..."))
        }
    }

    private fun openAUrl(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
}