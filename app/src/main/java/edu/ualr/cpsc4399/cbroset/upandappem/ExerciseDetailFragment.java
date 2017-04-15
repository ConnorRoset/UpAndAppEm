package edu.ualr.cpsc4399.cbroset.upandappem;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.util.Calendar;

import edu.ualr.cpsc4399.cbroset.upandappem.DatabaseConnectors.DownloadExerciseInfo;
import edu.ualr.cpsc4399.cbroset.upandappem.DatabaseConnectors.DownloadExerciseRegimens;
import edu.ualr.cpsc4399.cbroset.upandappem.DatabaseConnectors.UpdateExerciseRegimen;
import edu.ualr.cpsc4399.cbroset.upandappem.Exercise.ExerciseInfo;
import edu.ualr.cpsc4399.cbroset.upandappem.Exercise.ExerciseRegimen;
import edu.ualr.cpsc4399.cbroset.upandappem.Exercise.InfoReg;

/**
 * A fragment representing a single Exercise detail screen.
 * This fragment is either contained in a {@link ExerciseListActivity}
 * in two-pane mode (on tablets) or a {@link ExerciseDetailActivity}
 * on handsets.
 */
public class ExerciseDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    //Build an infoReg object from the recieved bundle
    private InfoReg infoReg;
    private Bundle bundle;

    //Visual objects
    Button finish, startSets, finishSets;
    TextView reps, sets, instructions;
    ToggleButton complete;
    int setIndex;
    Calendar startTime, endTime, durationTime;
    int exerciseQuality = ExerciseRegimen.QUALITY.values().length -1; //needs to be bound between 0-9;

    public ExerciseDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = this.getArguments();
        infoReg = (InfoReg) bundle.get("EXERCISE_INFO");
        getActivity().setTitle(infoReg.getExerciseInfo().getExercise_name());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.exercise_detail, container, false);
        return rootView;
    }

    private void updateButtonText() {
        startSets.setText("Set number");
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        reps = (TextView) getActivity().findViewById(R.id.exercise_detail_rep_textView);
        String temp = "Reps: " + String.valueOf(infoReg.getExerciseRegimen().getExercise_reps());
        reps.setText(temp);

        sets = (TextView) getActivity().findViewById(R.id.exercise_detail_set_textView);
        temp = "Sets: " + String.valueOf(infoReg.getExerciseRegimen().getExercise_set());
        sets.setText(temp);

        instructions = (TextView) getActivity().findViewById(R.id.exercise_detail_instructions_textView);
        instructions.setText(infoReg.getExerciseInfo().getInstructions());

        //Time tools
        startTime = Calendar.getInstance();
        endTime = Calendar.getInstance();
        durationTime = Calendar.getInstance();
        //toggle button
        complete = (ToggleButton) getActivity().findViewById(R.id.exercise_detail_complete_toggleButton);
        complete.setTextOff("Mark complete");
        complete.setTextOn("Complete");
        complete.setEnabled(false);

        if (infoReg.getExerciseRegimen().isComplete()) {
            complete.setChecked(true);
        } else {
            complete.setChecked(false);
        }
        complete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    finish.setEnabled(true);

                } else {
                    finish.setEnabled(false);
                }
            }
        });

        //start and finish buttons for toggle
        setIndex = 0;

        //startSets.setText("Set " + setIndex+1 + " start");
        startSets = (Button) getActivity().findViewById(R.id.set_start_button);
        startSets.setText("Set " + (setIndex + 1) + " start");
        startSets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startSets.setEnabled(false);
                finishSets.setEnabled(true);
                startTime = Calendar.getInstance();
            }
        });


        finishSets = (Button) getActivity().findViewById(R.id.set_finish_button);
        finishSets.setEnabled(false);
        finishSets.setText("Set " + (setIndex + 1) + " finish");
        finishSets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTime = Calendar.getInstance();
                // durationTime = Calendar.getInstance();
                durationTime.setTimeInMillis(endTime.getTimeInMillis() - startTime.getTimeInMillis());

                //check if time is less than 5 seconds
                if (durationTime.getTimeInMillis() < 5000) {
                    //Toast.makeText(getContext(), "BAD!", Toast.LENGTH_SHORT).show();
                    //They have failed it because they are clicking too quickly
                    int factor = 10/infoReg.getExerciseRegimen().getExercise_set();
                    exerciseQuality = exerciseQuality - factor;
                    if (exerciseQuality < 0) {
                        exerciseQuality = 0;
                    }

                }

                //check if greater than 1 minute per set
                else if (durationTime.getTimeInMillis() > 60000) {
                    //simple decrement to decrease overall score.
                    //this would need more extensive analysis from a PT to determine good scores.
                    //bounds check, as well
                    exerciseQuality--;
                    if(exerciseQuality< 0){
                        exerciseQuality = 0;
                    }
                }
                //exerciseQuality = 9;
                setIndex++;
                if (setIndex >= infoReg.getExerciseRegimen().getExercise_set()) {
                    startSets.setEnabled(false);
                    finishSets.setEnabled(false);
                    complete.setEnabled(true);
                } else {
                    startSets.setEnabled(true);
                    finishSets.setEnabled(false);
                    startSets.setText("Set " + (setIndex + 1) + " start");
                    finishSets.setText("Set " + (setIndex + 1) + " finish");
                }
            }
        });


        //finish button
        finish = (Button) getActivity().findViewById(R.id.exercise_detail_finish_button);
        if (infoReg.getExerciseRegimen().isComplete()) {
            finish.setEnabled(true);
        } else {
            finish.setEnabled(false);
        }
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoReg.getExerciseRegimen().setComplete(true);
                infoReg.getExerciseRegimen().setExercise_quality(ExerciseRegimen.QUALITY.values()[exerciseQuality]);
                infoReg.getExerciseRegimen().setTime_updated(Calendar.getInstance());
                Intent intent = new Intent();
                Bundle rBundle = new Bundle();
                //populate the bundle with the result
                String url = "";
                url = ExerciseListActivity.ROOT_URL + "/exercise_regimen";
                url = url.trim();

                //update the database with the completed exercise
                new UpdateExerciseRegimen(infoReg, (ExerciseDetailActivity) getActivity()).execute(url);


                rBundle.putBoolean(DownloadExerciseRegimens.COMPLETE, infoReg.getExerciseRegimen().isComplete());
                rBundle.putInt(DownloadExerciseRegimens.REGIMEN_ID, infoReg.getExerciseRegimen().getRegimen_id());
                rBundle.putSerializable(DownloadExerciseRegimens.TIME_UPDATED, Calendar.getInstance());
                intent.putExtras(rBundle);
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
            }
        });

    }
}
