package esser.marcelo.busoclock

import android.app.Application
import esser.marcelo.busoclock.box.ObjectBoxFile

/**
 * @author Wottrich
 * @since 25/07/2019.
 */

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ObjectBoxFile.initBox(this)
    }

}