// Yangzong Guo
// ITP 368, Fall 2017
// Final Project
// yangzong@usc.edu
package itp368.guo.yangzong.habit;

import itp368.guo.yangzong.habit.model.GoodHabit;
import itp368.guo.yangzong.habit.model.Habit;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;


public class GoodHabitListCell extends ListCell<GoodHabit> {
    @Override
    public void updateItem(GoodHabit item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {
            // don't display if habit doesn't fall on specified day
            if (item.isToday()) {
                HBox hbox = new HBox(8);
                // checkbox for completion
                CheckBox cb = new CheckBox();
                cb.setSelected(item.IsCompleted());
                cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    public void changed(ObservableValue<? extends Boolean> ov,
                                        Boolean old_val, Boolean new_val) {
                        if (new_val) {
                            item.setCompleted(true);
                        } else {
                            item.setCompleted(false);
                        }
                    }
                });
                Label name = new Label(item.getName());
                hbox.getChildren().addAll(cb, name);
                setGraphic(hbox);
            } else {
                HBox hbox = new HBox(8);
                // checkbox for completion
                CheckBox cb = new CheckBox();
                cb.setSelected(false);
                cb.setDisable(true);
                Label name = new Label(item.getName());
                name.setTextFill(Color.web("#C0C0C0"));
                hbox.getChildren().addAll(cb, name);
                setGraphic(hbox);
            }
        }
    }
}

