package eva.android.com.javarx;

import android.app.Application;

import eva.android.com.javarx.Net.DaggerNetComponent;
import eva.android.com.javarx.Net.NetComponent;
import eva.android.com.javarx.Net.NetModule;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class App extends Application {
    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        mNetComponent = DaggerNetComponent.builder()
                .netModule(new NetModule(getResources().getString(R.string.base_url)))
                .build();

    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
