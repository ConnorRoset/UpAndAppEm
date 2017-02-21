package edu.ualr.cpsc4399.cbroset.upandappem.Exercise;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by connorroset on 2/19/17.
 */

public class InfoReg implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.exerciseRegimen, flags);
        dest.writeParcelable(this.exerciseInfo, flags);
    }

    protected InfoReg(Parcel in) {
        this.exerciseRegimen = in.readParcelable(ExerciseRegimen.class.getClassLoader());
        this.exerciseInfo = in.readParcelable(ExerciseInfo.class.getClassLoader());
    }

    public static final Parcelable.Creator<InfoReg> CREATOR = new Parcelable.Creator<InfoReg>() {
        @Override
        public InfoReg createFromParcel(Parcel source) {
            return new InfoReg(source);
        }

        @Override
        public InfoReg[] newArray(int size) {
            return new InfoReg[size];
        }
    };
}
