package com.zoinks.waterreporting.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zoinks.waterreporting.R;
import com.zoinks.waterreporting.model.Facade;
import com.zoinks.waterreporting.model.User;

public class ViewUserActivity extends AppCompatActivity {

    private final Facade facade = Facade.getInstance();
    private User mUser;

    private TextView mUsernameView;
    private TextView mNameView;
    private TextView mIsBlocked;
    private TextView mIsBanned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        mUser = facade.getUserByUsername(getIntent().getStringExtra("username"));

        mUsernameView = (TextView) findViewById(R.id.username);
        mUsernameView.setText(String.format("Username: %s", getIntent().getStringExtra("username")));
        mNameView = (TextView) findViewById(R.id.name);
        mNameView.setText(String.format("Name: %s", getIntent().getStringExtra("name")));
        mIsBlocked = (TextView) findViewById(R.id.is_blocked);
        mIsBlocked.setText(String.format("Is Blocked? %s", getIntent().getStringExtra("isBlocked")));
        mIsBanned = (TextView) findViewById(R.id.is_banned);
        mIsBanned.setText(String.format("Is Blocked? %s", getIntent().getStringExtra("isBanned")));

        Button unblockAccount = (Button) findViewById(R.id.unblock_account);
        if (mUser.isLockedOut()) {
            unblockAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mUser.unblockAccount();
                    Toast.makeText(getApplicationContext(), String.format("User %s can now login",
                            mUser.getUsername()), Toast.LENGTH_LONG).show();

                    // update info
                    mIsBlocked.setText(String.format("Is Blocked? %s", mUser.isLockedOut() ? "Yes" : "No"));
                }
            });
            unblockAccount.setVisibility(View.VISIBLE);
        }

        Button banAccount = (Button) findViewById(R.id.ban_account);
        if (!mUser.isBanned()) {
            banAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mUser.toggleBan();
                    Toast.makeText(getApplicationContext(), String.format("User %s is now banned from submitting reports",
                            mUser.getUsername()), Toast.LENGTH_LONG).show();

                    // update info
                    mIsBanned.setText(String.format("Is Banned? %s", mUser.isBanned() ? "Yes" : "No"));
                }
            });
            banAccount.setVisibility(View.VISIBLE);
        }

        Button unbanAccount = (Button) findViewById(R.id.unban_account);
        if (mUser.isBanned()) {
            unbanAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mUser.toggleBan();
                    Toast.makeText(getApplicationContext(), String.format("User %s is now permitted to submit reports",
                            mUser.getUsername()), Toast.LENGTH_LONG).show();

                    // update info
                    mIsBanned.setText(String.format("Is Banned? %s", mUser.isBanned() ? "Yes" : "No"));
                }
            });
            unbanAccount.setVisibility(View.VISIBLE);
        }

        Button deleteAccount = (Button) findViewById(R.id.delete_account);
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facade.deleteUser(mUser.getUsername());
                Toast.makeText(getApplicationContext(), String.format("User %s deleted",
                        mUser.getUsername()), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUsernameView.setText(String.format("Username: %s", mUser.getUsername()));
        mNameView.setText(String.format("Name: %s", mUser.getName()));
        mIsBlocked.setText(String.format("Is Blocked? %s", mUser.isLockedOut() ? "Yes" : "No"));
        mIsBanned.setText(String.format("Is Banned? %s", mUser.isBanned() ? "Yes" : "No"));
    }
}
