// Yangzong Guo
// ITP 368, Fall 2017
// Final Project
// yangzong@usc.edu
package itp368.guo.yangzong.habit;

import itp368.guo.yangzong.habit.model.BadHabit;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class BadHabitListCell extends ListCell<BadHabit> {
    @Override
    public void updateItem(BadHabit item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {
            HBox hbox = new HBox(8);
            Label name = new Label(item.getName());
            Label times = new Label("Times Today: " + item.getCount());
            Label space = new Label(" | ");
            Button plus = new Button("+");
            plus.setStyle("-fx-font: 11 arial");
            plus.setOnAction(e -> {
                item.increment();
                times.setText("Times Today: " + item.getCount());
            });
            Button minus = new Button("-");
            minus.setStyle("-fx-font: 11 arial");
            minus.setPrefWidth(23);
            minus.setOnAction(e -> {
                if (item.getCount() > 0) {
                    item.decrement();
                    times.setText("Times Today: " + item.getCount());
                }
            });
            hbox.getChildren().addAll(plus, minus, name, space, times);
            setGraphic(hbox);
        }
    }
}
