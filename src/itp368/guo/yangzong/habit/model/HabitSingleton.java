// Yangzong Guo
// ITP 368, Fall 2017
// Final Project
// yangzong@usc.edu
package itp368.guo.yangzong.habit.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HabitSingleton implements Serializable{
    private static HabitSingleton instance = null;
    private List<GoodHabit> goodHabits;
    private List<BadHabit> badHabits;

    protected HabitSingleton() {
        // Exists only to defeat instantiation.
        goodHabits = new ArrayList<GoodHabit>();
        badHabits = new ArrayList<BadHabit>();
    }

    public static HabitSingleton getInstance() {
        if(instance == null) {
            instance = new HabitSingleton();
        }
        return instance;
    }
    // set the instance of singleton to one retrieved in file
    public void setInstance(HabitSingleton singleton) {
        instance = singleton;
    }
    // add a habit to list of good habits
    public void addGoodHabit(GoodHabit habit) {
        goodHabits.add(habit);
    }
    // retur good habit at position
    public GoodHabit getGoodAt(int position) {
        return goodHabits.get(position);
    }
    // remove good habit at position
    public void removeGoodHabit(int position) {
        goodHabits.remove(position);
    }
    // return list of good habits
    public List<GoodHabit> getGoodHabits() {
        return goodHabits;
    }
    // add a habit to list of bad habits
    public void addBadHabit(BadHabit habit) {
        badHabits.add(habit);
    }
    // return bad habit at position
    public BadHabit getBadAt(int position) {
        return badHabits.get(position);
    }
    // remove bad habit at position
    public void removeBadHabit(int position) {
        badHabits.remove(position);
    }
    // get list of bad habits
    public List<BadHabit> getBadHabits() {
        return badHabits;
    }
    // update habit at index
    public void updateGood(int index, GoodHabit habit) {
        if (index >= 0 && index < goodHabits.size()){
            goodHabits.set(index, habit);
        }
    }
    // update habit at index
    public void updateBad(int index, BadHabit habit) {
        if (index >= 0 && index < badHabits.size()){
            badHabits.set(index, habit);
        }
    }
}
