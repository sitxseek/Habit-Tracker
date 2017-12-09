// Yangzong Guo
// ITP 368, Fall 2017
// Final Project
// yangzong@usc.edu
package itp368.guo.yangzong.habit.model;

import java.io.Serializable;

public class Habit implements Serializable{
    private String name;

    public Habit(String name) {
        this.name = name;
    }
    // return name of the habit
    public String getName() {
        return name;
    }
    // set name of the habit
    public void setName(String name) {
        this.name = name;
    }
}
