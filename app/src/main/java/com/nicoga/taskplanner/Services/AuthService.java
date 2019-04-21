package com.nicoga.taskplanner.Services;


import com.nicoga.taskplanner.LoginWrapper;
import com.nicoga.taskplanner.Utils.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("user/login")
    Call<Token> login(@Body LoginWrapper user);
}
