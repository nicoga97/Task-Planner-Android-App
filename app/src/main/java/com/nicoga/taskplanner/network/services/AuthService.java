package com.nicoga.taskplanner.network.services;


import com.nicoga.taskplanner.network.data.LoginWrapper;
import com.nicoga.taskplanner.network.data.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("user/login")
    Call<Token> login(@Body LoginWrapper user);
}
