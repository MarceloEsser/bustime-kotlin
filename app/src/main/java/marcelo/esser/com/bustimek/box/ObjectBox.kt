package marcelo.esser.com.bustimek.box

import android.content.Context
import io.objectbox.BoxStore
import marcelo.esser.com.bustimek.model.sogal.MyObjectBox

class ObjectBox {

    companion object {
        lateinit var boxStore: BoxStore

        fun init(context: Context) {
            boxStore = MyObjectBox.builder().androidContext(context.applicationContext).build()
        }
    }
}