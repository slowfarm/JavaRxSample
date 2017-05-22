package eva.android.com.javarx.Models;


import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class User extends RealmObject {

    @SerializedName("login") private String login;
    @SerializedName("id") private Integer id;
    @SerializedName("avatar_url") private String avatarUrl;

    public String getLogin() {
        return login;
    }

    public Integer getId() {
        return id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

}