package com.nicoga.taskplanner.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.nicoga.taskplanner.R;
import com.nicoga.taskplanner.storage.Storage;

public class LaunchActivity extends AppCompatActivity {

    public static final String TOKEN_KEY = "TOKEN_KEY";

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        Storage storage =new Storage(this);

        if(storage.containsToken()){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        }
    }
}
