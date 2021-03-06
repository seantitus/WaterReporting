package com.zoinks.waterreporting.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zoinks.waterreporting.R;
import com.zoinks.waterreporting.model.Facade;
import com.zoinks.waterreporting.model.User;
import com.zoinks.waterreporting.model.UserType;

/**
 * Main home screen activity - the first thing the user sees after signing in
 *
 * Created by Nancy on 2/15/17.
 */
public class MainActivity extends AppCompatActivity {
    private final Facade facade = Facade.getInstance();

    private TextView mUsernameView;
    private TextView mPrivilegeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mUsernameView = (TextView) findViewById(R.id.username_label);
        mPrivilegeView = (TextView) findViewById(R.id.privilege_label);

        Button viewList = (Button) findViewById(R.id.view_list);
        viewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchList = new Intent(MainActivity.this, ListActivity.class);
                launchList.putExtra("ManagerList", false);
                startActivity(launchList);
            }
        });

        Button viewQualityList = (Button) findViewById(R.id.view_quality_list);
        if (facade.getCurrentUser().checkPrivilege() == UserType.MANAGER.getPrivilege()) {
            viewQualityList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent launchList = new Intent(MainActivity.this, ListActivity.class);
                    launchList.putExtra("ManagerList", true);
                    startActivity(launchList);
                }
            });
            viewQualityList.setVisibility(View.VISIBLE);
        }

        Button viewMap = (Button) findViewById(R.id.view_map);
        viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchMap = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(launchMap);
            }
        });

        // users can only submit reports if they are not banned
        if (!facade.getCurrentUser().isBanned()) {
            Button submitWaterSourceReport = (Button) findViewById(R.id.submit_source_report);
            submitWaterSourceReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent submitReport = new Intent(MainActivity.this,
                            SubmitWaterSourceReportActivity.class);
                    startActivity(submitReport);
                }
            });

            Button submitWaterQualityReport = (Button) findViewById(R.id.submit_quality_report);
            // make sure only workers and managers can add a new report
            if (facade.getCurrentUser().checkPrivilege() == UserType.MANAGER.getPrivilege()
                    || facade.getCurrentUser().checkPrivilege() == UserType.WORKER.getPrivilege()) {
                submitWaterQualityReport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent submitReport = new Intent(MainActivity.this,
                                SubmitWaterQualityReportActivity.class);
                        startActivity(submitReport);
                    }
                });
                submitWaterQualityReport.setVisibility(View.VISIBLE);
            }

            Button viewHistoricalReport = (Button) findViewById(R.id.view_historical_report);
            // only managers can generate historical reports
            if (facade.getCurrentUser().checkPrivilege() == UserType.MANAGER.getPrivilege()) {
                viewHistoricalReport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent viewHistorical = new Intent(MainActivity.this,
                                HistoricalReportOptionsActivity.class);
                        startActivity(viewHistorical);
                    }
                });
                viewHistoricalReport.setVisibility(View.VISIBLE);
            }
        }

        Button viewAdminPanel = (Button) findViewById(R.id.view_admin_panel);
        // only administrators can view the admin panel
        if (facade.getCurrentUser().checkPrivilege() == UserType.ADMINISTRATOR.getPrivilege()) {
            viewAdminPanel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent viewPanel = new Intent(MainActivity.this, AccountListActivity.class);
                    startActivity(viewPanel);
                }
            });
            viewAdminPanel.setVisibility(View.VISIBLE);
        }

        Button updateProfile = (Button) findViewById(R.id.update_profile);
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchUpdate = new Intent(MainActivity.this, UpdateProfileActivity.class);
                startActivity(launchUpdate);
            }
        });

        Button logout = (Button) findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                facade.logout();
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLabels();
    }

    private void updateLabels() {
        try {
            User current = facade.getCurrentUser();
            mUsernameView.setText(String.format("Username: %s", current.getUsername()));
            UserType privilege = UserType.values()[current.checkPrivilege() / 10 - 1];
            mPrivilegeView.setText(String.format("Privilege: %s", privilege.name()));
        } catch (NullPointerException e) {  // happens when currentUser is null from logout
            finish();
        }
    }
}
