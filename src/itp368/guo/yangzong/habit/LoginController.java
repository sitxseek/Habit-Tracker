// Yangzong Guo
// ITP 368, Fall 2017
// Final Project
// yangzong@usc.edu
package itp368.guo.yangzong.habit;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;

public class LoginController {
    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private Button signInBtn;
    @FXML
    private Button registerBtn;

    public LoginController() {}

    @FXML
    public void initialize() {

    }

    public void onSignInAction() {
        String user = usernameText.getText();
        String pass = passwordText.getText();
        String line = null;
        String[] login = null;
        boolean success = false;
        // comparing user input to entries in file
        try {
            FileReader fileReader = new FileReader("login.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                login = line.split(":");
                // if username and password exists proceed to main scene
                if (login[0].equals(user) && login[1].equals(pass)) {
                    // user credentials are correct
                    success = true;
                    try {
                        Stage stage = (Stage) signInBtn.getScene().getWindow();
                        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("main.fxml")));
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            // alert dialog for failed authentication
            if (!success) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("You did not enter a valid username/password");
                alert.showAndWait();
            }
            bufferedReader.close();
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + "login.txt" + "'");
        } catch(IOException ex) {
            System.out.println("Error reading file '" + "login.txt" + "'");
        }
    }

    public void onRegisterAction() {
        String user = usernameText.getText();
        String pass = passwordText.getText();
        // appends username and password to file
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("login.txt", true));
            writer.append("\n" + user + ":" + pass);
            usernameText.setText(null);
            passwordText.setText(null);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // alert dialog after registering
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Successfully Registered!");
        alert.showAndWait();
    }
}
