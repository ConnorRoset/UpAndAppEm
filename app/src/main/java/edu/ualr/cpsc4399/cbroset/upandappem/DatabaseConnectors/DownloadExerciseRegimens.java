package edu.ualr.cpsc4399.cbroset.upandappem.DatabaseConnectors;

import android.os.AsyncTask;
import android.text.format.DateUtils;
import android.widget.Toast;


import org.json.JSONObject;
import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.ualr.cpsc4399.cbroset.upandappem.Exercise.ExerciseRegimen;
import edu.ualr.cpsc4399.cbroset.upandappem.ExerciseListActivity;


/**
 * Created by connorroset on 2/13/17.
 */
public class DownloadExerciseRegimens extends AsyncTask<String, Integer, List<ExerciseRegimen>> {
    public static final String COMPLETE = "complete";
    public static final String DUE_DATE = "due_date";
    public static final String EXERCISE_ID = "exercise_id";
    public static final String EXERCISE_QUALITY = "exercise_quality";
    public static final String EXERCISE_REPS = "exercise_reps";
    public static final String EXERCISE_SET = "exercise_set";
    public static final String PATIENT_ID = "patient_id";
    public static final String REGIMEN_ID = "regimen_id";
    public static final String THERAPIST_ID = "therapist_id";
    public static final String TIME_UPDATED = "time_updated";

    private List<ExerciseRegimen> exerciseRegimens = new ArrayList<ExerciseRegimen>();
    private ExerciseListActivity activity;
    URL url1;
    String response = "";
    URLConnection urlconn = null;
    BufferedReader bufferedReader = null;

    public DownloadExerciseRegimens(ExerciseListActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
    }

    protected List<ExerciseRegimen> doInBackground(String... url) {
        response = url[0];

        try {
            url1 = new URL(url[0]);

            urlconn = url1.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            //weird structure, so grab the first array
            JSONArray exerciseRegimenArray = new JSONArray(stringBuilder.toString());

            //then grab the second array, it happens to be at index 1
            JSONArray sub = exerciseRegimenArray.getJSONArray(1);
            for (int i = 0; i < sub.length(); i++) {
                JSONObject obj = sub.getJSONObject(i);
                ExerciseRegimen er = getExerciseRegimenFromJSON(obj);

                //only add it if it is today's workout
                if (DateUtils.isToday(er.getDue_date().getTimeInMillis())) {
                    exerciseRegimens.add(er);
                }
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return exerciseRegimens;
    }

    private ExerciseRegimen getExerciseRegimenFromJSON(JSONObject obj) {
        ExerciseRegimen er = null;
        boolean complete;
        Calendar dueDate = Calendar.getInstance(), timeUpdated = Calendar.getInstance();
        int eID, reps, set, pID, rID, tID;
        ExerciseRegimen.QUALITY quality;
        try {
            //complete
            complete = obj.getBoolean(COMPLETE);

            //due date
            String tempDate = obj.getString(DUE_DATE);
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
            try {
                dueDate.setTime(sdf.parse(tempDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //Exercise Id
            eID = obj.getInt(EXERCISE_ID);

            //EXERCISEQUALITY
            quality = ExerciseRegimen.QUALITY.TEN;

            //exercise reps
            reps = obj.getInt(EXERCISE_REPS);

            //exercise sets
            set = obj.getInt(EXERCISE_SET);

            //patient ID
            pID = obj.getInt(PATIENT_ID);

            //regimenID
            rID = obj.getInt(REGIMEN_ID);

            //therapist ID
            tID = obj.getInt(THERAPIST_ID);

            //don't update the time updated for now, it's okay
            //no need to do the time updated, that pulls system time on the database
            er = new ExerciseRegimen(eID, reps, set, pID, rID, tID, quality,
                    complete, dueDate, timeUpdated);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return er;
    }

    @Override
    protected void onPostExecute(List<ExerciseRegimen> exerciseRegimens) {

        //callback from main activity to set up the exercise regimens
        activity.setExerciseRegimens(exerciseRegimens);
        String temp = "";
        if (exerciseRegimens.size() == 0) {
            temp = "No workouts Today!";
            Toast.makeText(activity.getApplicationContext(), temp, Toast.LENGTH_LONG).show();
        } else {
            //only after that one has finished calling can we attempt to fetch the
            // info for the exercises
            activity.getExerciseInfoFromDatabase();
        }
    }
}
