package marcelo.esser.com.bustimek

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this@CustomApplication)
        val realmConfiguration: RealmConfiguration = RealmConfiguration.Builder()
            .name("realm-sogal-lines.realm")
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }
}