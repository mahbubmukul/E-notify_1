package com.devbd.devmukul.e_notify.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.devbd.devmukul.e_notify.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

//    @Bind(R.id.input_name) EditText _nameText;
//    @Bind(R.id.input_address) EditText _addressText;
    @Bind(R.id.input_username) EditText _userNameText;
    @Bind(R.id.input_lmd) EditText _lmdText;
    @Bind(R.id.input_password) EditText _passwordText;
//    @Bind(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @Bind(R.id.btn_signup) Button _signupButton;
    @Bind(R.id.link_login) TextView _loginLink;

    private String mDOB;
    private ProgressDialog mProgressDialog;

    private int mYear;
    private int mMonth;
    private int mDay;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });


        final DatePickerDialog dialog = new DatePickerDialog(
                this, mDateSetListener, 2017, 2, 14);

        _lmdText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

//        String name = _nameText.getText().toString();
//        String address = _addressText.getText().toString();
        String email = _userNameText.getText().toString();
        String mobile = _lmdText.getText().toString();
        String password = _passwordText.getText().toString();
//        String reEnterPassword = _reEnterPasswordText.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

//        String name = _nameText.getText().toString();
//        String address = _addressText.getText().toString();
        String email = _userNameText.getText().toString();
        String mobile = _lmdText.getText().toString();
        String password = _passwordText.getText().toString();
//        String reEnterPassword = _reEnterPasswordText.getText().toString();

//        if (name.isEmpty() || name.length() < 3) {
//            _nameText.setError("at least 3 characters");
//            valid = false;
//        } else {
//            _nameText.setError(null);
//        }
//
//        if (address.isEmpty()) {
//            _addressText.setError("Enter Valid Address");
//            valid = false;
//        } else {
//            _addressText.setError(null);
//        }


        if (email.isEmpty()) {
            _userNameText.setError("enter a valid user name");
            valid = false;
        } else {
            _userNameText.setError(null);
        }

        if (mobile.isEmpty()) {
            _lmdText.setError("Enter Valid LMD Date");
            valid = false;
        } else {
            _lmdText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

//        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
//            _reEnterPasswordText.setError("Password Do not match");
//            valid = false;
//        } else {
//            _reEnterPasswordText.setError(null);
//        }

        return valid;
    }

    private final DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    String[] mWeekArray, mMonthArray;
                    String birthDate, birthMonth, birthYear;
                    int dayofweek;

                    mYear = year;
                    mMonth = monthOfYear + 1;
                    mDay = dayOfMonth;
                    mWeekArray = getResources().getStringArray(R.array.day_of_week);
                    mMonthArray = getResources().getStringArray(R.array.month_name);

                    if (mDay < 10) birthDate = "0" + mDay;
                    else birthDate = mDay + "";
                    if (mMonth < 10) birthMonth = "0" + mMonth;
                    else birthMonth = mMonth + "";
                    birthYear = mYear + "";

                    Calendar c = Calendar.getInstance();
                    c.setTime(new Date(mYear, mMonth - 1, mDay));
                    dayofweek = c.get(Calendar.DAY_OF_WEEK);

                    String testDate = birthDate + "/" + birthMonth + "/" + birthYear;

                    SimpleDateFormat readDate = new SimpleDateFormat("dd/MM/yyyy");
                    readDate.setTimeZone(TimeZone.getTimeZone("GMT+06:00")); // missing line
                    Date date=null;
                    try {
                        date = readDate.parse(testDate);
                    }catch (Exception e){}
                    SimpleDateFormat writeDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    writeDate.setTimeZone(TimeZone.getTimeZone("GMT"));
                    String s = writeDate.format(date);

                    System.out.println("Date Test GMT"+s);
                    mDOB = s;

                    _lmdText.setError(null);
                    _lmdText.setText(testDate);
                }
            };
}