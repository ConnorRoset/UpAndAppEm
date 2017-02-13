package edu.ualr.cpsc4399.cbroset.upandappem.DatabaseConnectors;

import android.os.AsyncTask;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.ualr.cpsc4399.cbroset.upandappem.Exercise.ExerciseRegimen;
import edu.ualr.cpsc4399.cbroset.upandappem.ExerciseListActivity;

/**
 * Created by connorroset on 2/13/17.
 */

public class DownloadExerciseRegimens extends AsyncTask<String, Integer, List<ExerciseRegimen>> {




    private List<ExerciseRegimen> exerciseRegimens = new ArrayList<ExerciseRegimen>();
    private ExerciseListActivity activity;

    public DownloadExerciseRegimens(ExerciseListActivity activity){
        this.activity = activity;
    }


    protected List<ExerciseRegimen> doInBackground(String... url) {

        exerciseRegimens.add(new ExerciseRegimen(1, 3, 2, 2, 1, 1, ExerciseRegimen.QUALITY.ONE, false, Calendar.getInstance(), Calendar.getInstance()));
        return exerciseRegimens;
    }

    @Override
    protected void onPostExecute(List<ExerciseRegimen> exerciseRegimens) {

        activity.setExerciseRegimens(exerciseRegimens);
        //delegate.processFinished(exerciseRegimens);
    }
}
