package edu.ualr.cpsc4399.cbroset.upandappem.Exercise;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by connorroset on 1/27/17.
 */
public class Exercise {

    public enum QUALITY {POOR, FAIR, GOOD, EXCELLENT}

    private int reps, sets;
    private String title;
    private Calendar date;
    private QUALITY quality;
    private boolean compelete;

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public QUALITY getQuality() {
        return quality;
    }

    public void setQuality(QUALITY quality) {
        this.quality = quality;
    }


    public boolean isCompelete() {
        return compelete;
    }

    public void setCompelete(boolean compelete) {
        this.compelete = compelete;
    }

    public Exercise(String title, Calendar date, int sets, int reps) {
        this.title = title;
        this.date = date;
        this.sets = sets;
        this.reps = reps;
    }


}
