// Yangzong Guo
// ITP 368, Fall 2017
// Final Project
// yangzong@usc.edu
package itp368.guo.yangzong.habit;

import itp368.guo.yangzong.habit.model.BadHabit;
import itp368.guo.yangzong.habit.model.GoodHabit;
import itp368.guo.yangzong.habit.model.Habit;
import itp368.guo.yangzong.habit.model.HabitSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.*;


public class CreateController {
    private List<DayOfWeek> days;
    @FXML
    private Pane root;
    @FXML
    private TextField habitText;
    @FXML
    private Button saveBtn;
    @FXML
    private Button backBtn;
    @FXML
    private RadioButton goodRadio;
    @FXML
    private RadioButton badRadio;
    @FXML
    private ToggleButton sun;
    @FXML
    private ToggleButton mon;
    @FXML
    private ToggleButton tue;
    @FXML
    private ToggleButton wed;
    @FXML
    private ToggleButton thu;
    @FXML
    private ToggleButton fri;
    @FXML
    private ToggleButton sat;
    @FXML
    private Label name;
    @FXML
    private Label repeat;

    private boolean isGood;

    private boolean update;

    private int position;

    public CreateController() {
        // no args controller
    }

    @FXML
    public void initialize() {
        // set theme and language specified in main scene
        if (Controller.light) {
            root.getStylesheets().clear();
        } else {
            root.getStylesheets().add("modena_dark.css");
        }
        if (Controller.english) {
            setLanguage("en", "EN");
        } else {
            setLanguage("fr", "FR");
        }
        ToggleGroup group = new ToggleGroup();
        goodRadio.setToggleGroup(group);
        badRadio.setToggleGroup(group);
        days = new ArrayList<DayOfWeek>(Arrays.asList(DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY));
        sun.setSelected(true);
        mon.setSelected(true);
        tue.setSelected(true);
        wed.setSelected(true);
        thu.setSelected(true);
        fri.setSelected(true);
        sat.setSelected(true);
        isGood = true;
    }
    // called when editing a habit
    public void initData(int position) {
        this.position = position;
        update = true;
    }
    // toggle buttons for days of week
    public void handleRadioAction(ActionEvent event) {
        if (event.getSource() == goodRadio) {
            isGood = true;
            sun.setDisable(false);
            mon.setDisable(false);
            tue.setDisable(false);
            wed.setDisable(false);
            thu.setDisable(false);
            fri.setDisable(false);
            sat.setDisable(false);
        } else if (event.getSource() == badRadio) {
            isGood = false;
            sun.setDisable(true);
            mon.setDisable(true);
            tue.setDisable(true);
            wed.setDisable(true);
            thu.setDisable(true);
            fri.setDisable(true);
            sat.setDisable(true);
        }
    }
    // add or remove to days list
    public void handleToggleAction(ActionEvent event) {
        if (event.getSource() == sun) {
            if (sun.isSelected()) {
                days.add(DayOfWeek.SUNDAY);
            } else {
                days.remove(DayOfWeek.SUNDAY);
            }
        } else if (event.getSource() == mon) {
            if (mon.isSelected()) {
                days.add(DayOfWeek.MONDAY);
            } else {
                days.remove(DayOfWeek.MONDAY);
            }
        } else if (event.getSource() == tue) {
            if (tue.isSelected()) {
                days.add(DayOfWeek.TUESDAY);
            } else {
                days.remove(DayOfWeek.TUESDAY);
            }
        } else if (event.getSource() == wed) {
            if (wed.isSelected()) {
                days.add(DayOfWeek.WEDNESDAY);
            } else {
                days.remove(DayOfWeek.WEDNESDAY);
            }
        } else if (event.getSource() == thu) {
            if (thu.isSelected()) {
                days.add(DayOfWeek.THURSDAY);
            } else {
                days.remove(DayOfWeek.THURSDAY);
            }
        } else if (event.getSource() == fri) {
            if (fri.isSelected()) {
                days.add(DayOfWeek.FRIDAY);
            } else {
                days.remove(DayOfWeek.FRIDAY);
            }
        } else {
            if (sat.isSelected()) {
                days.add(DayOfWeek.SATURDAY);
            } else {
                days.remove(DayOfWeek.SATURDAY);
            }
        }
    }
    // save the habit
    public void onSaveAction() {
        HabitSingleton singleton = HabitSingleton.getInstance();
        // if scene loaded from edit, update the selected habit
        if (update) {
            if (isGood) {
                GoodHabit habit = new GoodHabit(habitText.getText(), days);
                singleton.updateGood(position, habit);
            } else {
                BadHabit habit = new BadHabit(habitText.getText());
                singleton.updateBad(position, habit);
            }
        } else { // creating new habit
            if (isGood) {
                GoodHabit habit = new GoodHabit(habitText.getText(), days);
                singleton.addGoodHabit(habit);
            } else {
                BadHabit habit = new BadHabit(habitText.getText());
                singleton.addBadHabit(habit);
            }
        }
        try { // go back to main screen
            Stage stage = (Stage) saveBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("main.fxml")));
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // go back to main screen
    public void onBackAction() {
        try {
            Stage stage = (Stage) backBtn.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("main.fxml")));
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // internationalization
    public void setLanguage(String language, String country) {
        Locale currentLocale;
        ResourceBundle messages;
        currentLocale = new Locale(language, country);
        messages = ResourceBundle.getBundle("messages", currentLocale);

        name.setText(messages.getString("nameOfHabit"));
        goodRadio.setText(messages.getString("goodHabit"));
        badRadio.setText(messages.getString("badHabit"));
        repeat.setText(messages.getString("repeat"));
        sun.setText(messages.getString("sun"));
        mon.setText(messages.getString("mon"));
        tue.setText(messages.getString("tue"));
        wed.setText(messages.getString("wed"));
        thu.setText(messages.getString("thu"));
        fri.setText(messages.getString("fri"));
        sat.setText(messages.getString("sat"));
        saveBtn.setText(messages.getString("save"));
        backBtn.setText(messages.getString("back"));
    }
}
