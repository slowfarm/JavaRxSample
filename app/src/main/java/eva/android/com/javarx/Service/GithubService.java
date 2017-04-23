package eva.android.com.javarx.Service;

import eva.android.com.javarx.Models.User;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GithubService {
    /*
    * get request with variable path
    */
    @GET("/users/{user}")
    Observable<User> getUsers(@Path("user") String user);
}
