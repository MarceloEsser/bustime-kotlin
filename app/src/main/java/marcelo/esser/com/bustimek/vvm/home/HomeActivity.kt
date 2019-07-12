package marcelo.esser.com.bustimek.vvm.home

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*
import marcelo.esser.com.bustimek.R
import marcelo.esser.com.bustimek.vvm.sogal.lines.SogalLinesActivity
import marcelo.esser.com.bustimek.vvm.vicasa.lines.VicasaLinesActivity

class HomeActivity : AppCompatActivity() {

    private val activityContext by lazy {
        this@HomeActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        cv_home_activity_vicasa.setOnClickListener {
            startActivity(Intent(activityContext, VicasaLinesActivity::class.java))
        }

        cv_home_activity_sogal.setOnClickListener {
            startActivity(Intent(activityContext, SogalLinesActivity::class.java))
        }
    }
}
