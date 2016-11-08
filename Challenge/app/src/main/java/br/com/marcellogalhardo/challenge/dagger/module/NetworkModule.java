package br.com.marcellogalhardo.challenge.dagger.module;

import android.app.Application;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import javax.inject.Named;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module public class NetworkModule {

  private static final int CACHE_SIZE_10_MB = 10 * 1024 * 1024;

  @Provides @Reusable Cache providesOkHttpCache(Application application) {
    int cacheSize = CACHE_SIZE_10_MB;
    return new Cache(application.getCacheDir(), cacheSize);
  }

  @Provides @Reusable Gson providesGson() {
    return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        .create();
  }

  @Provides @Reusable HttpLoggingInterceptor providesHttpLoggingInterceptor() {
    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    return httpLoggingInterceptor;
  }

  @Provides @Reusable OkHttpClient providesOkHttpClient(Cache cache,
      HttpLoggingInterceptor httpLoggingInterceptor) {
    return new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).cache(cache).build();
  }

  @Provides @Reusable GsonConverterFactory providesGsonConverterFactory(Gson gson) {
    return GsonConverterFactory.create(gson);
  }

  @Provides @Reusable RxJavaCallAdapterFactory providesRxJavaCallAdapterFactory() {
    return RxJavaCallAdapterFactory.create();
  }

  @Provides @Reusable Retrofit providesRetrofit(GsonConverterFactory gsonConverterFactory,
      OkHttpClient okHttpClient, RxJavaCallAdapterFactory rxJavaCallAdapterFactory,
      @Named(SettingModule.BASE_URL) String baseUrl) {
    return new Retrofit.Builder().addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(rxJavaCallAdapterFactory)
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build();
  }
}