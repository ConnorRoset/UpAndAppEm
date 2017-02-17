package edu.ualr.cpsc4399.cbroset.upandappem.DatabaseConnectors;

import android.os.AsyncTask;
import android.provider.CalendarContract;
import android.text.format.DateFormat;
import android.util.JsonReader;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
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


    private List<ExerciseRegimen> exerciseRegimens = new ArrayList<ExerciseRegimen>();
    private ExerciseListActivity activity;
    String url1 = "";
    public DownloadExerciseRegimens(ExerciseListActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(activity.getApplicationContext(), "Starting", Toast.LENGTH_SHORT).show();
    }

    protected List<ExerciseRegimen> doInBackground(String... url) {

        try {
            url1 = url[0];
            InputStream input = new URL(url1).openStream();
            exerciseRegimens = readJsonStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exerciseRegimens;
    }

    @Override
    protected void onPostExecute(List<ExerciseRegimen> exerciseRegimens) {

        //callback from main activity to set up the exercise regimens
        activity.setExerciseRegimens(exerciseRegimens);
        Toast.makeText(activity.getApplicationContext(), url1, Toast.LENGTH_SHORT).show();

    }


    public List<ExerciseRegimen> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readExerciseRegimenArray(reader);
        } finally {
            reader.close();
        }
    }

    public List readExerciseRegimenArray(JsonReader reader) throws IOException {
        List exerciseRegimens = new ArrayList();

        reader.beginObject();
        String result = reader.nextName();
        reader.beginArray();


        while (reader.hasNext()) {
            exerciseRegimens.add(readExerciseRegimen(reader));
        }


        reader.endArray();
        return exerciseRegimens;
    }

    public ExerciseRegimen readExerciseRegimen(JsonReader reader) throws IOException {
        int exercise_id = 0, exercise_reps = 0, exercise_set = 0, patient_id = 0, regimen_id = 0, therapist_id = 0;
        int exercise_quality = 0;
        boolean complete = false;
        Calendar due_date = Calendar.getInstance();
        Calendar time_updated = Calendar.getInstance();
        ExerciseRegimen.QUALITY quality;
        quality = ExerciseRegimen.QUALITY.values()[(exercise_quality)];

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("complete")) {
                complete = reader.nextBoolean();
            } else if (name.equals("due_date")) {
                String tempDate = reader.nextString();
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
                //worry about this later, just use the getInstance Calendar for now

//                try {
//                    due_date.setTime(sdf.parse(tempDate));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
            } else if (name.equals("exercise_id")) {
                exercise_id = reader.nextInt();

            } else if (name.equals("exercise_quality")) {
                exercise_quality = reader.nextInt();
                quality = ExerciseRegimen.QUALITY.values()[(exercise_quality)];
            } else if (name.equals("exercise_reps")) {
                exercise_reps = reader.nextInt();
            } else if (name.equals("exercise_set")) {
                exercise_set = reader.nextInt();
            } else if (name.equals("patient_id")) {
                patient_id = reader.nextInt();
            } else if (name.equals("regimen_id")) {
                regimen_id = reader.nextInt();
            } else if (name.equals("therapist_id")) {
                therapist_id = reader.nextInt();
            } else if (name.equals("time_updated")) {

                //don't actually do anything with it
                //reader.skipValue();
                time_updated = Calendar.getInstance();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new ExerciseRegimen(exercise_id, exercise_reps, exercise_set, patient_id, regimen_id, therapist_id, quality, complete, due_date, time_updated);
    }


}
