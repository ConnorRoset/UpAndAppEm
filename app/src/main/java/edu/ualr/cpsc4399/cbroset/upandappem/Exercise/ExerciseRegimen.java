package edu.ualr.cpsc4399.cbroset.upandappem.Exercise;

import java.util.ArrayList;

/**
 * Created by connorroset on 1/27/17.
 */

public class ExerciseRegimen {
    private ArrayList<Exercise> exercises;
    int patientID;

    public ExerciseRegimen(int patientID) {
        this.patientID = patientID;
        getExerciseRegimen(patientID);
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    public void removeExercise(Exercise exercise) {
        if (exercises.contains(exercise)) {
            exercises.remove(exercise);
        }
    }

    private void getExerciseRegimen(int patientID) {
        exercises = new ArrayList<>();
        //find patient id on database
        //for each entry on database, add that exercise to the exercise regimen
    }
}
