package edu.ualr.cpsc4399.cbroset.upandappem.ExerciseCatalog;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.ualr.cpsc4399.cbroset.upandappem.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseCatalog extends Fragment {


    public ExerciseCatalog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise_catalog, container, false);
    }

}
