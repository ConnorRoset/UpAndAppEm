package edu.ualr.cpsc4399.cbroset.upandappem;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
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

import edu.ualr.cpsc4399.cbroset.upandappem.DatabaseConnectors.DownloadExerciseRegimens;
import edu.ualr.cpsc4399.cbroset.upandappem.DatabaseConnectors.UpdateExerciseRegimen;
import edu.ualr.cpsc4399.cbroset.upandappem.Exercise.ExerciseInfo;
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
    Button finish;
    TextView reps, sets, instructions;
    ToggleButton complete;


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

    public void onViewCreated(View view, Bundle savedInstanceState) {
        reps = (TextView) getActivity().findViewById(R.id.exercise_detail_rep_textView);
        String temp = "Reps: " + String.valueOf(infoReg.getExerciseRegimen().getExercise_reps());
        reps.setText(temp);

        sets = (TextView) getActivity().findViewById(R.id.exercise_detail_set_textView);
        temp = "Sets: " + String.valueOf(infoReg.getExerciseRegimen().getExercise_set());
        sets.setText(temp);

        instructions = (TextView) getActivity().findViewById(R.id.exercise_detail_instructions_textView);
        instructions.setText(infoReg.getExerciseInfo().getInstructions());

        complete = (ToggleButton) getActivity().findViewById(R.id.exercise_detail_complete_toggleButton);
        complete.setTextOff("Incomplete");
        complete.setTextOn("Complete");
        if(infoReg.getExerciseRegimen().isComplete()){
            complete.setChecked(true);
        } else{
            complete.setChecked(false);
        }
        complete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    finish.setEnabled(true);

                } else{
                    finish.setEnabled(false);
                }
            }
        });

        finish = (Button) getActivity().findViewById(R.id.exercise_detail_finish_button);
        if(infoReg.getExerciseRegimen().isComplete()){
            finish.setEnabled(true);
        } else{
            finish.setEnabled(false);
        }
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoReg.getExerciseRegimen().setComplete(true);

                Intent intent = new Intent();
                Bundle rBundle = new Bundle();
                //populate the bundle with the result
                String url = "";
                url = ExerciseListActivity.ROOT_URL + "/exercise_regimen";
                url = url.trim();

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
