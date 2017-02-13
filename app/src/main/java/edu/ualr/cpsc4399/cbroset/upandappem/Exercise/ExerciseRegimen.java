package edu.ualr.cpsc4399.cbroset.upandappem.Exercise;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by connorroset on 1/27/17.
 */

public class ExerciseRegimen {

    public enum QUALITY {ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN}

    private int exercise_id, exercise_reps, exercise_set, patient_id, regimen_id, therapist_id;
    private QUALITY exercise_quality;
    private boolean complete;
    private Calendar due_date, time_updated;

    public ExerciseRegimen(int exercise_id, int exercise_reps, int exercise_set, int patient_id, int regimen_id, int therapist_id, QUALITY exercise_quality, boolean complete, Calendar due_date, Calendar time_updated) {
        this.exercise_id = exercise_id;
        this.exercise_reps = exercise_reps;
        this.exercise_set = exercise_set;
        this.patient_id = patient_id;
        this.regimen_id = regimen_id;
        this.therapist_id = therapist_id;
        this.exercise_quality = exercise_quality;
        this.complete = complete;
        this.due_date = due_date;
        this.time_updated = time_updated;
    }

    //All the getters and setters
    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public int getExercise_reps() {
        return exercise_reps;
    }

    public void setExercise_reps(int exercise_reps) {
        this.exercise_reps = exercise_reps;
    }

    public int getExercise_set() {
        return exercise_set;
    }

    public void setExercise_set(int exercise_set) {
        this.exercise_set = exercise_set;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getRegimen_id() {
        return regimen_id;
    }

    public void setRegimen_id(int regimen_id) {
        this.regimen_id = regimen_id;
    }

    public int getTherapist_id() {
        return therapist_id;
    }

    public void setTherapist_id(int therapist_id) {
        this.therapist_id = therapist_id;
    }

    public QUALITY getExercise_quality() {
        return exercise_quality;
    }

    public void setExercise_quality(QUALITY quality) {
        this.exercise_quality = quality;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public Calendar getDue_date() {
        return due_date;
    }

    public void setDue_date(Calendar due_date) {
        this.due_date = due_date;
    }

    public Calendar getTime_updated() {
        return time_updated;
    }

    public void setTime_updated(Calendar time_updated) {
        this.time_updated = time_updated;
    }
}
