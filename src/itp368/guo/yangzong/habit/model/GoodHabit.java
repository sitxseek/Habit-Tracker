// Yangzong Guo
// ITP 368, Fall 2017
// Final Project
// yangzong@usc.edu
package itp368.guo.yangzong.habit.model;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GoodHabit extends Habit implements Serializable{
    private List<DayOfWeek> days;
    private boolean completed;

    public GoodHabit(String name, List<DayOfWeek> days) {
        super(name);
        this.days = new ArrayList<DayOfWeek>(days);
        completed = false;
    }
    // add the days that were selected when habit was created
    public void addDay(DayOfWeek day) {
        days.add(day);
    }
    // remove a day
    public void removeDay(DayOfWeek day) {
        days.remove(day);
    }
    // get days that habit should show up
    public List<DayOfWeek> getDays() {
        return this.days;
    }
    // set completed to true or false when checkbox is clicked
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    // return whether the habit was completed
    public boolean IsCompleted() {
        return completed;
    }
    // returns true if the habit should show up today
    public boolean isToday() {
        LocalDate today = LocalDate.now();
        java.time.DayOfWeek dayOfWeek = today.getDayOfWeek();
        for (int i = 0; i < days.size(); i++) {
            if (days.get(i).equals(dayOfWeek)) {
                return true;
            }
        }
        return false;
    }
}
