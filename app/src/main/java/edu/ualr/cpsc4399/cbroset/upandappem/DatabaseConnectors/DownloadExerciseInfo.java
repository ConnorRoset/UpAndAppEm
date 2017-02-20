package edu.ualr.cpsc4399.cbroset.upandappem.DatabaseConnectors;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import edu.ualr.cpsc4399.cbroset.upandappem.Exercise.ExerciseInfo;
import edu.ualr.cpsc4399.cbroset.upandappem.Exercise.ExerciseRegimen;
import edu.ualr.cpsc4399.cbroset.upandappem.ExerciseListActivity;

/**
 * Created by connorroset on 2/19/17.
 */

public class DownloadExerciseInfo extends AsyncTask<String, Integer, ExerciseInfo> {
    public static final String EXERCISE_NAME = "exercise_name";
    public static final String INSTRUCTIONS = "instructions";

    private ExerciseListActivity activity;
    private ExerciseInfo exerciseInfo = null;


    //connection info
   // String urly;
    URLConnection urlconn = null;
    BufferedReader bufferedReader = null;
    public DownloadExerciseInfo(ExerciseListActivity activity){
        this.activity = activity;
    }

    @Override
    protected void onPreExecute(){
        //preexecution stagee
        super.onPreExecute();
    }

    protected ExerciseInfo doInBackground(String... url){
       // urly = url[0];
        try{
            urlconn = new URL(url[0]).openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));

            //build the JSON string
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);

            }
            JSONArray arry = new JSONArray(stringBuilder.toString());
            JSONObject obj = arry.getJSONObject(1);
            String name, instructions;
            int id;
            name = obj.getString(EXERCISE_NAME);
            instructions = obj.getString(INSTRUCTIONS);
            id = obj.getInt(DownloadExerciseRegimens.EXERCISE_ID);
            exerciseInfo = new ExerciseInfo(id,name,instructions);
            //urly = exerciseInfo.getExercise_name();
        } catch (IOException | JSONException e){
            e.printStackTrace();
        }

        return exerciseInfo;
    }

    @Override
    protected void onPostExecute(ExerciseInfo exerciseInfo){
        //Toast.makeText(activity.getApplicationContext(), urly, Toast.LENGTH_SHORT).show();
        activity.addExerciseInfoToRegimen(exerciseInfo);
    }
}
