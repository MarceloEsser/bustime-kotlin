package esser.marcelo.busoclock.helper

import android.app.ProgressDialog
import android.content.Context
import android.os.Handler

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/08/20
 */

class ProgressDialogHelper(private val context: Context) {

    private var progressDialog: ProgressDialog? = null

    fun showLoader() {
        progressDialog = ProgressDialog.show(context, "Por Favor aguarde", "Carregando", true)
        Handler().postDelayed({
            hideLoader()

        }, 60000)
    }

    fun hideLoader() {
        if (progressDialog != null && progressDialog!!.isShowing()) {
            progressDialog!!.dismiss();
        }
        progressDialog = null;
    }

}