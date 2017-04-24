package eva.android.com.javarx.Service;

import eva.android.com.javarx.Models.User;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.Single;

public interface GithubService {
    /**
    * get request with variable path
    */
    @GET("/users/{user}")
    Single<User> getUser(@Path("user") String user);
}
