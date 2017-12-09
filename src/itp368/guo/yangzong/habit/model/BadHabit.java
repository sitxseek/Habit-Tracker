// Yangzong Guo
// ITP 368, Fall 2017
// Final Project
// yangzong@usc.edu
package itp368.guo.yangzong.habit.model;

import java.io.Serializable;

public class BadHabit extends Habit implements Serializable{
    private int count;
    private int totalCount;
    private int days;

    public BadHabit(String name) {
        super(name);
        count = 0;
        days = 0;
    }
    // increase count by 1
    public void increment() {
        count++;
    }
    // decrease count by 1
    public void decrement() {
        count--;
    }
    // return count
    public int getCount() {
        return count;
    }
    // return average of habit
    public double getAverage() {
        if (days == 0) return 0;
        return totalCount / days;
    }
    // add to the total count
    public void addTotal() {
        totalCount += count;
    }
    // increment day
    public void addDay(long days) {
        this.days += days;
    }
    // reset the daily count after each day
    public void resetDailyCount() {
        count = 0;
    }
    // reset stats
    public void reset() {
        count = 0;
        totalCount = 0;
        days = 0;
    }

}
