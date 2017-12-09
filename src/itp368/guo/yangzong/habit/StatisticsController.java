// Yangzong Guo
// ITP 368, Fall 2017
// Final Project
// yangzong@usc.edu
package itp368.guo.yangzong.habit;

import itp368.guo.yangzong.habit.model.BadHabit;
import itp368.guo.yangzong.habit.model.HabitSingleton;
import itp368.guo.yangzong.habit.model.StatisticsSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class StatisticsController {
    @FXML
    private BorderPane root;
    @FXML
    private Label currentStreak;
    @FXML
    private Label bestStreak;
    @FXML
    private Label totalPerfect;
    @FXML
    private Button goodResetBtn;
    @FXML
    private Button badResetBtn;
    @FXML
    private Label sinceGoodDate;
    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private Button backBtn;
    @FXML
    private Button backBtn2;
    @FXML
    private Label currentLabel;
    @FXML
    private Label bestLabel;
    @FXML
    private Label totalLabel;
    @FXML
    private Tab goodTab;
    @FXML
    private Tab badTab;

    private ObservableList<String> names = FXCollections.observableArrayList();

    public StatisticsController() {
        // no args controller
    }

    @FXML
    public void initialize() {
        // set theme and language based on what was selected in main scene
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
        // initialize stats for good habits
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        currentStreak.setText(Integer.toString(StatisticsSingleton.getInstance().getStreak()));
        bestStreak.setText(Integer.toString(StatisticsSingleton.getInstance().getBestStreak()));
        totalPerfect.setText(Integer.toString(StatisticsSingleton.getInstance().getTotalPerfectDays()));
        sinceGoodDate.setText("Since " + dtf.format(StatisticsSingleton.getInstance().getGoodDate()));
        // initialize bar chart for bad habits
        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName("Since "  + dtf.format(StatisticsSingleton.getInstance().getBadDate()));
        for (int i = 0; i < HabitSingleton.getInstance().getBadHabits().size(); i++) {
            names.add(HabitSingleton.getInstance().getBadHabits().get(i).getName());
            dataSeries.getData().add(new XYChart.Data(HabitSingleton.getInstance().getBadHabits().get(i).getName(), HabitSingleton.getInstance().getBadHabits().get(i).getAverage()));
        }
        barChart.getData().add(dataSeries);
    }
    // reset labels on good stats screen
    public void onGoodResetAction() {
        // confirm want to reset
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Reset stats for good habits?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(null);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            // reset stats
            StatisticsSingleton.getInstance().setGoodDate(LocalDate.now());
            StatisticsSingleton.getInstance().reset();
            currentStreak.setText(Integer.toString(StatisticsSingleton.getInstance().getStreak()));
            bestStreak.setText(Integer.toString(StatisticsSingleton.getInstance().getBestStreak()));
            totalPerfect.setText(Integer.toString(StatisticsSingleton.getInstance().getTotalPerfectDays()));
        }
    }
    // reset labels on bad stats screen
    public void onBadResetAction() {
        // confirm want to reset
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Reset stats for bad habits?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(null);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            // reset stats
            StatisticsSingleton.getInstance().setBadDate(LocalDate.now());
            List<BadHabit> badHabits = HabitSingleton.getInstance().getBadHabits();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            XYChart.Series dataSeries = new XYChart.Series();
            dataSeries.setName("Since " + dtf.format(StatisticsSingleton.getInstance().getBadDate()));
            for (int i = 0; i < badHabits.size(); i++) {
                badHabits.get(i).reset();
                dataSeries.getData().add(new XYChart.Data(badHabits.get(i).getName(), badHabits.get(i).getAverage()));
            }
            barChart.getData().setAll(dataSeries);
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

        currentLabel.setText(messages.getString("currentStreak"));
        bestLabel.setText(messages.getString("bestStreak"));
        totalLabel.setText(messages.getString("total"));
        backBtn.setText(messages.getString("back"));
        backBtn2.setText(messages.getString("back"));
        badResetBtn.setText(messages.getString("reset"));
        goodResetBtn.setText(messages.getString("reset"));
        goodTab.setText(messages.getString("goodStats"));
        badTab.setText(messages.getString("badStats"));
    }
}
