package edu.ualr.cpsc4399.cbroset.upandappem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import edu.ualr.cpsc4399.cbroset.upandappem.Exercise.Exercise;
import edu.ualr.cpsc4399.cbroset.upandappem.Exercise.ExerciseRegimen;
import edu.ualr.cpsc4399.cbroset.upandappem.Messages.MessagesActivity;
import edu.ualr.cpsc4399.cbroset.upandappem.Settings.SettingsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * An activity representing a list of Exercises. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ExerciseDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ExerciseListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private ExerciseRegimen exerciseRegimen;

    //starting other activity codes:
    public static final int SETTINGS_ACTIVITY_RESULT = 0;

    //sharedprefs information:
    public static final String MY_PREFS = "MyPrefs";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "UserName";
    public static final String LOGGED_IN = "loggedIn";
    SharedPreferences sharedPreferences;

    //fields on screen
    TextView introText;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_exercise_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        introText = (TextView) findViewById(R.id.activity_exercise_list_todays_workout);
        recyclerView = (RecyclerView) findViewById(R.id.exercise_list);
        assert recyclerView != null;
        //recyclerView.setLayoutParams(new LinearLayoutManager(get));
        //setupRecyclerView(recyclerView);
        sharedPreferences = getSharedPreferences(MY_PREFS, MODE_PRIVATE);


        //set up the shared preferences to get everything started

        //set up the recyclerview


        if (findViewById(R.id.exercise_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //check to see if the recyler view is good!
        if (sharedPreferences.getBoolean(LOGGED_IN, false)) {
            String intro = "Workout for: " + sharedPreferences.getString(NAME, "default name");
            introText.setText(intro);

            assert recyclerView != null;
            //setupRecyclerView(recyclerView);

        } else {
            //put up a message saying to login
            Toast.makeText(this, "Log in via the settings on the top right", Toast.LENGTH_SHORT).show();

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
            startActivity(intent);
            return true;
        } else if (id == R.id.action_message) {
            //build a messaging activity here
            startActivity(new Intent(this, MessagesActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        //recyclerView.setAdapter(new ExerciseRegimenRecyclerViewAdapter(exerciseRegimen.getExercises()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    public class ExerciseRegimenRecyclerViewAdapter
            extends RecyclerView.Adapter<ExerciseRegimenRecyclerViewAdapter.ViewHolder> {

        private final ArrayList<Exercise> mExercises;

        public ExerciseRegimenRecyclerViewAdapter(ArrayList<Exercise> items) {
            this.mExercises = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            CardView view = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_card_card_layout, parent, false);
            //this will get changed to the card layout file
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            // holder.mExercise = exerciseRegimen.getExercises().get(position);
            holder.mExercise = mExercises.get(position);

//            holder.title.setText(holder.mExercise.getTitle());
//            holder.date.setText(holder.mExercise.getDueDate().getTime().toString());
//            holder.mReps.setText(String.valueOf(holder.mExercise.getReps()));
//            holder.mSets.setText(String.valueOf(holder.mExercise.getSets()));

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle exerciseInfo = new Bundle();
//                    exerciseInfo.putString("TITLE", holder.mExercise.getTitle());
//                    exerciseInfo.putString("DATE", holder.mExercise.getDueDate().getTime().toString());
//                    exerciseInfo.putInt("REPS", holder.mExercise.getReps());
//                    exerciseInfo.putInt("SETS", holder.mExercise.getSets());

                    if (mTwoPane) {
                        //Bundle arguments = new Bundle();
                        //arguments.putString(ExerciseDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        ExerciseDetailFragment fragment = new ExerciseDetailFragment();
                        fragment.setArguments(exerciseInfo);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.exercise_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ExerciseDetailActivity.class);
                        intent.putExtras(exerciseInfo);
                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mExercises.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final CardView cardView;
            public final TextView title;
            public final TextView date;
            public Exercise mExercise;
            public TextView mReps;
            public TextView mSets;

            public ViewHolder(CardView view) {
                super(view);
                cardView = (CardView) view.findViewById(R.id.exercise_card_view);
                title = (TextView) view.findViewById(R.id.exercise_card_title);
                date = (TextView) view.findViewById(R.id.exercise_card_date);
                mReps = (TextView) view.findViewById(R.id.exercise_card_reps);
                mSets = (TextView) view.findViewById(R.id.exercise_card_sets);
            }


        }
    }
}
