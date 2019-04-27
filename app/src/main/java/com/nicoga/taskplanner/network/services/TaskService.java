package com.nicoga.taskplanner.network.services;

import com.nicoga.taskplanner.network.data.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TaskService {
    @GET("api/tasks")
    Call<List<Task>> getTasks();
}
