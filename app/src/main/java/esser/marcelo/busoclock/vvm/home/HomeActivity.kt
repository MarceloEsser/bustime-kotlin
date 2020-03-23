package esser.marcelo.busoclock.vvm.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.service.vicasaServices.VicasaService
import esser.marcelo.busoclock.vvm.sogal.lines.SogalLinesActivity
import esser.marcelo.busoclock.vvm.vicasa.lines.VicasaLinesActivity
import kotlinx.android.synthetic.main.activity_home.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class HomeActivity : AppCompatActivity() {

    private val activityContext by lazy {
        this@HomeActivity
    }

    private val vicasaString: VicasaService by lazy {
        VicasaService()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //chamaServico()

        cv_home_activity_vicasa.setOnClickListener {
            startActivity(Intent(activityContext, VicasaLinesActivity::class.java))
        }

        cv_home_activity_sogal.setOnClickListener {
            startActivity(Intent(activityContext, SogalLinesActivity::class.java))
        }
    }

    private fun events() {
        cardsEvents()

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

    private fun cardsEvents() {
        cv_home_activity_vicasa.setOnClickListener {
            startActivityForResult(
                Intent(activityContext, VicasaLinesActivity::class.java),
                updateScreen
            )
        }

        cv_home_activity_sogal.setOnClickListener {
            startActivityForResult(
                Intent(activityContext, SogalLinesActivity::class.java),
                updateScreen
            )
        }

        cv_home_activity_favorities.setOnClickListener {
            startActivityForResult(
                Intent(activityContext, FavoriteActivity::class.java),
                updateScreen
            )
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

    private fun openAUrl(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
            updateScreen -> {
                loadFavorites()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

}

}
