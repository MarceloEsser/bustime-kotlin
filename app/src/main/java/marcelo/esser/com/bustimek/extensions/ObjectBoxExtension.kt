package marcelo.esser.com.bustimek.extensions

import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import marcelo.esser.com.bustimek.box.ObjectBox

inline fun <reified T> box(): Box<T> = ObjectBox.boxStore.boxFor()

inline fun <reified T> boxList(): List<T> = ObjectBox.boxStore.boxFor<T>().all

