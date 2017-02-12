package com.zoinks.waterreporting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Intent launchLogin = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(launchLogin);
            }
        });
    }

}
