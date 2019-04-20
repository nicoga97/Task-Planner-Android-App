package com.nicoga.taskplanner;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;

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

        AuthService authService = retrofit.create(AuthService.class);

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

    public void login(View v){
        if(validateEmail() && validatePassword()){
        }else{
          return;
        }
    }
}

