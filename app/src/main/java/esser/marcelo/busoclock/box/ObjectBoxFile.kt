package esser.marcelo.busoclock.box

import android.content.Context
import esser.marcelo.busoclock.model.boxModels.MyObjectBox
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor

/**
 * @author Wottrich
 * @since 25/07/2019.
 */

object ObjectBoxFile {

    lateinit var boxStore: BoxStore

    fun initBox(context: Context) {

        boxStore = MyObjectBox.builder().androidContext(context.applicationContext).build()

    }

}

inline fun <reified T> box () : Box<T> = ObjectBoxFile.boxStore.boxFor()

inline fun <reified T> boxList () : List<T> = ObjectBoxFile.boxStore.boxFor<T>().all

inline fun <reified T> put (vararg items: T) = box<T>().put(items.asList())

