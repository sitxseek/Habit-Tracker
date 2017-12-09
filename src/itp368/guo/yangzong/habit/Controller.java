// Yangzong Guo
// ITP 368, Fall 2017
// Final Project
// yangzong@usc.edu
package itp368.guo.yangzong.habit;

import itp368.guo.yangzong.habit.model.BadHabit;
import itp368.guo.yangzong.habit.model.GoodHabit;
import itp368.guo.yangzong.habit.model.Habit;
import itp368.guo.yangzong.habit.model.HabitSingleton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Controller {
    @FXML
    private BorderPane root;
    @FXML
    private Button statsBtn;
    @FXML
    private Button createBtn;
    @FXML
    private Label dateLabel;
    @FXML
    private ListView<GoodHabit> goodHabitsList;
    @FXML
    private ListView<BadHabit> badHabitsList;
    @FXML
    private Button goodEditBtn;
    @FXML
    private Button goodDeleteBtn;
    @FXML
    private Button badEditBtn;
    @FXML
    private Button badDeleteBtn;
    @FXML
    private Tab goodTab;
    @FXML
    private Tab badTab;
    @FXML
    private MenuBar menu;
    @FXML
    private Menu language;
    @FXML
    private Menu theme;

    static boolean english = true;
    static boolean light = true;

    public Controller() {
        // no args controller
    }

    @FXML
    public void initialize() {
        // set language
        if (english) {
            setEnglish();
        } else {
            setFrench();
        }
        // set theme
        if (light) {
            setLightTheme();
        } else {
            setDarkTheme();
        }
        // get current date
        Date now = new Date();
        StringBuilder sb = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        // set date label
        sb.append("It's " + new SimpleDateFormat("EEEE").format(now) + ", "
                + new SimpleDateFormat("MMMM").format(now)
                + " " + String.valueOf(dayOfMonth));
        dateLabel.setText(sb.toString());

        // load habit lists
        loadHabits();
    }
    // load existing habits from singleton
    public void loadHabits() {
        List<GoodHabit> good = HabitSingleton.getInstance().getGoodHabits();
        List<BadHabit> bad = HabitSingleton.getInstance().getBadHabits();
        ObservableList<GoodHabit> goodHabits = FXCollections.observableArrayList(good);
        ObservableList<BadHabit> badHabits = FXCollections.observableArrayList(bad);
        goodHabitsList.setItems(goodHabits);
        badHabitsList.setItems(badHabits);
        // setting custom cell for good habit list
        goodHabitsList.setCellFactory(new Callback<ListView<GoodHabit>, ListCell<GoodHabit>>() {
            @Override public ListCell<GoodHabit> call(ListView<GoodHabit> list) {
                return new GoodHabitListCell();
            }
        });
        // setting custom cell for bad habit list
        badHabitsList.setCellFactory(new Callback<ListView<BadHabit>, ListCell<BadHabit>>() {
            @Override public ListCell<BadHabit> call(ListView<BadHabit> list) {
                return new BadHabitListCell();
            }
        });
    }
    // switch scenes
    public void handleButtonClick(ActionEvent event) {
        Stage stage = null;
        Parent root = null;
        // switch to create scene or statistics scene depending on which button is clicked
        if (event.getSource() == statsBtn) {
            stage=(Stage) statsBtn.getScene().getWindow();
            try {
                root = FXMLLoader.load(getClass().getResource("statistics.fxml"));
            } catch(IOException e) {
                throw new RuntimeException(e); // this should never happen
            }
        } else if (event.getSource() == createBtn) {
            stage=(Stage) createBtn.getScene().getWindow();
            try {
                root = FXMLLoader.load(getClass().getResource("create.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e); // this should never happen
            }
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    // called when edit or delete button pressed
    public void onEditDeleteAction(ActionEvent event) {
        if (event.getSource() == goodEditBtn) {
            try {
                // get the create controller to pass in parameters
                FXMLLoader loader = new FXMLLoader(getClass().getResource("create.fxml"));
                Pane pane = loader.load();
                CreateController controller = loader.getController();
                controller.initData(goodHabitsList.getSelectionModel().getSelectedIndex());
                // load create scene
                Stage stage = (Stage) goodEditBtn.getScene().getWindow();
                Scene scene = new Scene(pane, 600, 400);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (event.getSource() == goodDeleteBtn) {
            // delete and reload list view
            HabitSingleton.getInstance().removeGoodHabit(goodHabitsList.getSelectionModel().getSelectedIndex());
            loadHabits();
        } else if (event.getSource() == badEditBtn) {
            try {
                // get the create controller to pass in parameters
                FXMLLoader loader = new FXMLLoader(getClass().getResource("create.fxml"));
                Pane pane = loader.load();
                CreateController controller = loader.getController();
                controller.initData(badHabitsList.getSelectionModel().getSelectedIndex());
                // load create scene
                Stage stage = (Stage) badEditBtn.getScene().getWindow();
                Scene scene = new Scene(pane, 600, 400);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (event.getSource() == badDeleteBtn) {
            // delete and reload list view
            HabitSingleton.getInstance().removeBadHabit(badHabitsList.getSelectionModel().getSelectedIndex());
            loadHabits();
        }
    }
    // internationalization
    public void setLanguage(String language, String country) {
        Locale currentLocale;
        ResourceBundle messages;
        currentLocale = new Locale(language, country);
        messages = ResourceBundle.getBundle("messages", currentLocale);

        statsBtn.setText(messages.getString("statistics"));
        createBtn.setText(messages.getString("createHabit"));
        goodTab.setText(messages.getString("goodHabits"));
        badTab.setText(messages.getString("badHabits"));
        goodEditBtn.setText(messages.getString("edit"));
        badEditBtn.setText(messages.getString("edit"));
        goodDeleteBtn.setText(messages.getString("delete"));
        badDeleteBtn.setText(messages.getString("delete"));
    }

    public void setEnglish() {
        english = true;
        Locale currentLocale;
        ResourceBundle messages;
        currentLocale = new Locale("en", "US");
        messages = ResourceBundle.getBundle("messages", currentLocale);

        statsBtn.setText(messages.getString("statistics"));
        createBtn.setText(messages.getString("createHabit"));
        goodTab.setText(messages.getString("goodHabits"));
        badTab.setText(messages.getString("badHabits"));
        goodEditBtn.setText(messages.getString("edit"));
        badEditBtn.setText(messages.getString("edit"));
        goodDeleteBtn.setText(messages.getString("delete"));
        badDeleteBtn.setText(messages.getString("delete"));
    }

    public void setFrench() {
        english = false;
        Locale currentLocale;
        ResourceBundle messages;
        currentLocale = new Locale("fr", "FR");
        messages = ResourceBundle.getBundle("messages", currentLocale);

        statsBtn.setText(messages.getString("statistics"));
        createBtn.setText(messages.getString("createHabit"));
        goodTab.setText(messages.getString("goodHabits"));
        badTab.setText(messages.getString("badHabits"));
        goodEditBtn.setText(messages.getString("edit"));
        badEditBtn.setText(messages.getString("edit"));
        goodDeleteBtn.setText(messages.getString("delete"));
        badDeleteBtn.setText(messages.getString("delete"));
    }

    public void setLightTheme() {
        light = true;
        root.getStylesheets().clear();
    }

    public void setDarkTheme() {
        light = false;
        root.getStylesheets().add("modena_dark.css");
    }
}
