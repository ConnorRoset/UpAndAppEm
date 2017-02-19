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

import edu.ualr.cpsc4399.cbroset.upandappem.DatabaseConnectors.DownloadExerciseRegimens;
import edu.ualr.cpsc4399.cbroset.upandappem.Exercise.ExerciseInfo;
import edu.ualr.cpsc4399.cbroset.upandappem.Exercise.ExerciseRegimen;
import edu.ualr.cpsc4399.cbroset.upandappem.Messages.MessagesActivity;
import edu.ualr.cpsc4399.cbroset.upandappem.Settings.SettingsActivity;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * An activity representing a list of Exercises. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ExerciseDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ExerciseListActivity extends AppCompatActivity {


    private boolean mTwoPane; //for whether it is tablet or not

    //variables throughout the app
    private List<ExerciseRegimen> exerciseRegimens;
    private static final String ROOT_URL = "http://10.0.2.2:5000";
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

        //recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.exercise_list_recyclerView);
        assert recyclerView != null;
        setupRecyclerView(recyclerView);


        //check if user is logged in, else try to populate the list
        isLoggedIn = sharedPreferences.getBoolean(LOGGED_IN, false);
       // Toast.makeText(this, String.valueOf(isLoggedIn), Toast.LENGTH_SHORT).show();

        if (isLoggedIn) {
            //
            getWorkoutsFromDatabase();
            recyclerView.getAdapter().notifyDataSetChanged();
        } else {

            Toast.makeText(this, "Log in via the settings on the top right", Toast.LENGTH_SHORT).show();

        }

        //check for tablet layout
        if (findViewById(R.id.exercise_detail_container) != null) {
            mTwoPane = true;
        }
    }

    public void getWorkoutsFromDatabase() {
        //Here is where all the information regarding the workout fetching from the database will be
        try {
            //attempt to populate the exerciseRegimens with the getWorkouts function,
            //  getWorkoutsFromDatabase();
            String url = ROOT_URL + "/exercise_regimen/" + sharedPreferences.getString(USER_ID, "");
            //Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
            //DownloadExerciseRegimens der =
            new DownloadExerciseRegimens(this).execute(url);
            //der.execute(url);



        } catch (UnknownError e) {
            Toast.makeText(this, "Connection to database failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    public void setExerciseRegimens(List<ExerciseRegimen> exerciseRegimens) {
//        if(exerciseRegimens.isEmpty()){
//            Toast.makeText(this, "Empty from download", Toast.LENGTH_SHORT).show();
//        }
        this.exerciseRegimens.addAll(exerciseRegimens);
    }


    @Override
    public void onResume() {
        super.onResume();
        refreshScreen();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SETTINGS_ACTIVITY_RESULT) {
            if (resultCode == RESULT_OK) {
                //check for successful login, then setup recyclerview if necessary
                if (sharedPreferences.contains(LOGGED_IN)) {
                    Toast.makeText(getApplicationContext(), "logged in", Toast.LENGTH_SHORT).show();
                    getWorkoutsFromDatabase();
//                    setupRecyclerView(recyclerView);
                } else {
                    Toast.makeText(getApplicationContext(), "logged out", Toast.LENGTH_SHORT).show();
                    exerciseRegimens.clear();


                }
                recyclerView.getAdapter().notifyDataSetChanged();
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
//        if(exerciseRegimens.isEmpty()){
//            Toast.makeText(getApplicationContext(), "Oops", Toast.LENGTH_SHORT).show();
//        }
    }

    //recyclerview setup information
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new ExerciseRegimenRecyclerViewAdapter(exerciseRegimens));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    public class ExerciseRegimenRecyclerViewAdapter
            extends RecyclerView.Adapter<ExerciseRegimenRecyclerViewAdapter.ViewHolder> {

        private final List<ExerciseRegimen> mExerciseRegimens;

        public ExerciseRegimenRecyclerViewAdapter(List<ExerciseRegimen> items) {
            this.mExerciseRegimens = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            CardView view = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_card_card_layout, parent, false);
            //this will get changed to the card layout file
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            holder.title.setText(String.valueOf(exerciseRegimens.get(position).getExercise_id()));
            String FormattedDate = android.text.format.DateFormat.getLongDateFormat(getApplicationContext()).format(exerciseRegimens.get(position).getDue_date().getTime());
            holder.date.setText(FormattedDate);
            holder.mReps.setText((String.valueOf(exerciseRegimens.get(position).getExercise_reps())));
            holder.mSets.setText((String.valueOf(exerciseRegimens.get(position).getExercise_set())));

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle exerciseInfo = new Bundle();

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
            return mExerciseRegimens.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final CardView cardView;
            public final TextView title;
            public final TextView date;
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
