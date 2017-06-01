package com.devbd.devmukul.e_notify.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.devbd.devmukul.e_notify.Api.HttpRequestGetAsyncTask;
import com.devbd.devmukul.e_notify.Api.HttpRequestPostAsyncTask;
import com.devbd.devmukul.e_notify.Api.HttpResponseListener;
import com.devbd.devmukul.e_notify.Api.HttpResponseObject;
import com.devbd.devmukul.e_notify.ApiConstants;
import com.devbd.devmukul.e_notify.Constants;
import com.devbd.devmukul.e_notify.CustomView.ResourceSelectorDialog;
import com.devbd.devmukul.e_notify.Model.Item;
import com.devbd.devmukul.e_notify.Model.ProjectResponse;
import com.devbd.devmukul.e_notify.Model.TaskAddRequest;
import com.devbd.devmukul.e_notify.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProjectBaseTaskUpdateActivity extends AppCompatActivity implements HttpResponseListener, View.OnClickListener {

    private static final String TAG = ProjectBaseTaskUpdateActivity.class.getSimpleName();
    private final Gson gson = new GsonBuilder().create();
    ProgressDialog progressDialog;
    int count = 0;
    private int mSelectedProjectId = -1;
    private List<Item> mProjectList;
    private HttpRequestGetAsyncTask mGetProjectListAsyncTask = null;
    private ProjectResponse mProjectResponse;
    private EditText taskNameEditText;
    private EditText taskShortDescEditText;
    private EditText taskStartDateEditText;
    private EditText taskEndDateEditText;
    private EditText taskCostEditText;
    private EditText taskDescEditText;
    private EditText ptaskProjectEditText;
    private Button updateTaskButton;
    private Button cancelButton;
    private ResourceSelectorDialog<Item> professionSelectorDialog;
    private Date startDate;
    private Date endDate;
    private String accessToken;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_base_task_update);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.setCancelable(false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(Constants.ApplicationTag, MODE_PRIVATE);
        loadProfileInfo();
        initializeView();
        progressDialog.show();
        fetchProfessionList();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return true;
    }

    private void loadProfileInfo() {
        accessToken = sharedPreferences.getString(Constants.ACCESS_TOKEN_KEY, "");
    }

    private void initializeView() {
        taskNameEditText = (EditText) findViewById(R.id.task_name);
        taskShortDescEditText = (EditText) findViewById(R.id.task_short_desc);
        taskStartDateEditText = (EditText) findViewById(R.id.task_start_date);
        taskEndDateEditText = (EditText) findViewById(R.id.task_end_date);
        ptaskProjectEditText = (EditText) findViewById(R.id.project_spn);
        taskCostEditText = (EditText) findViewById(R.id.task_cost);
        taskDescEditText = (EditText) findViewById(R.id.task_desc);

        taskCostEditText = (EditText) findViewById(R.id.task_cost);
        updateTaskButton = (Button) findViewById(R.id.submit);

        updateTaskButton.setOnClickListener(this);
        taskStartDateEditText.setOnClickListener(this);
        taskEndDateEditText.setOnClickListener(this);
        
    }

    

    public void fetchProfessionList() {
        if (mGetProjectListAsyncTask != null) {
            return;
        }
        mGetProjectListAsyncTask = new HttpRequestGetAsyncTask("PROJECTS", "https://whispering-springs-63266.herokuapp.com/project/all", this);
        mGetProjectListAsyncTask.addHeader("Authorization", accessToken);
        mGetProjectListAsyncTask.mHttpResponseListener = this;
        mGetProjectListAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }
    private void setProfessionAdapter(List<Item> professionList) {
        professionSelectorDialog = new ResourceSelectorDialog<>(this, "Select a project", professionList, mSelectedProjectId);
        professionSelectorDialog.setOnResourceSelectedListener(new ResourceSelectorDialog.OnResourceSelectedListener() {
            @Override
            public void onResourceSelected(int id, String name) {
                ptaskProjectEditText.setError(null);
                ptaskProjectEditText.setText(name);
                mSelectedProjectId = id;
            }
        });
        ptaskProjectEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                professionSelectorDialog.show();
            }
        });
    }

    @Override
    public void httpResponseReceiver(@Nullable HttpResponseObject result) {

        if (result == null) {
            progressDialog.cancel();
            Toast.makeText(this, "Server Error!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (count >= 2) {
            progressDialog.cancel();
        }
        switch (result.getStatus()) {
            case ApiConstants.StatusCode.S_OK:
                switch (result.getApiCommand()) {

                    case "PROJECTS":
                        count++;
                        mProjectResponse = gson.fromJson(result.getJsonString(), ProjectResponse.class);
                        mProjectList = mProjectResponse.getItem();
                        setProfessionAdapter(mProjectList);
                        mGetProjectListAsyncTask = null;
                        break;
                    case ApiConstants.Command.PROFILE_UPDATE:
                        System.out.println("Huck  "+result.getJsonString());
//                        ProfileInfo profileInfoResponse = gson.fromJson(result.getJsonString(), ProfileInfo.class);
//                        Intent resultIntent = new Intent();
//                        resultIntent.putExtra("profileInfo", (Parcelable) profileInfoResponse);
//                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                        break;
                }
                progressDialog.cancel();
                break;
            case ApiConstants.StatusCode.S_ACCEPTED:
                switch (result.getApiCommand()) {

                    case "PROJECTS":
                        count++;
                        mProjectResponse = gson.fromJson(result.getJsonString(), ProjectResponse.class);
                        mProjectList = mProjectResponse.getItem();
                        setProfessionAdapter(mProjectList);
                        mGetProjectListAsyncTask = null;
                        break;
                    case ApiConstants.Command.PROFILE_UPDATE:
                        System.out.println("Huck  "+result.getJsonString());
                        Toast.makeText(this, "Task added succesfully!", Toast.LENGTH_LONG).show();
//                        ProfileInfo profileInfoResponse = gson.fromJson(result.getJsonString(), ProfileInfo.class);
//                        Intent resultIntent = new Intent();
//                        resultIntent.putExtra("profileInfo", (Parcelable) profileInfoResponse);
//                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                        break;
                }
                progressDialog.cancel();
                break;
            case ApiConstants.StatusCode.S_FORBIDDEN:
                progressDialog.cancel();
                break;
            case ApiConstants.StatusCode.S_BAD_REQUEST:
                progressDialog.cancel();
                break;
            case ApiConstants.StatusCode.S_NOT_ACCEPTED:
                progressDialog.cancel();
                Toast.makeText(this, "Failed to Load Project List", Toast.LENGTH_LONG).show();
                break;
            case ApiConstants.StatusCode.S_NOT_FOUND:
                progressDialog.cancel();
                Toast.makeText(this, "Server Down!", Toast.LENGTH_LONG).show();
                break;
            default:
                progressDialog.cancel();
                Toast.makeText(this, "Server Down!", Toast.LENGTH_LONG).show();
                break;

        }
    }

//    private void setProfessionField(String professionName) {
//        if (mProfessionList != null) {
//            if (!TextUtils.isEmpty(professionName)) {
//                for (int i = 0; i < mProfessionList.size(); i++) {
//                    if (professionName.equals(mProfessionList.get(i).getNodeTitle())) {
//                        mSelectedProfessionId = mProfessionList.get(i).getNodeID();
//                        ptaskProjectEditText.setText(mProfessionList.get(i).getNodeTitle());
//                        return;
//                    }
//                }
//            }
//            if (mProfessionList.size() > 0) {
//                mSelectedProfessionId = mProfessionList.get(0).getNodeID();
//                ptaskProjectEditText.setText(mProfessionList.get(0).getNodeTitle());
//            }
//        }
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                if (isValidProfileInfo()) {
                    updateProfileInfo();
                }
                break;
            case R.id.task_start_date:
                SimpleDateFormat birthDateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
                Date date = new Date(System.currentTimeMillis());
                if (!TextUtils.isEmpty(taskStartDateEditText.getText())) {
                    try {
                        date = birthDateFormatter.parse(taskStartDateEditText.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                final DatePickerDialog dialog = new DatePickerDialog(
                        this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Log.d(TAG, dayOfMonth + " " + month + " " + year);
                        startDate = new Date(year - 1900, month, dayOfMonth);
                        SimpleDateFormat birthDateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
                        taskStartDateEditText.setText(birthDateFormatter.format(startDate));
                    }
                }, 1900 + date.getYear(), date.getMonth(), date.getDay());
                dialog.setCancelable(false);
                dialog.show();
                break;

            case R.id.task_end_date:
                SimpleDateFormat endDateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
                Date date1 = new Date(System.currentTimeMillis());
                if (!TextUtils.isEmpty(taskEndDateEditText.getText())) {
                    try {
                        date1 = endDateFormatter.parse(taskEndDateEditText.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                final DatePickerDialog dialog1 = new DatePickerDialog(
                        this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Log.d(TAG, dayOfMonth + " " + month + " " + year);
                        endDate = new Date(year - 1900, month, dayOfMonth);
                        SimpleDateFormat birthDateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
                        taskEndDateEditText.setText(birthDateFormatter.format(endDate));
                    }
                }, 1900 + date1.getYear(), date1.getMonth(), date1.getDay());
                dialog1.setCancelable(false);
                dialog1.show();
                break;
        }
    }

    private boolean isValidProfileInfo() {

        if (TextUtils.isEmpty(taskNameEditText.getText())) {
            //Log.w(TAG, "Full Name not Entered");
            taskNameEditText.requestFocus();
            taskNameEditText.setError("Task name can't be empty!");
            return false;
        }
        return true;
    }

    private void updateProfileInfo() {
        TaskAddRequest tempProfileInfo = new TaskAddRequest();
        tempProfileInfo.setTaskName(taskNameEditText.getText().toString());
        if (startDate != null) {
            SimpleDateFormat dateOfBirthFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            SimpleDateFormat dateOfBirthDisplayFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
            tempProfileInfo.setStartDate(dateOfBirthFormat.format(startDate));
//            tempProfileInfo.setDateOfBirthDisplay(dateOfBirthDisplayFormat.format(dateOfBirth));
        }
        if (endDate != null) {
            SimpleDateFormat dateOfBirthFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            SimpleDateFormat dateOfBirthDisplayFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
            tempProfileInfo.setEndDate(dateOfBirthFormat.format(endDate));
//            tempProfileInfo.setDateOfBirthDisplay(dateOfBirthDisplayFormat.format(dateOfBirth));
        }
        if (!TextUtils.isEmpty(taskShortDescEditText.getText().toString())) {
            tempProfileInfo.setShortDescription(taskShortDescEditText.getText().toString());
        }
        if (!TextUtils.isEmpty(taskDescEditText.getText().toString())) {
            tempProfileInfo.setLongDescription(taskDescEditText.getText().toString());
        }

//        if (!TextUtils.isEmpty(ptaskProjectEditText.getText()) && mSelectedProjectId != -1) {
//            tempProfileInfo.getProfession().setNodeID(mSelectedProfessionId);
//            tempProfileInfo.getProfession().setNodeTitle(ptaskProjectEditText.getText().toString());
//        }


        if (!TextUtils.isEmpty(taskCostEditText.getText().toString())) {
            tempProfileInfo.setCost(Long.valueOf(taskCostEditText.getText().toString()));
        }

        Gson gson = new GsonBuilder().create();
        String jsonBody = gson.toJson(tempProfileInfo);



        HttpRequestPostAsyncTask updateProfileAsyncTask = new HttpRequestPostAsyncTask(ApiConstants.Command.PROFILE_UPDATE,
                "https://whispering-springs-63266.herokuapp.com/task/add", jsonBody, this, this);
        updateProfileAsyncTask.addHeader("Authorization", accessToken);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating task");
        progressDialog.setCancelable(false);
        progressDialog.show();
        updateProfileAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


    }
}
