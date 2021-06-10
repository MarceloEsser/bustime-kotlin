package esser.marcelo.busoclock.view.activity

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import esser.marcelo.busoclock.R
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

        observers()

        onInitValues()
    }

    abstract fun onInitValues()

    abstract fun observers()

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


    fun showSnackBar(message: String) {
        Snackbar.make(findViewById(R.id.content), message,
            Snackbar.LENGTH_SHORT
        ).setBackgroundTint(Color.BLACK).setTextColor(Color.WHITE).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (isShowingLoader) {
            hideLoader()
        }
    }

    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}