package marcelo.esser.com.bustimek.vvm.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import marcelo.esser.com.bustimek.R
import marcelo.esser.com.bustimek.service.vicasaServices.VicasaService

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

        /*cv_home_activity_vicasa.setOnClickListener {
            chamaServico()
        }

        cv_home_activity_sogal.setOnClickListener {
            startActivity(Intent(activityContext, SogalLinesActivity::class.java))
        }*/
    }

    /*private fun chamaServico() {
        vicasaString.vicasaService().getVicasaSchedules("I307C").enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                parseResponse(response.body()!!.string())
            }
        })
    }*/

    /*fun parseResponse(response: String) {
        print(response)
        val diasUteisCBCCSelector =
            "body > table:nth-child(2) > tbody > tr > td > table.texto_linhas > tbody > tr:nth-child(5) > td:nth-child(4)"
        val document: Document = Jsoup.parse(response)
        val diasCBElement: Element? = document.selectFirst(diasUteisCBCCSelector)

        for (element in diasCBElement!!.children()) {
            val texto = element.text()
            println(texto)
        }

    }*/
}
