package edu.ualr.cpsc4399.cbroset.upandappem.Exercise;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by connorroset on 1/27/17.
 */
public class ExerciseInfo {

    private int exercise_id;
    private String exercise_name, instructions;

    public ExerciseInfo(int exercise_id, String exercise_name, String instructions){
        this.exercise_id = exercise_id;
        this.exercise_name = exercise_name;
        this.instructions = instructions;

    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public String getExercise_name() {
        return exercise_name;
    }

    public void setExercise_name(String exercise_name) {
        this.exercise_name = exercise_name;
    }
}
