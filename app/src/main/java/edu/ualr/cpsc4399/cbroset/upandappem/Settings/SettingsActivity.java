package edu.ualr.cpsc4399.cbroset.upandappem.Settings;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import edu.ualr.cpsc4399.cbroset.upandappem.ExerciseListActivity;
import edu.ualr.cpsc4399.cbroset.upandappem.R;

public class SettingsActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText nameET, emailET, userIDET, usernameET;
    String name, email, userID, username;
    Button submit, logout;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //fields on the screen;
        nameET = (EditText) findViewById(R.id.settings_name_edit_text);
        emailET = (EditText) findViewById(R.id.settings_email_edit_text);
        userIDET = (EditText) findViewById(R.id.settings_user_id_edit_text);
        usernameET = (EditText) findViewById(R.id.settings_username_edit_text);
        submit = (Button) findViewById(R.id.settings_submit_button);
        logout = (Button) findViewById(R.id.settings_logout_button);

        //shared preferences stuff
        sharedPreferences = getSharedPreferences(ExerciseListActivity.MY_PREFS, MODE_PRIVATE);

        if(!sharedPreferences.getBoolean(ExerciseListActivity.LOGGED_IN, false)) {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(nameET.getText().toString()) ||
                            TextUtils.isEmpty(emailET.getText().toString()) ||
                            TextUtils.isEmpty(userIDET.getText().toString()) ||
                            TextUtils.isEmpty((usernameET.getText().toString()))) {
                        Toast.makeText(getApplicationContext(),
                                "You have left a field empty", Toast.LENGTH_SHORT).show();
                    } else {
                        //populate the shared prefs with the data
                        name = nameET.getText().toString().trim();
                        email = emailET.getText().toString().trim();
                        userID = userIDET.getText().toString().trim();
                        username = usernameET.getText().toString().trim();

                        editor = sharedPreferences.edit();
                        editor.putString(ExerciseListActivity.NAME, name);
                        editor.putString(ExerciseListActivity.EMAIL, email);
                        editor.putString(ExerciseListActivity.USER_ID, userID);
                        editor.putString(ExerciseListActivity.USER_NAME, username);
                        editor.putBoolean(ExerciseListActivity.LOGGED_IN, true);
                        editor.apply();
                        setResult(RESULT_OK);
                        finish();
                    }
                }
            });
        } else {
            submit.setEnabled(false);
            nameET.setText(sharedPreferences.getString(ExerciseListActivity.NAME, ""));
            nameET.setEnabled(false);
            nameET.setTextColor(Color.BLACK);

            emailET.setText(sharedPreferences.getString(ExerciseListActivity.EMAIL, ""));
            emailET.setEnabled(false);
            emailET.setTextColor(Color.BLACK);

            userIDET.setText(sharedPreferences.getString(ExerciseListActivity.USER_ID, ""));
            userIDET.setEnabled(false);
            userIDET.setTextColor(Color.BLACK);

            usernameET.setText(sharedPreferences.getString(ExerciseListActivity.USER_NAME, ""));
            usernameET.setEnabled(false);
            usernameET.setTextColor(Color.BLACK);

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    setResult(RESULT_OK);
                    finish();

                }
            });
        }

    }
}
