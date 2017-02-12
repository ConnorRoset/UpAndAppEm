package edu.ualr.cpsc4399.cbroset.upandappem.Exercise;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by connorroset on 1/27/17.
 */

public class ExerciseRegimen {
    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    private ArrayList<Exercise> exercises;
   private int patientID;

    public ExerciseRegimen(int patientID) {
        this.patientID = patientID;
        getExerciseRegimenFromDatabase(patientID);
    }
    public ExerciseRegimen(int patientID, boolean testing) {
        this.patientID = patientID;
        getExerciseRegimenFromDatabase(patientID);
        setUpSampleExercises();
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    public void removeExercise(Exercise exercise) {
        if (exercises.contains(exercise)) {
            exercises.remove(exercise);
        }
    }
    public Exercise getExerciseAtIndex(int i){
        return exercises.get(i);
    }
    private void getExerciseRegimenFromDatabase(int patientID) {
        exercises = new ArrayList<>();
       // exercises = new ArrayList<>();
        //find patient id on database
        //for each entry on database, add that exercise to the exercise regimen
    }

    private void setUpSampleExercises(){
        //exercises = new ArrayList<>();
        Calendar myCal= Calendar.getInstance();
        myCal.set(2017, Calendar.FEBRUARY, 18);

        Exercise exercise1 = new Exercise("Pushups", myCal, 3, 15);
        exercises.add(exercise1);
        Exercise exercise2  = new Exercise("Crunches", myCal, 4,10);
        exercises.add(exercise2);
        Exercise exercise3 = new Exercise("Squats", myCal, 2, 30);
        exercises.add(exercise3);
    }
}
