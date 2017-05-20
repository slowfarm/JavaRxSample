package eva.android.com.javarx;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import org.reactivestreams.Publisher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import eva.android.com.javarx.Adapter.CardAdapter;
import eva.android.com.javarx.Models.User;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private CardAdapter mCardAdapter;
    private Realm realm;
    private String[] githubUsers;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private boolean loading = false;
    private int pageNumber = 1;
    private final int VISIBLE_THRESHOLD = 1;
    private int lastVisibleItem, totalItemCount;
    private ProgressBar progressBar;
    private Snackbar snackbar;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private PublishProcessor<Integer> paginator = PublishProcessor.create();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        realm = Realm.getDefaultInstance();
        realm.addChangeListener(items -> mCardAdapter.notifyDataSetChanged());

        snackbar = Snackbar.make(findViewById(R.id.layout), "Нет соединения", Snackbar.LENGTH_INDEFINITE)
                .setAction("Повторить", v -> subscribeForData());

        Button bClear = (Button) findViewById(R.id.button_clear);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mCardAdapter = new CardAdapter(realm.where(User.class).findAll());
        mRecyclerView.setAdapter(mCardAdapter);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        githubUsers = getResources().getStringArray(R.array.github_users);

        bClear.setOnClickListener(v -> realm.executeTransactionAsync(realm1 -> realm1.delete(User.class)));
        setUpLoadMoreListener();
        subscribeForData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
        compositeDisposable.clear();
    }

    private void setUpLoadMoreListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (!loading && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
                    pageNumber++;
                    paginator.onNext(pageNumber);
                    loading = true;
                }
            }
        });
    }

    private void subscribeForData() {

        Disposable disposable = paginator
                .onBackpressureDrop()
                .concatMap(new Function<Integer, Publisher<User>>() {
                    @Override
                    public Publisher<User> apply(@NonNull Integer page) throws Exception {
                        loading = true;
                        progressBar.setVisibility(View.VISIBLE);
                        return dataFromNetwork();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        items -> {
                            realm.executeTransaction(realm1 -> realm1.insert(items));
                            loading = false;
                            progressBar.setVisibility(View.INVISIBLE);
                        }, error -> {
                            progressBar.setVisibility(View.INVISIBLE);
                            error.printStackTrace();
                            snackbar.show();
                            loading = false;
                        });

        compositeDisposable.add(disposable);
        paginator.onNext(pageNumber);

    }

    private Flowable<User> dataFromNetwork() {
         return Application
                 .getService()
                 .getUser(githubUsers[new Random().nextInt(githubUsers.length-1)])
                 .subscribeOn(Schedulers.newThread());
    }
}
