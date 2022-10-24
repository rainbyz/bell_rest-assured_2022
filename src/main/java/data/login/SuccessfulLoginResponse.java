package data.login;

public class SuccessfulLoginResponse {
    private String token;

    public SuccessfulLoginResponse(String token) {
        this.token = token;
    }

    public SuccessfulLoginResponse() {
        super();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}