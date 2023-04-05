package io.swagger.rbac.client.model;



import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("access_token")
    private String accessToken;


    @SerializedName("refresh_token")
    private String refreshToken;

    @SerializedName("user_name")
    private String userName;


    public LoginResponse(){

    }

    public LoginResponse(String accessToken, String refreshToken, String userName) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userName = userName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getUserName() {
        return userName;
    }
}
