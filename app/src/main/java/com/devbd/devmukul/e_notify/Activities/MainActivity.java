package com.devbd.devmukul.e_notify.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.devbd.devmukul.e_notify.Api.HttpRequestGetAsyncTask;
import com.devbd.devmukul.e_notify.Api.HttpResponseListener;
import com.devbd.devmukul.e_notify.Api.HttpResponseObject;
import com.devbd.devmukul.e_notify.Constants;
import com.devbd.devmukul.e_notify.DataAdapter;
import com.devbd.devmukul.e_notify.Model.Item;
import com.devbd.devmukul.e_notify.Model.ProjectResponse;
import com.devbd.devmukul.e_notify.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HttpResponseListener {

    private HttpRequestGetAsyncTask mProjectTask = null;
    private ProjectResponse mProjectResponseModel;
    public static List<Item> sProjectList = new ArrayList<Item>();
    private RecyclerView mRecyclerView;
    private DataAdapter mAdapter;
    private RelativeLayout progessLayout;
    private GridLayoutManager mLayoutManager;
    private String accessToken;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        preferences = this.getSharedPreferences(Constants.ApplicationTag, Context.MODE_PRIVATE);
        accessToken = preferences.getString(Constants.ACCESS_TOKEN_KEY, "");

        System.out.println("Fuck "+accessToken);

        init();
        updateData();
    }

    private void init() {
        mLayoutManager = new GridLayoutManager(MainActivity.this,1);
        mRecyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        progessLayout =(RelativeLayout) findViewById(R.id.progress_layout);
    }

    private void initViews(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        DataAdapter adapter = new DataAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);

    }

    private void updateData() {
        fetchNews();
    }

    private void fetchNews() {
        progessLayout.setVisibility(View.VISIBLE);
        mProjectTask = new HttpRequestGetAsyncTask("PROJECTS", "https://whispering-springs-63266.herokuapp.com/project/all", MainActivity.this);
        mProjectTask.mHttpResponseListener = this;
        mProjectTask.addHeader("Authorization", accessToken);
        mProjectTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }
    
    @Override
    public void httpResponseReceiver(HttpResponseObject result) {

        if (result == null || result.getStatus() == Constants.HTTP_RESPONSE_STATUS_INTERNAL_ERROR
                || result.getStatus() == Constants.HTTP_RESPONSE_STATUS_NOT_FOUND) {
            mProjectTask = null;
            if (MainActivity.this!= null)
                Toast.makeText(MainActivity.this, "Server Error!", Toast.LENGTH_SHORT).show();
            return;
        }

        Gson gson = new Gson();
        switch (result.getApiCommand()) {

            case "PROJECTS":
                try {
                    if (result.getStatus() == Constants.HTTP_RESPONSE_STATUS_OK) {

                        mProjectResponseModel = gson.fromJson(result.getJsonString(), ProjectResponse.class);
                        sProjectList = mProjectResponseModel.getItem();
                        mAdapter = new DataAdapter(MainActivity.this,sProjectList);
                        mRecyclerView.setAdapter(mAdapter);// set adapter on recyclerview
                        mAdapter.notifyDataSetChanged();
                    } else {
                        if (this != null){}
                        Toast.makeText(MainActivity.this, result.getJsonString(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (this != null)
                        Toast.makeText(MainActivity.this, "Server Error!", Toast.LENGTH_LONG).show();
                }
                progessLayout.setVisibility(View.GONE);
                mProjectTask = null;

                break;
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_project_setup) {
            startActivity(new Intent(getApplicationContext(), ProjectSetupActivity.class));

        } else if (id == R.id.nav_project_manager) {
            startActivity(new Intent(getApplicationContext(), ProjectManagerActivity.class));
        } else if (id == R.id.nav_task) {
            startActivity(new Intent(getApplicationContext(), TaskUpdateActivity.class));
        }else if (id == R.id.nav_project_base_task) {
            startActivity(new Intent(getApplicationContext(), ProjectBaseTaskUpdateActivity.class));
        }else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_loguot) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
