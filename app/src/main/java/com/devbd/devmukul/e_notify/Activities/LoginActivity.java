package com.devbd.devmukul.e_notify.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.devbd.devmukul.e_notify.Api.HttpRequestPostAsyncTask;
import com.devbd.devmukul.e_notify.Api.HttpResponseListener;
import com.devbd.devmukul.e_notify.Api.HttpResponseObject;
import com.devbd.devmukul.e_notify.Constants;
import com.devbd.devmukul.e_notify.Model.LoginRequest;
import com.devbd.devmukul.e_notify.Model.LoginResponse;
import com.devbd.devmukul.e_notify.R;
import com.devbd.devmukul.e_notify.Utilities;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity implements HttpResponseListener {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    String date = "12-01-2017";
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat formatter1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

    private HttpRequestPostAsyncTask mLoginTask = null;
    private LoginResponse mLoginResponse;
    private String userName;
    private String password;
    private ProgressDialog mProgressDialog;
    SharedPreferences pref;



    @Bind(R.id.input_username) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Authenticating... Please Wait..");
        
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Utilities.isConnectionAvailable(LoginActivity.this)) login();
                else if (LoginActivity.this != null)
                    Toast.makeText(LoginActivity.this, "No Internet Connection! Try Again..", Toast.LENGTH_LONG).show();

            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (mLoginTask != null) {
            return;
        }

        boolean cancel = false;
        View focusView = null;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (!validate()) {
            onLoginFailed();
            return;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if (focusView != null) focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            userName = email;
            password= password;

            mProgressDialog.show();
            LoginRequest mOtpRequestPersonalSignup = new LoginRequest(userName, password);
            Gson gson = new Gson();
            String json = gson.toJson(mOtpRequestPersonalSignup);
            mLoginTask = new HttpRequestPostAsyncTask("LOG_IN",
                    "https://whispering-springs-63266.herokuapp.com/auth", json, LoginActivity.this);
            mLoginTask.mHttpResponseListener = this;
            mLoginTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);


        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() ) {
            _emailText.setError("this field can't be empty");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    @Override
    public void httpResponseReceiver(@Nullable HttpResponseObject result) {


        if (result == null || result.getStatus() == Constants.HTTP_RESPONSE_STATUS_INTERNAL_ERROR
                || result.getStatus() == Constants.HTTP_RESPONSE_STATUS_NOT_FOUND) {
            mProgressDialog.dismiss();
            mLoginTask = null;
            if (LoginActivity.this != null)
                Toast.makeText(LoginActivity.this, "Login Failed.", Toast.LENGTH_SHORT).show();
            return;
        }


        Gson gson = new Gson();

        if (result.getApiCommand().equals("LOG_IN")) {

            String message;
            try {
                mLoginResponse = gson.fromJson(result.getJsonString(), LoginResponse.class);
                message = mLoginResponse.getToken();
            } catch (Exception e) {
                e.printStackTrace();
                message = "Server Error!";
            }


            if (result.getStatus() == Constants.HTTP_RESPONSE_STATUS_OK) {
                pref = LoginActivity.this.getSharedPreferences(Constants.ApplicationTag, Activity.MODE_PRIVATE);
                pref.edit().putString(Constants.ACCESS_TOKEN_KEY, message).apply();
                onLoginSuccess();

            } else if (result.getStatus() == Constants.HTTP_RESPONSE_STATUS_NOT_ACCEPTABLE) {
                if (LoginActivity.this != null)
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();

            } else {
                if (LoginActivity.this != null)
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }

            mProgressDialog.dismiss();
            mLoginTask = null;
        }
    }
}
