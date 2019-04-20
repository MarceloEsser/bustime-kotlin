package marcelo.esser.com.bustimek.controller

import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import marcelo.esser.com.bustimek.model.sogal.SogalFavoriteLines

class RealmController {

    fun getAllFavorites(): RealmResults<SogalFavoriteLines> {
        return Realm.getDefaultInstance().where(SogalFavoriteLines::class.java).findAll()
    }

    fun getFavoriteBy(id: Long): SogalFavoriteLines? {
        return Realm.getDefaultInstance().where(SogalFavoriteLines::class.java).equalTo("id", id).findFirst()
    }

    fun generateId(realm: Realm): Long? {
        val results: RealmResults<SogalFavoriteLines> = realm.where(SogalFavoriteLines::class.java)
            .findAll().sort("id", Sort.ASCENDING)

        if (results.size != 0)
            return results.last()!!.id!! + 1

        return null
    }

}