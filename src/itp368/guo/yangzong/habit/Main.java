// Yangzong Guo
// ITP 368, Fall 2017
// Final Project
// yangzong@usc.edu
package itp368.guo.yangzong.habit;

import itp368.guo.yangzong.habit.model.Habit;
import itp368.guo.yangzong.habit.model.HabitSingleton;
import itp368.guo.yangzong.habit.model.StatisticsSingleton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.prefs.Preferences;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // load stored data
        readData();
        // load main screen
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Habit Tracker Application");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        // save singleton objects when closing application
        primaryStage.setOnCloseRequest(event -> {
            // set date last closed
            StatisticsSingleton.getInstance().setDate(LocalDate.now());
            writeData();
        });
    }

    public void writeData() {
        try {
            // write object to file
            FileOutputStream fos = new FileOutputStream("data.txt");
            FileOutputStream fos2 = new FileOutputStream("stats.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
            oos.writeObject(HabitSingleton.getInstance());
            oos2.writeObject(StatisticsSingleton.getInstance());
            oos.close();
            oos2.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
    // reads data from file
    public void readData() {
        try {
            FileInputStream fileIn = new FileInputStream("data.txt");
            FileInputStream fileIn2 = new FileInputStream("stats.txt");
            // deserializes primitive data and objects previously written using an ObjectOutputStream
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            ObjectInputStream objectIn2 = new ObjectInputStream(fileIn2);
            // set the HabitSingleton and StatisticsSingleton to the loaded ones from file
            HabitSingleton.getInstance().setInstance((HabitSingleton) objectIn.readObject());
            StatisticsSingleton.getInstance().setInstance((StatisticsSingleton) objectIn2.readObject());
            System.out.println("The Object has been read from the file");
            objectIn.close();
            objectIn2.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        updateStats();
    }
    // update file with new object
    public void updateStats() {
        // get the current date
        LocalDate date = LocalDate.now();
        // gets number of days that has passed since user opened application
        long daysBetween = ChronoUnit.DAYS.between(StatisticsSingleton.getInstance().getDate(), date);
        System.out.println(daysBetween);
        StatisticsSingleton statistics = StatisticsSingleton.getInstance();
        HabitSingleton habits = HabitSingleton.getInstance();
        // if one day has passed, update statistics
        if (daysBetween == 1) {
            if (statistics.isPerfectDay(HabitSingleton.getInstance().getGoodHabits())) {
                statistics.incrementStreak();
                statistics.incrementTotalPerfectDays();
            } else {
                StatisticsSingleton.getInstance().resetStreak();
            }
            for (int i = 0; i < habits.getBadHabits().size(); i++) {
                habits.getBadHabits().get(i).addDay(1);
                habits.getBadHabits().get(i).addTotal();
                habits.getBadHabits().get(i).resetDailyCount();
            }
            for (int i = 0; i < habits.getGoodHabits().size(); i++) {
                habits.getGoodHabits().get(i).setCompleted(false);
            }
            // if more than one day has passed, that means user did not complete any habits during those days,
            // so reset the current streak
        } else if (daysBetween > 1 && HabitSingleton.getInstance().getGoodHabits().size() != 0) {
            statistics.resetStreak();
            for (int i = 0; i < habits.getGoodHabits().size(); i++) {
                habits.getGoodHabits().get(i).setCompleted(false);
            }
            for (int i = 0; i < habits.getBadHabits().size(); i++) {
                habits.getBadHabits().get(i).addDay(daysBetween);
                habits.getBadHabits().get(i).addTotal();
                habits.getBadHabits().get(i).resetDailyCount();
            }
            // more than one day has passed but user did not have any good habits to accomplish, so don't reset streak
        } else if (daysBetween > 1) {
            for (int i = 0; i < habits.getBadHabits().size(); i++) {
                habits.getBadHabits().get(i).addDay(daysBetween);
                habits.getBadHabits().get(i).addTotal();
                habits.getBadHabits().get(i).resetDailyCount();
            }
            for (int i = 0; i < habits.getGoodHabits().size(); i++) {
                habits.getGoodHabits().get(i).setCompleted(false);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
