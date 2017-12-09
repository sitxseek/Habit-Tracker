// Yangzong Guo
// ITP 368, Fall 2017
// Final Project
// yangzong@usc.edu
package itp368.guo.yangzong.habit.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class StatisticsSingleton implements Serializable {
    private int streak;
    private int bestStreak;
    private int totalPerfectDays;
    private LocalDate goodDate;
    private LocalDate badDate;
    private LocalDate date;
    private static StatisticsSingleton instance = null;

    protected StatisticsSingleton() {
        // Exists only to defeat instantiation.
        streak = 0;
        bestStreak = 0;
        totalPerfectDays = 0;
        date = LocalDate.now();
        goodDate = LocalDate.now();
        badDate = LocalDate.now();
    }

    public static StatisticsSingleton getInstance() {
        if(instance == null) {
            instance = new StatisticsSingleton();
        }
        return instance;
    }
    // set instance of singleton to one retrieved from file
    public void setInstance(StatisticsSingleton singleton) {
        instance = singleton;
    }
    // increase streak by 1
    public void incrementStreak() {
        streak++;
    }
    // set streak to 0
    public void resetStreak() {
        streak = 0;
    }
    // return streak
    public int getStreak() {
        return streak;
    }
    // return highest streak
    public int getBestStreak() {
        if (streak > bestStreak) {
            bestStreak = streak;
        }
        return bestStreak;
    }
    // returns true if user accomplishes all habits
    public boolean isPerfectDay(List<GoodHabit> goodHabitList) {
        for (int i = 0; i < goodHabitList.size(); i++) {
            if (!goodHabitList.get(i).IsCompleted() && goodHabitList.get(i).isToday()) {
                return false;
            }
        }
        return true;
    }
    // return total number of days user accomplished all habits
    public int getTotalPerfectDays() {
        return totalPerfectDays;
    }
    // increase total perfect days by 1
    public void incrementTotalPerfectDays() {
        totalPerfectDays++;
    }
    // reset stats
    public void reset() {
        streak = 0;
        bestStreak = 0;
        totalPerfectDays = 0;
    }
    // get date when user last reset
    public LocalDate getGoodDate() {
        return goodDate;
    }
    // get date when user last reset
    public LocalDate getBadDate() {
        return badDate;
    }
    // set new date when user resets
    public void setGoodDate(LocalDate date) {
        goodDate = date;
    }
    // set new date when user resets
    public void setBadDate(LocalDate date) {
        badDate = date;
    }
    // get the day user closed program
    public LocalDate getDate() {
        return date;
    }
    // set the date to today
    public void setDate(LocalDate date) {
        this.date = date;
    }
}
