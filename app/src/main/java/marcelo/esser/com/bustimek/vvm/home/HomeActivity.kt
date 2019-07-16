package marcelo.esser.com.bustimek.vvm.home

import android.animation.ValueAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import marcelo.esser.com.bustimek.R

class HomeActivity : AppCompatActivity() {

var control = false

    private val activityContext by lazy {
        this@HomeActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        /*cv_home_activity_vicasa.setOnClickListener {
            startActivity(Intent(activityContext, VicasaLinesActivity::class.java))
        }

        cv_home_activity_sogal.setOnClickListener {
            startActivity(Intent(activityContext, SogalLinesActivity::class.java))
        }*/

        animation_view.setOnClickListener {
            control = !control
            val animator = if (control) ValueAnimator.ofFloat(0f, 0.5f) else ValueAnimator.ofFloat(0.5f, 1f)
            animator.addUpdateListener { animation ->
                animation_view.progress = animation.animatedValue as Float
            }
            animator.duration =700
            animator.start()
        }
    }

}
