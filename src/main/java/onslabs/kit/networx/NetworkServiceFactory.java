//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package onslabs.kit.networx;

import java.util.HashMap;
import onslabs.kit.networx.NetworkClient;
import onslabs.kit.networx.TokenAuthenticator;
import retrofit2.Retrofit;

public class NetworkServiceFactory {
    private static NetworkServiceFactory sDataService;
    private Retrofit mRestClient;
    private TokenAuthenticator mTokenAuthenticator;

    private NetworkServiceFactory() {
    }

    private NetworkServiceFactory(Retrofit restClient) {
        this.mRestClient = restClient;
    }

    public static <S> S getInstance(String baseUrl, Class<S> serviceClass, HashMap requestHeaderMap) {
        if(sDataService == null) {
            sDataService = new NetworkServiceFactory(NetworkClient.getRestAdapter(baseUrl, requestHeaderMap));
        }

        return sDataService.getClient(serviceClass);
    }

    public static <S> S getNewInstance(String baseUrl, Class<S> serviceClass, HashMap requestHeaderMap) {
        sDataService = null;
        sDataService = new NetworkServiceFactory(NetworkClient.getRestAdapter(baseUrl, requestHeaderMap));
        return sDataService.getClient(serviceClass);
    }

    private <S> S getClient(Class<S> serviceClass) {
        return this.mRestClient.create(serviceClass);
    }
}

