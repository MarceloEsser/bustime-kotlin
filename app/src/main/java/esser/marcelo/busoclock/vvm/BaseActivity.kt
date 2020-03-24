package esser.marcelo.busoclock.vvm

import android.support.v7.app.AppCompatActivity
import esser.marcelo.busoclock.helper.ProgressDialogHelper

open class BaseActivity : AppCompatActivity() {
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