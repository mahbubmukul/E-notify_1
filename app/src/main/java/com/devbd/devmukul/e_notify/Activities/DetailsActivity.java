package com.devbd.devmukul.e_notify.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.devbd.devmukul.e_notify.R;
import com.devbd.devmukul.e_notify.Utilities;

public class DetailsActivity extends AppCompatActivity {

    private TextView projectNameTextView;
    private TextView projectShortDescTextView;
    private TextView projectStartDateTextView;
    private TextView projectEndDateTextView;
    private TextView projectDescTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        projectNameTextView = (TextView) findViewById(R.id.name);
        projectShortDescTextView = (TextView) findViewById(R.id.short_desc);
        projectStartDateTextView = (TextView) findViewById(R.id.start_date);
        projectEndDateTextView = (TextView) findViewById(R.id.end_date);
        projectDescTextView = (TextView) findViewById(R.id.long_desc);

        Intent ii = getIntent();
        int pos = ii.getIntExtra("Position",0);


        projectNameTextView.setText(MainActivity.sProjectList.get(pos).getProjectName());
        projectShortDescTextView.setText(MainActivity.sProjectList.get(pos).getShortDescription());

        projectDescTextView.setText(MainActivity.sProjectList.get(pos).getLongDescription());

        projectStartDateTextView.setText(Utilities.getDateFormat(MainActivity.sProjectList.get(pos).getStartDate()));

        projectEndDateTextView.setText(Utilities.getDateFormat(MainActivity.sProjectList.get(pos).getEndDate()));

    }

}
