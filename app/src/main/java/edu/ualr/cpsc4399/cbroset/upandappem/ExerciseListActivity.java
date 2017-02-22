package edu.ualr.cpsc4399.cbroset.upandappem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import edu.ualr.cpsc4399.cbroset.upandappem.DatabaseConnectors.DownloadExerciseInfo;
import edu.ualr.cpsc4399.cbroset.upandappem.DatabaseConnectors.DownloadExerciseRegimens;
import edu.ualr.cpsc4399.cbroset.upandappem.Exercise.ExerciseInfo;
import edu.ualr.cpsc4399.cbroset.upandappem.Exercise.ExerciseRegimen;
import edu.ualr.cpsc4399.cbroset.upandappem.Exercise.InfoReg;
import edu.ualr.cpsc4399.cbroset.upandappem.Exercise.InfoRegRVAdapter;
import edu.ualr.cpsc4399.cbroset.upandappem.Messages.MessagesActivity;
import edu.ualr.cpsc4399.cbroset.upandappem.Settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * An activity representing a list of Exercises. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ExerciseDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ExerciseListActivity extends AppCompatActivity {


    public boolean mTwoPane; //for whether it is tablet or not

    //variables throughout the app
    private List<ExerciseInfo> exerciseInfos;
    private List<ExerciseRegimen> exerciseRegimens;
    private List<InfoReg> infoRegs;
    private static final String ROOT_URL = "http://10.0.2.2:5000";
    //starting other activity codes:
    public static final int SETTINGS_ACTIVITY_RESULT = 0;
    public static final int EXERCISE_DETAIL = 1;

    //sharedprefs information:
    public static final String MY_PREFS = "MyPrefs";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "UserName";
    public static final String LOGGED_IN = "loggedIn";
    SharedPreferences sharedPreferences;

    //fields on screen
//    TextView noWorkouts;
    RecyclerView recyclerView;
    boolean isLoggedIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        //shared preferences loading
        sharedPreferences = getSharedPreferences(MY_PREFS, MODE_PRIVATE);


        //Build up the list of exercise regimens, it should be unpopulated at this point
        exerciseRegimens = new ArrayList<>();
        exerciseInfos = new ArrayList<>();
        infoRegs = new ArrayList<>();

        //recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.exercise_list_recyclerView);
        assert recyclerView != null;
        setupRecyclerView(recyclerView);


        //check if user is logged in, else try to populate the list
        isLoggedIn = sharedPreferences.getBoolean(LOGGED_IN, false);
        // Toast.makeText(this, String.valueOf(isLoggedIn), Toast.LENGTH_SHORT).show();

        if (isLoggedIn) {
            getExerciseRegimensFromDatabase();
            recyclerView.getAdapter().notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Log in via the settings on the top right", Toast.LENGTH_SHORT).show();
        }

        //check for tablet layout
        if (findViewById(R.id.exercise_detail_container) != null) {
            mTwoPane = true;
        }
    }

    public void getExerciseRegimensFromDatabase() {

    //Download the exerciseRegimens for today, as of right now it will only
        try {

            String url = ROOT_URL + "/exercise_regimen/" + sharedPreferences.getString(USER_ID, "");
            new DownloadExerciseRegimens(this).execute(url);


        } catch (UnknownError e) {
            Toast.makeText(this, "Connection to database failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    public void getExerciseInfoFromDatabase() {

        for (ExerciseRegimen er : exerciseRegimens) {

            try {

                String url = ROOT_URL + "/exercise/" + er.getExercise_id();
                new DownloadExerciseInfo(this).execute(url);


            } catch (UnknownError e) {
                Toast.makeText(this, "Connection to database failed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        //
    }

    public void setExerciseRegimens(List<ExerciseRegimen> exerciseRegimens) {

        //this may be a good function in which to manipulate the date function on the exercises to
        //display for the day
        this.exerciseRegimens.addAll(exerciseRegimens);
    }

    public void addExerciseInfoToRegimen(ExerciseInfo exerciseInfo) {
        for (ExerciseRegimen er : exerciseRegimens) {
            if (er.getExercise_id() == exerciseInfo.getExercise_id()) {
                infoRegs.add(new InfoReg(er, exerciseInfo));
            }
        }
        //after a change has happened, notify the recylerview to update
        refreshScreen();
    }


    @Override
    public void onResume() {
        super.onResume();
        refreshScreen();
    }
    @Override
    public void onPause(){
        super.onPause();
        //Toast.makeText(this, "Main paused", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onStop(){
        super.onStop();
        //Toast.makeText(this, "Main stopped", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SETTINGS_ACTIVITY_RESULT) {
            if (resultCode == RESULT_OK) {
                //check for successful login, then setup recyclerview if necessary
                if (sharedPreferences.contains(LOGGED_IN)) {
                    Toast.makeText(getApplicationContext(), "logged in", Toast.LENGTH_SHORT).show();
                    getExerciseRegimensFromDatabase();
//                    setupRecyclerView(recyclerView);
                } else {
                    Toast.makeText(getApplicationContext(), "logged out", Toast.LENGTH_SHORT).show();
                    exerciseRegimens.clear();
                }
            }
        }
        else if (requestCode == EXERCISE_DETAIL) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                //get the exercise id & the complete status from the bundle

                if (bundle.containsKey(DownloadExerciseRegimens.COMPLETE)){
                    //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                    //this is where the async task to the database will need to be made to update the exercise
                }
                updateExerciseRegimen(bundle.getInt(DownloadExerciseRegimens.REGIMEN_ID), bundle.getBoolean(DownloadExerciseRegimens.COMPLETE));
            }
        }
        refreshScreen();
    }

    private void updateExerciseRegimen(int regimen_id, boolean complete) {
        for (InfoReg ir : infoRegs) {
            if (ir.getExerciseRegimen().getRegimen_id() == regimen_id) {
                ir.getExerciseRegimen().setComplete(complete);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //add a new settings detail here to login and what not
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, SETTINGS_ACTIVITY_RESULT);
            return true;
        } else if (id == R.id.action_message) {
            //build a messaging activity here
            startActivity(new Intent(this, MessagesActivity.class));
            return true;
        } else if (id == R.id.action_refresh) {
            refreshScreen();
        }
        return super.onOptionsItemSelected(item);
    }

    public void refreshScreen() {
        //refresh the recyclerview with the notifydatachanged
        recyclerView.getAdapter().notifyDataSetChanged();
    }


        //recyclerview setup information

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        InfoRegRVAdapter adapter = new InfoRegRVAdapter(infoRegs, getApplicationContext());
        //recyclerView.setAdapter(new ExerciseRegimenRecyclerViewAdapter(exerciseRegimens));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }


}
