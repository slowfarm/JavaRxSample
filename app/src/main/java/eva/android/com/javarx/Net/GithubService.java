package eva.android.com.javarx.Net;


import eva.android.com.javarx.Models.User;
import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {

    @GET("/users/{user}")
    Flowable<User> getUser(@Path("user") String user);
}
