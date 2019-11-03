package ovh.kocproz.mdpages.user.dto;

public class JwtAutheticationResponse {
    private String token;

    public JwtAutheticationResponse(String token) {
        this.token = token;
    }

    public JwtAutheticationResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
