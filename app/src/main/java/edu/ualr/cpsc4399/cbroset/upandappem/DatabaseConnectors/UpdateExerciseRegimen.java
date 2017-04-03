package edu.ualr.cpsc4399.cbroset.upandappem.DatabaseConnectors;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import edu.ualr.cpsc4399.cbroset.upandappem.Exercise.InfoReg;
import edu.ualr.cpsc4399.cbroset.upandappem.ExerciseDetailActivity;
import edu.ualr.cpsc4399.cbroset.upandappem.ExerciseDetailFragment;
import edu.ualr.cpsc4399.cbroset.upandappem.ExerciseListActivity;

/**
 * Created by connorroset on 2/21/17.
 */

public class UpdateExerciseRegimen extends AsyncTask<String, Integer, InfoReg> {
    ExerciseDetailActivity activity;
    private InfoReg infoReg = null;
    HttpURLConnection connection = null;
    JSONObject json = null;
    URL url1;
    DataOutputStream dataOut = null;
    BufferedWriter writer = null;

    public UpdateExerciseRegimen(InfoReg infoReg, ExerciseDetailActivity activity) {
        this.activity = activity;
        this.infoReg = infoReg;

    }

    @Override
    public void onPreExecute() {
        //

    }

    @Override
    protected InfoReg doInBackground(String... url) {
        try {
            //Connect to the database
            //build a json to update the database
            json = new JSONObject();
            json.put("complete", infoReg.getExerciseRegimen().isComplete());
            json.put("regimen_id", infoReg.getExerciseRegimen().getRegimen_id());
            json.put("exercise_quality", infoReg.getExerciseRegimen().getExercise_quality().ordinal());
            //right here is where we need to add the put for the exercise Quality
            //open the connection
            url1 = new URL(url[0]);
            connection = (HttpURLConnection) url1.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            //send the request
//            String response = json.toString();
            dataOut = new DataOutputStream(connection.getOutputStream());
            dataOut.writeBytes(json.toString());
            //special line to force a connection.
            connection.getResponseCode();
            dataOut.flush();
            dataOut.close();


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return infoReg;
    }

    @Override
    protected void onPostExecute(InfoReg infoReg) {

        //Toast.makeText(activity, connection.getRequestMethod(), Toast.LENGTH_LONG).show();

        //Toast.makeText(, "", Toast.LENGTH_SHORT).show();
    }
}
