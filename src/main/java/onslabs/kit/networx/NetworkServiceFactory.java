package onslabs.kit.networx;

import java.util.HashMap;

import retrofit2.Retrofit;


public class NetworkServiceFactory {

    private static NetworkServiceFactory sDataService;
    private Retrofit mRestClient;
    private TokenAuthenticator mTokenAuthenticator;

    private NetworkServiceFactory() {
    }

    private NetworkServiceFactory(Retrofit restClient) {
        mRestClient = restClient;
    }

    public static <S> S getInstance(String baseUrl, Class<S> serviceClass,HashMap requestHeaderMap) {
        if (sDataService == null) {
            sDataService = new NetworkServiceFactory(NetworkClient.getRestAdapter(baseUrl, requestHeaderMap));
        }
        return sDataService.getClient(serviceClass);
    }

    public static <S> S getNewInstance(String baseUrl, Class<S> serviceClass,HashMap requestHeaderMap) {
            sDataService = null;
            sDataService = new NetworkServiceFactory(NetworkClient.getRestAdapter(baseUrl, requestHeaderMap));

        return sDataService.getClient(serviceClass);
    }


    private <S> S getClient(Class<S> serviceClass) {
        return mRestClient.create(serviceClass);
    }

}

