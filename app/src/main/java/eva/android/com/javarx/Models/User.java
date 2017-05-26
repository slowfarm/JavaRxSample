package eva.android.com.javarx.Models;


import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class User extends RealmObject {

    @SerializedName("name") private String name;
    @SerializedName("id") private Integer id;
    @SerializedName("avatar_url") private String avatarUrl;

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

}