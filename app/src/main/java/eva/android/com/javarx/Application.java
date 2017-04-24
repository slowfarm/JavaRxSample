package eva.android.com.javarx;

import eva.android.com.javarx.Service.GithubService;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class Application extends android.app.Application {

    public static GithubService service;
    private final String API_BASE_URL = "https://api.github.com/";

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        /**
        * initialize realm configuration
        */
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        /**
         * initialize retrofit configurations
         */
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(API_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(GithubService.class);
    }

    public static GithubService getService() {
        return service;
    }
}
