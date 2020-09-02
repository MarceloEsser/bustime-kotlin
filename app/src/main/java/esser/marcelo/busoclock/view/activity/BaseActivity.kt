package esser.marcelo.busoclock.view.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import esser.marcelo.busoclock.view.dialog.LoaderDialog

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/09/20
 */

abstract class BaseActivity(private val layoutRes: Int) : AppCompatActivity() {

    private val loader: LoaderDialog = LoaderDialog()

    private var isShowingLoader = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        onInitValues()
    }

    abstract fun onInitValues()

    fun showLoader() {
        loader.show(supportFragmentManager, "loader")
        isShowingLoader = true
    }

    fun hideLoader() {
        if (isShowingLoader) {
            loader.dismiss()
        }
        isShowingLoader = false
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (isShowingLoader) {
            hideLoader()
        }
    }
}