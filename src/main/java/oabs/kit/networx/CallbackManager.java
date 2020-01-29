package oabs.kit.networx;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.HashMap;

import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Jitendra Kumar on 25/11/16.
 */

public abstract class CallbackManager<T>implements Callback<T> {

    private String baseUrl;
    private HashMap<String, String> requestHeaders = new HashMap<>();
    private InputStream certificateInputStream;

    public CallbackManager(final String baseUrl, final HashMap<String, String> requestHeaders) {
        this.baseUrl = baseUrl;
        this.requestHeaders = requestHeaders;
    }

    public CallbackManager(final String baseUrl, final InputStream certificateInputStream, final HashMap<String, String> requestHeaders) {
        this.baseUrl = baseUrl;
        this.requestHeaders = requestHeaders;
        this.certificateInputStream = certificateInputStream;
    }

    public <S> S getHttpsServiceClient(final boolean isHeaderUpdate, final Class<S> serviceClass) {
        if (isHeaderUpdate) {
            return NetworkServiceFactory.getNewHttpsInstance(certificateInputStream, baseUrl,serviceClass, requestHeaders);
        }
        return NetworkServiceFactory.getHttpsInstance(certificateInputStream,baseUrl, serviceClass, requestHeaders);
    }

    public <S> S getServiceClient(final boolean isHeaderUpdate, final Class<S> serviceClass) {
        if (isHeaderUpdate) {
            return NetworkServiceFactory.getNewInstance(baseUrl,serviceClass, requestHeaders);
        }
        return NetworkServiceFactory.getInstance(baseUrl, serviceClass, requestHeaders);
    }

    @Override
    public void onResponse(retrofit2.Call call, Response response) {
        if (response.isSuccessful()) {
            onSuccess(response.body());
        } else {
            try {
                onError(new RetroError(RetroError.Kind.HTTP, response.errorBody().string(), response.code()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(retrofit2.Call call, Throwable throwable) {
        if (throwable instanceof UnknownHostException) {
            onError(new RetroError(RetroError.Kind.NETWORK, "Unable to connect to server.", -999));
        } else {
            onError(new RetroError(RetroError.Kind.UNEXPECTED, "Unexpected error...try after sometime.", -999));
        }
    }

    protected abstract void onSuccess(Object o);

    protected abstract void onError(RetroError retroError);

}
