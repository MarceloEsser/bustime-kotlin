package esser.marcelo.busoclock.view.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import esser.marcelo.busoclock.helper.ProgressDialogHelper

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/09/20
 */

abstract class BaseActivity(private val layoutRes: Int) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        onInitValues()
    }

    abstract fun onInitValues()

    private val progressDialog: ProgressDialogHelper by lazy {
        ProgressDialogHelper(this)
    }

    private var isShowingLoader = false
    private var canCancelLoader = true

    fun showLoader(isCancelable: Boolean = true) {
        progressDialog.showLoader()
        canCancelLoader = isCancelable
        isShowingLoader = true
    }

    fun hideLoader() {
        progressDialog.hideLoader()
        isShowingLoader = false
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (isShowingLoader && canCancelLoader) {
            hideLoader()
        }
    }
}