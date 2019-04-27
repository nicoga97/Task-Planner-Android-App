package com.nicoga.taskplanner.network;

import com.nicoga.taskplanner.network.data.Token;
import com.nicoga.taskplanner.network.services.AuthService;
import com.nicoga.taskplanner.network.services.TaskService;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitNetwork {

    private static final String BASE_URL = "https://task-panner-api.herokuapp.com";
    private Retrofit retrofit;



    public RetrofitNetwork(final String token )
    {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor( new Interceptor()
        {
            @Override
            public okhttp3.Response intercept( Chain chain )
                    throws IOException
            {
                Request original = chain.request();

                Request request = original.newBuilder().header( "Accept", "application/json" ).header( "Authorization",
                        "Bearer "
                                + token).method(
                        original.method(), original.body() ).build();
                return chain.proceed( request );
            }
        } );
        retrofit =
                new Retrofit.Builder().baseUrl( BASE_URL ).addConverterFactory( GsonConverterFactory.create() ).client(
                        httpClient.build() ).build();
    }

    public static AuthService getAuthService(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl( BASE_URL ) //localhost for emulator
                .addConverterFactory( GsonConverterFactory.create() ).build();
        return retrofit.create( AuthService.class );
    }

    public TaskService getTaskService(){
        return retrofit.create(TaskService.class);
    }
}

