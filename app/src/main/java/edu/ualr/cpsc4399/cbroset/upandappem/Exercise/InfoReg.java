package edu.ualr.cpsc4399.cbroset.upandappem.Exercise;

/**
 * Created by connorroset on 2/19/17.
 */

public class InfoReg {
    public ExerciseRegimen getExerciseRegimen() {
        return exerciseRegimen;
    }

    public void setExerciseRegimen(ExerciseRegimen exerciseRegimen) {
        this.exerciseRegimen = exerciseRegimen;
    }

    public ExerciseInfo getExerciseInfo() {
        return exerciseInfo;
    }

    public void setExerciseInfo(ExerciseInfo exerciseInfo) {
        this.exerciseInfo = exerciseInfo;
    }

    private ExerciseRegimen exerciseRegimen;
    private ExerciseInfo exerciseInfo;
    public InfoReg(ExerciseRegimen exerciseRegimen, ExerciseInfo exerciseInfo){
        this.exerciseRegimen = exerciseRegimen;
        this.exerciseInfo = exerciseInfo;
    }
}
