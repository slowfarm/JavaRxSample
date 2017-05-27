package eva.android.com.javarx.Models;


import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class User extends RealmObject {

    @SerializedName("login") private String login;
    @SerializedName("id") private Integer id;
    @SerializedName("avatar_url") private String avatarUrl;
    @SerializedName("name") private String name;
    @SerializedName("company") private String company;
    @SerializedName("blog") private String blog;
    @SerializedName("location") private String location;

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getBlog() {
        return blog;
    }

    public String getLocation() {
        return location;
    }

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