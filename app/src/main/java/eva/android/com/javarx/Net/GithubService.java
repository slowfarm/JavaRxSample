package eva.android.com.javarx.Net;


import java.util.List;

import eva.android.com.javarx.Models.User;
import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface GithubService {

    @GET("/vovan/test.json")
    Flowable<List<User>> getUser();
}
