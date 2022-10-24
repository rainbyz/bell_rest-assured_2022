package data.login;

public class UnsuccessfulLoginResponse {
    private String error;

    public UnsuccessfulLoginResponse(String error) {
        this.error = error;
    }

    public UnsuccessfulLoginResponse() {
        super();
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}