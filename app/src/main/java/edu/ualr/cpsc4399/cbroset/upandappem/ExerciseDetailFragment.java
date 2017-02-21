package edu.ualr.cpsc4399.cbroset.upandappem;

import android.app.Activity;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    /**
     * The dummy content this fragment is presenting.
     */
    private InfoReg infoReg;
    private Bundle bundle;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
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

        // Show the dummy content as text in a TextView.
//        if (mExerciseInfo != null) {
//            //((TextView) rootView.findViewById(R.id.exercise_detail)).setText(mExercise.getTitle());
//        }

        return rootView;
    }
}
