package oabs.kit.networx;

import java.io.InputStream;
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

    public static <S> S getInstance(final String baseUrl, final Class<S> serviceClass,final HashMap requestHeaderMap) {
        if (sDataService == null) {
            sDataService = new NetworkServiceFactory(NetworkClient.getRestAdapter(baseUrl, requestHeaderMap));
        }
        return sDataService.getClient(serviceClass);
    }

    public static <S> S getNewInstance(final String baseUrl, final Class<S> serviceClass,final HashMap requestHeaderMap) {
            sDataService = null;
            sDataService = new NetworkServiceFactory(NetworkClient.getRestAdapter(baseUrl, requestHeaderMap));

        return sDataService.getClient(serviceClass);
    }

    public static <S> S getXmlInstance(final String baseUrl, final Class<S> serviceClass,final HashMap requestHeaderMap) {
        if (sDataService == null) {
            sDataService = new NetworkServiceFactory(NetworkClient.getXmlAdapter(baseUrl, requestHeaderMap));
        }
        return sDataService.getClient(serviceClass);
    }

    public static <S> S getNewXmlInstance(final String baseUrl, final Class<S> serviceClass,final HashMap requestHeaderMap) {
        sDataService = null;
        sDataService = new NetworkServiceFactory(NetworkClient.getXmlAdapter(baseUrl, requestHeaderMap));

        return sDataService.getClient(serviceClass);
    }


    public static <S> S getHttpsInstance(final InputStream certificateInputStream, final String baseUrl, final Class<S> serviceClass, final HashMap requestHeaderMap) {
        if (sDataService == null) {
            sDataService = new NetworkServiceFactory(NetworkClient.getHttpsRestAdapter(certificateInputStream,baseUrl, requestHeaderMap));
        }
        return sDataService.getClient(serviceClass);
    }

    public static <S> S getNewHttpsInstance(final InputStream certificateInputStream, final String baseUrl, final Class<S> serviceClass, final HashMap requestHeaderMap) {
        sDataService = null;
        sDataService = new NetworkServiceFactory(NetworkClient.getHttpsRestAdapter(certificateInputStream,baseUrl, requestHeaderMap));

        return sDataService.getClient(serviceClass);
    }

    public static <S> S getXmlHttpsInstance(final InputStream certificateInputStream, final String baseUrl, final Class<S> serviceClass, final HashMap requestHeaderMap) {
        if (sDataService == null) {
            sDataService = new NetworkServiceFactory(NetworkClient.getXmlHttpsAdapter(certificateInputStream,baseUrl, requestHeaderMap));
        }
        return sDataService.getClient(serviceClass);
    }

    public static <S> S getNewXmlHttpsInstance(final InputStream certificateInputStream, final String baseUrl, final Class<S> serviceClass, final HashMap requestHeaderMap) {
        sDataService = null;
        sDataService = new NetworkServiceFactory(NetworkClient.getXmlHttpsAdapter(certificateInputStream,baseUrl, requestHeaderMap));

        return sDataService.getClient(serviceClass);
    }


    private <S> S getClient(Class<S> serviceClass) {
        return mRestClient.create(serviceClass);
    }

}

