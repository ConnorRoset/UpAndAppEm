package edu.ualr.cpsc4399.cbroset.upandappem.Exercise;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by connorroset on 1/27/17.
 */
public class ExerciseInfo implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.exercise_id);
        dest.writeString(this.exercise_name);
        dest.writeString(this.instructions);
    }

    protected ExerciseInfo(Parcel in) {
        this.exercise_id = in.readInt();
        this.exercise_name = in.readString();
        this.instructions = in.readString();
    }

    public static final Parcelable.Creator<ExerciseInfo> CREATOR = new
            Parcelable.Creator<ExerciseInfo>() {
        @Override
        public ExerciseInfo createFromParcel(Parcel source) {
            return new ExerciseInfo(source);
        }

        @Override
        public ExerciseInfo[] newArray(int size) {
            return new ExerciseInfo[size];
        }
    };
}
