package eva.android.com.javarx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.annimon.stream.Stream;

import eva.android.com.javarx.Adapter.CardAdapter;
import eva.android.com.javarx.Models.User;
import eva.android.com.javarx.Service.GithubService;
import eva.android.com.javarx.Service.ServiceFactory;
import io.realm.Realm;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    CardAdapter mCardAdapter;
    String API_BASE_URL = "https://api.github.com/";
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        * Realm initialization and adding change listener
        */
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        realm.addChangeListener(element -> mCardAdapter.notifyDataSetChanged());
        /*
        * Service initialization
        */
        GithubService service = ServiceFactory.createRetrofitService(GithubService.class, API_BASE_URL);
        /*
        * View initialization
        */
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        Button bClear = (Button) findViewById(R.id.button_clear);
        Button bFetch = (Button) findViewById(R.id.button_fetch);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCardAdapter = new CardAdapter(realm.where(User.class).findAll());
        mRecyclerView.setAdapter(mCardAdapter);

        bClear.setOnClickListener(v -> realm.executeTransactionAsync(realm1 -> realm1.delete(User.class)));
        bFetch.setOnClickListener(v -> Stream.of(Data.githubList).forEach(s -> service.getUsers(s)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        r -> realm.executeTransactionAsync(realm1 -> realm1.insert(r)),
                        Throwable::printStackTrace,
                        () -> Toast.makeText(this, "complete", Toast.LENGTH_SHORT).show()
                )));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
