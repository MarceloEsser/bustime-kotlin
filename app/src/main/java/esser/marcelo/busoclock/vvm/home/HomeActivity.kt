package esser.marcelo.busoclock.vvm.home

import android.content.Intent
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

}
