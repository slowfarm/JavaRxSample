package eva.android.com.javarx;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Vladimir on 22.04.2017.
 */

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
