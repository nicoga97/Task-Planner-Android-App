package com.nicoga.taskplanner;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;
import com.nicoga.taskplanner.Services.AuthService;
import com.nicoga.taskplanner.Utils.Token;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;
    private AuthService authService;
    private AlertDialog alertDialog;
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        textInputEmail = findViewById(R.id.textInputEmail);
        textInputPassword = findViewById(R.id.textInputPassword);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://task-panner-api.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        authService = retrofit.create(AuthService.class);
        buildDialog();

    }

    private void buildDialog(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle(getApplicationContext().getResources().getString(R.string.login_failed_dialog_title));
        builder1.setMessage(getApplicationContext().getResources().getString(R.string.login_failed_dialog_message));
        builder1.setCancelable(true);
        builder1.setNegativeButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        alertDialog = builder1.create();
    }

    private boolean validateEmail() {
        AppCompatEditText emailInput = (AppCompatEditText) textInputEmail.getEditText();
        textInputEmail.setError(null);
        if (TextUtils.isEmpty(emailInput.getText().toString())) {
            textInputEmail.setError(getApplicationContext().getResources().getString(R.string.field_validation_empty));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput.getText().toString()).matches()) {
            textInputEmail.setError(getApplicationContext().getResources().getString(R.string.field_validation_email));
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        textInputPassword.setError(null);
        AppCompatEditText passwordInput = (AppCompatEditText) textInputPassword.getEditText();
        if (TextUtils.isEmpty(passwordInput.getText().toString())) {
            textInputPassword.setError(getApplicationContext().getResources().getString(R.string.field_validation_empty));
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }

    private void switchToMainView() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void login(View v) {
        if (validateEmail() && validatePassword()) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Response<Token> response =
                                authService.login(new LoginWrapper(textInputEmail.getEditText().getText().toString(), textInputPassword.getEditText().getText().toString())).execute();
                        Token token =response.body();
                        if(token==null){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    alertDialog.show();
                                }
                            });
                            return;
                        }
                        saveLoginInformation(token);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                switchToMainView();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        } else {
            return;
        }
    }

    private void saveLoginInformation(Token token) {
        SharedPreferences sharedPref =
                getSharedPreferences(getString(R.string.login_preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("TOKEN_KEY");
        editor.putString("TOKEN_KEY", "Bearer "+token.getAccessToken());
        editor.apply();
    }
}

