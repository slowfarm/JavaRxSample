package eva.android.com.javarx;

import eva.android.com.javarx.Service.GithubService;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class Application extends android.app.Application {

    public static GithubService service;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        /**
        * init realm configuration
        */
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        /**
         * init retrofit configurations
         */
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(getResources().getString(R.string.base_url))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(GithubService.class);
    }

    public static GithubService getService() {
        return service;
    }
}
