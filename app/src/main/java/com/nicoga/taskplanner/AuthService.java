package com.nicoga.taskplanner;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("/login")
    Call<LoginWrapper> createUser(@Body LoginWrapper user);
}
