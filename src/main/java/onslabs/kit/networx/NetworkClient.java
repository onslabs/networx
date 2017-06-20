//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package onslabs.kit.networx;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Interceptor.Chain;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            int maxStale = 2419200;
            return originalResponse.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale).build();
        }
    };

    private NetworkClient() {
    }

    public static Retrofit getRestAdapter(String baseUrl, final HashMap<String, String> requestHeaderMap) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(Level.HEADERS);
        interceptor.setLevel(Level.BODY);
        Gson gson = (new GsonBuilder()).addSerializationExclusionStrategy(new ExclusionStrategy() {
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                Expose expose = (Expose)fieldAttributes.getAnnotation(Expose.class);
                return expose != null && !expose.serialize();
            }

            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        }).addDeserializationExclusionStrategy(new ExclusionStrategy() {
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                Expose expose = (Expose)fieldAttributes.getAnnotation(Expose.class);
                return expose != null && !expose.deserialize();
            }

            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        }).setLenient().create();
        OkHttpClient client = (new Builder()).addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR).addInterceptor(interceptor).addNetworkInterceptor(new Interceptor() {
            public Response intercept(Chain chain) throws IOException {
                okhttp3.Request.Builder builder = chain.request().newBuilder();
                Set entrySet = requestHeaderMap.entrySet();
                Iterator request = entrySet.iterator();

                while(request.hasNext()) {
                    Entry entry = (Entry)request.next();
                    if(entry.getValue() != null) {
                        if(((String)entry.getValue()).isEmpty()) {
                            builder.removeHeader((String)entry.getKey());
                        } else {
                            builder.addHeader((String)entry.getKey(), (String)entry.getValue());
                        }
                    }
                }

                Request request1 = builder.build();
                return chain.proceed(request1);
            }
        }).addNetworkInterceptor(interceptor).writeTimeout(5, TimeUnit.MINUTES).readTimeout(5, TimeUnit.MINUTES).build();
        return (new retrofit2.Retrofit.Builder()).baseUrl(baseUrl).client(client).addConverterFactory(GsonConverterFactory.create(gson)).build();
    }
}

