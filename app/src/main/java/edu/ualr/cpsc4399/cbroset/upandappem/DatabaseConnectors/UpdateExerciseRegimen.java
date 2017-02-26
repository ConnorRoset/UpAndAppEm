package edu.ualr.cpsc4399.cbroset.upandappem.DatabaseConnectors;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
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


    public UpdateExerciseRegimen(InfoReg infoReg, ExerciseDetailActivity activity){
        this.activity = activity;
        this.infoReg = infoReg;

    }
    @Override
    public void onPreExecute(){
        //

    }
    @Override
    protected InfoReg doInBackground(String... url){
        try {
            //Connect to the database
            url1 = new URL(url[0]);

            connection = (HttpURLConnection) url1.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(50);
            connection.connect();
            //connection.setRequestProperty("Accept", "application/json");
           // DataOutputStream dataOut = new DataOutputStream(connection.getOutputStream());
            OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
            //Build the string/json
            json = new JSONObject();
           // {"complete":true, "regimen_id":3}
            json.put("complete", infoReg.getExerciseRegimen().isComplete());
            json.put("regimen_id", infoReg.getExerciseRegimen().getRegimen_id());

           // dataOut.writeBytes(json.toString());
            osw.write(json.toString());
            osw.flush();
            osw.close();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return infoReg;
    }
    @Override
    protected void onPostExecute(InfoReg infoReg){
        Toast.makeText(activity, url1.toString(), Toast.LENGTH_LONG).show();
        //Toast.makeText(, "", Toast.LENGTH_SHORT).show();
    }
}
