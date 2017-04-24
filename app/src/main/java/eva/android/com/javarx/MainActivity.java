package eva.android.com.javarx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.annimon.stream.Stream;

import eva.android.com.javarx.Adapter.CardAdapter;
import eva.android.com.javarx.Models.User;
import io.realm.Realm;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private CardAdapter mCardAdapter;
    private Realm realm;
    private String[] githubUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * Realm initialization and adding change listener
         */
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        realm.addChangeListener(element -> mCardAdapter.notifyDataSetChanged());
        /**
         * View initialization
         */
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        Button bClear = (Button) findViewById(R.id.button_clear);
        Button bFetch = (Button) findViewById(R.id.button_fetch);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCardAdapter = new CardAdapter(realm.where(User.class).findAll());
        mRecyclerView.setAdapter(mCardAdapter);

        githubUsers = getResources().getStringArray(R.array.github_users);
        /**
         * realm async transaction to wipe users data
         */
        bClear.setOnClickListener(v -> realm.executeTransactionAsync(realm1 -> realm1.delete(User.class)));
        bFetch.setOnClickListener(v -> Stream.of(githubUsers).forEach(s ->
                Application.getService().getUsers(s) // lambda foreach
                        /**
                         * network operations must be performed from another thread
                         */
                        .subscribeOn(Schedulers.newThread())
                        /**
                         * realm objects can only be accessed on the thread they were created
                         */
                        .observeOn(AndroidSchedulers.mainThread())
                        /**
                         * follow-up actions:
                         * insert new record to realm if request will be successful
                         * or display error
                         */
                        .subscribe(
                                r -> realm.executeTransactionAsync(realm1 -> realm1.insert(r)), //onComplete()
                                Throwable::printStackTrace //onError()
                        )));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * close realm when activity is destroyed
         */
        realm.close();
    }
}
