package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Reports implements Initializable {
    @FXML
    TextArea summary, balanceDist;

    @FXML
    TextField profit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        summary.clear();
        balanceDist.clear();
        double accumulatedProfit = 0;
        for (int i = 0; i < HomeScreenController.snackMachine.getTransactionsList().size(); i++) {
            summary.appendText(HomeScreenController.snackMachine.getTransactionsList().get(i).toString() + "\n");
            accumulatedProfit += HomeScreenController.snackMachine.getTransactionsList().get(i).getProfit();
        }
        balanceDist.appendText("Machine Internal Balance: \n---------------------------------------\n");
        balanceDist.appendText(HomeScreenController.snackMachine.getCurrentBalance().getNoteSlot().getNoteBalance().toString() + "\n\n");
        balanceDist.appendText(HomeScreenController.snackMachine.getCurrentBalance().getCoinSlot().getCoinsBalance().toString() + "\n\n");
        balanceDist.appendText(HomeScreenController.snackMachine.getCurrentBalance().getCardSLot().getCardBalance().toString() + "\n\n");

        balanceDist.appendText("Machine Money Slot Balance: \n---------------------------------------\n");
        balanceDist.appendText(HomeScreenController.snackMachine.getMoneySlot().getNoteSlot().getNoteBalance().toString() + "\n\n");
        balanceDist.appendText(HomeScreenController.snackMachine.getMoneySlot().getCoinSlot().getCoinsBalance().toString() + "\n\n");
        balanceDist.appendText(HomeScreenController.snackMachine.getMoneySlot().getCardSLot().getCardBalance().toString() + "\n\n");

        profit.setText("Accumulated Profit: " + accumulatedProfit + " $");
    }


}
