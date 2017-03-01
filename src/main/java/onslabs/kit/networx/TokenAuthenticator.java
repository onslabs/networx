package onslabs.kit.networx;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
//        newAccessToken = service.refreshToken();
        String credential = Credentials.basic("jesse", "password1");
        return response.request().newBuilder()
                .header("Authorization", credential)
                .build();
    }
}