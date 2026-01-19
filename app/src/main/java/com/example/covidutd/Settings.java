package com.example.covidutd;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView profile_settings = this.findViewById(R.id.profile_settings);
        TextView mGoBack = (TextView) findViewById(R.id.goBack);

        mGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MapActivity.class));
                finish();
            }
        });

        profile_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileSettings.class));
                finish();
            }
        });
    }

    public void logout(View view) {
        AlertDialog.Builder altdial = new AlertDialog.Builder(this);
        altdial.setMessage("Are you sure you want to disconnect from your account?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = altdial.create();
        alert.setTitle("Leaving already? ;(");
        alert.show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MapActivity.class));
        finish();
    }
}
