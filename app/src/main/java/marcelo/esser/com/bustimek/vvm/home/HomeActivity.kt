package marcelo.esser.com.bustimek.vvm.home

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import marcelo.esser.com.bustimek.R
import marcelo.esser.com.bustimek.service.vicasaServices.VicasaService
import marcelo.esser.com.bustimek.vvm.sogal.lines.SogalLinesActivity
import marcelo.esser.com.bustimek.vvm.vicasa.lines.VicasaLinesActivity
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.xml.parsers.DocumentBuilderFactory

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

        chamaServico()

        cv_home_activity_vicasa.setOnClickListener {
            chamaServico()
        }

        cv_home_activity_sogal.setOnClickListener {
            startActivity(Intent(activityContext, SogalLinesActivity::class.java))
        }
    }

    private fun chamaServico() {
        vicasaString.vicasaService().getVicasaSchedules("I307C").enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                parseResponse(response.body()!!.string())
            }
        })
    }

    fun parseResponse(response: String) {
        print(response)
        val diasUteisCBCCSelector =
            "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(5) > td:nth-child(4)"
        val document: Document = Jsoup.parse(response)
        val diasCBElement: Element? = document.selectFirst(diasUteisCBCCSelector)

        for (element in diasCBElement!!.children()) {
            val texto = element.text()
            println(texto)
        }

    }
}
