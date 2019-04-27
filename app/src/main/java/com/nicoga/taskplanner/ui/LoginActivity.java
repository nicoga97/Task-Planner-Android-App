package com.nicoga.taskplanner.ui;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;
import com.nicoga.taskplanner.network.RetrofitNetwork;
import com.nicoga.taskplanner.network.data.LoginWrapper;
import com.nicoga.taskplanner.R;
import com.nicoga.taskplanner.network.data.Token;
import com.nicoga.taskplanner.storage.Storage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;
    private AlertDialog alertDialog;
    private Storage storage;
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        textInputEmail = findViewById(R.id.textInputEmail);
        textInputPassword = findViewById(R.id.textInputPassword);
        storage=new Storage(this);


    }

    private void buildDialog(String message){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle(getApplicationContext().getResources().getString(R.string.login_failed_dialog_title));
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setNegativeButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        return;
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
            v.setEnabled(false);
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Response<Token> response =
                                RetrofitNetwork.getAuthService()
                                        .login(new LoginWrapper(textInputEmail.getEditText().getText().toString(),
                                                textInputPassword.getEditText().getText().toString())).execute();
                        Token token =response.body();
                        if(token==null){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    buildDialog(getApplicationContext().getResources().getString(R.string.login_failed_dialog_message));
                                    alertDialog.show();
                                }
                            });
                            return;
                        }
                        storage.saveToken(token);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                switchToMainView();
                            }
                        });
                    } catch (Exception e) {
                        buildDialog(e.getMessage());
                        alertDialog.show();
                        e.printStackTrace();
                    }

                }
            });
            v.setEnabled(true);

        } else {
            return;
        }
    }

}

