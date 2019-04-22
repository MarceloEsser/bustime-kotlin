package marcelo.esser.com.bustimek

import android.app.Application
import marcelo.esser.com.bustimek.box.ObjectBox

class CustomApplication(): Application() {

    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this@CustomApplication)
    }
}