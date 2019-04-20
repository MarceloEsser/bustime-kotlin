package marcelo.esser.com.bustimek.model.sogal

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class SogalFavoriteLines(
    @PrimaryKey
    val id: Long? = null,

    val workingDays: RealmList<SchedulesDTO>? = null,
    val saturdays: RealmList<SchedulesDTO>? = null,
    val sunddays: RealmList<SchedulesDTO>? = null,
    val lineName: String? = null,
    val lineCode: String? = null,
    val lineWay: String? = null
) : RealmObject()