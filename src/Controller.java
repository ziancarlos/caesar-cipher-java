import java.util.LinkedList;
import java.util.Queue;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private Button button;

    @FXML
    private Label decryptLabel;

    @FXML
    private TextField decryptTf;

    @FXML
    private Label encryptLabel;

    @FXML
    private TextField encryptTf;

    @FXML
    private TextField resultTf;

    @FXML
    private ComboBox<Integer> numCb;

    Queue<Character> queue = new LinkedList<>();

    StringBuilder sb = new StringBuilder();

    public void initialize() {
        setDecryptVisible(false);

        for (int i = 1; i <= 22; i++) {
            numCb.getItems().add(i);
        }

        numCb.getSelectionModel().selectFirst();

        resultTf.setDisable(true);

        button.setText("Encrypt");
    }

    @FXML
    void button(ActionEvent event) {
        if (encryptLabel.isVisible()) {
            // decrypt
            this.encrypt();
        } else {
            // encrypt
            this.decrypt();
        }
    }

    @FXML
    void switchBtn(ActionEvent event) {
        this.decryptTf.setText(null);
        this.encryptTf.setText(null);
        this.resultTf.setText(null);

        if (encryptLabel.isVisible()) {
            setEncryptVisible(false);

            setDecryptVisible(true);

            this.button.setText("Decrypt");
        } else {
            setEncryptVisible(true);

            setDecryptVisible(false);

            this.button.setText("Encrypt");
        }
    }

    private void decrypt() {
        this.clearAll();

        if (this.decryptTf.getText() == null && this.decryptTf.getText().isBlank()) {
            this.setAlert("Please input the text to decrypt.");
            return;
        }

        for (int i = 0; i < this.decryptTf.getText().length(); i++) {
            queue.add(this.decryptTf.getText().charAt(i));
        }

        for (int i = 0; i < this.decryptTf.getText().length(); i++) {
            char currChar = queue.poll();

            if (currChar == ' ') {
                sb.append(" ");
            } else {
                int currIntVal = currChar - Integer.parseInt(numCb.getSelectionModel().getSelectedItem().toString());

                if (currIntVal > 122) {
                    System.out.println(currIntVal);
                    currIntVal = currIntVal - 26;
                    System.out.println(currIntVal);
                }

                if (currIntVal < 97) {
                    currIntVal = currIntVal + 26;
                }

                char currCharAfter = (char) currIntVal;

                sb.append(currCharAfter);
            }
        }

        this.resultTf.setText(sb.toString());

        this.decryptTf.setText(null);

    }

    private void encrypt() {
        this.clearAll();

        if (this.encryptTf.getText() == null && this.encryptTf.getText().isBlank()) {
            this.setAlert("Please enter text to encrypt.");
            return;
        }

        for (int i = 0; i < this.encryptTf.getText().length(); i++) {
            queue.add(this.encryptTf.getText().charAt(i));
        }

        for (int i = 0; i < this.encryptTf.getText().length(); i++) {
            char currChar = queue.poll();

            if (currChar == ' ') {
                sb.append(" ");
            } else {
                int currIntVal = currChar + Integer.parseInt(numCb.getSelectionModel().getSelectedItem().toString());

                if (currIntVal > 122) {
                    System.out.println(currIntVal);
                    currIntVal = currIntVal - 26;
                    System.out.println(currIntVal);
                }

                char currCharAfter = (char) currIntVal;

                sb.append(currCharAfter);
            }
        }

        this.resultTf.setText(sb.toString());

        this.encryptTf.setText(null);
    }

    private void setEncryptVisible(boolean visible) {
        encryptLabel.setVisible(visible);
        encryptTf.setVisible(visible);
    }

    private void setDecryptVisible(boolean visible) {
        decryptLabel.setVisible(visible);
        decryptTf.setVisible(visible);
    }

    private void clearAll() {
        this.sb = new StringBuilder();
        this.queue = new LinkedList<>();
    }

    private void setAlert(String headerMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(headerMessage);
        alert.showAndWait();
    }

}
