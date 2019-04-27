package com.nicoga.taskplanner.storage;

import android.content.Context;
import android.content.SharedPreferences;
import com.nicoga.taskplanner.R;

import com.nicoga.taskplanner.network.data.Token;

public class Storage {
    private final String TOKEN_KEY = "TOKEN_KEY";

    private final SharedPreferences sharedPreferences;

    public Storage( Context context )
    {
        this.sharedPreferences =
                context.getSharedPreferences( context.getString( R.string.login_preferences ), Context.MODE_PRIVATE );
    }

    public String getToken()
    {
        return sharedPreferences.getString( TOKEN_KEY, null );
    }

    public void saveToken( Token token )
    {
        sharedPreferences.edit().putString( TOKEN_KEY, token.getAccessToken() ).apply();
    }

    public boolean containsToken()
    {
        return sharedPreferences.contains( TOKEN_KEY );
    }

    public void clear()
    {
        sharedPreferences.edit().remove( TOKEN_KEY ).apply();
    }
}
