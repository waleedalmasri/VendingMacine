package sample;

import Items.Item;
import Money.Coin;
import Money.Note;
import MoneySlot.MoneySlot;
import VendingMachines.SnackMachine;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class HomeScreenController implements Initializable {
    @FXML
    GridPane itemsGrid;
    @FXML
    RadioButton payWithCash, payWithCard;
    @FXML
    HBox coinsBox, notesBox;
    @FXML
    TextField selectedItem, change, insertedCash;
    @FXML
    Button confirmButton, cancelButton;
    SnackMachine snackMachine;
    int selectedRow, selectedCol;
    DecimalFormat df = new DecimalFormat("###.##");


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ToggleGroup paymentGroup = new ToggleGroup();
        payWithCard.setToggleGroup(paymentGroup);
        payWithCash.setToggleGroup(paymentGroup);

        try {
            snackMachine = new SnackMachine(new MoneySlot("USD"), new MoneySlot("USD"));
            this.mapItemsOnGrid();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void mapItemsOnGrid() {

        for (int row = 0; row < 4; row++)
            for (int col = 0; col < 2; col++)
                itemsGrid.add(createCustomizeLabel(snackMachine.getSnacks()[row][col].peek(), row, col), col, row);
    }

    public VBox createCustomizeLabel(Item item, int rowIndex, int colIndex) {

        VBox container = new VBox();
        Label itemName = new Label(item.getName());
        Label itemDescription = new Label(item.getDescription());
        Label itemPrice = new Label(String.valueOf(item.getSellingPrice()));
        Label remainingUnits = new Label(String.valueOf(item.getAvailableItems()));


        container.getChildren().addAll(itemName, itemDescription, itemPrice, remainingUnits);
        container.setSpacing(10.0);
        container.setAlignment(Pos.CENTER);
        container.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        container.setId("unSelected");
        container.setOnMouseClicked(buttonEventHandler());
        return container;

    }

    EventHandler<? super MouseEvent> buttonEventHandler() {
        return event -> {
            VBox prevSelected = (VBox) itemsGrid.lookup("#selected");
            if (prevSelected != null) {
                prevSelected.setId("unSelected");
                prevSelected.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }


            Node node = (Node) event.getTarget();
            selectedRow = itemsGrid.getRowIndex(node);
            selectedCol = itemsGrid.getColumnIndex(node);
            ((VBox) node).setBorder(new Border(new BorderStroke(Color.CYAN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5, 5, 5, 5))));
            ((VBox) node).setId("selected");

            selectedItem.setText(snackMachine.getSnacks()[selectedRow][selectedCol].peek().getName());
            payWithCard.setDisable(false);
            payWithCash.setDisable(false);
            this.updateStatus();
        };

    }

    public void onChangePaymentMethod() {
        if (payWithCash.isSelected()) {
            coinsBox.setDisable(false);
            notesBox.setDisable(false);
        } else {
            coinsBox.setDisable(true);
            notesBox.setDisable(true);
        }


    }

    public void moneyPressed(Event e) {
        Object node = e.getTarget();
        Button b = (Button) node;

        String[] data = b.getText().split(" ");
        if (data[1].trim().charAt(0) == 'c' || Integer.parseInt(data[0].trim()) == 1) {
            Coin newCoin = new Coin("USD", data[1].trim().charAt(0), Integer.parseInt(data[0].trim()));
            if (snackMachine.validateMoney(newCoin))
                snackMachine.getMoneySlot().updateBalance(newCoin);
            else
                System.out.println("USD only");
        } else {
            Note newNote = new Note("USD", data[1].trim().charAt(0), Integer.parseInt(data[0].trim()));
            if (snackMachine.validateMoney(newNote))
                snackMachine.getMoneySlot().updateBalance(newNote);
            else
                System.out.println("USD only");
        }
        this.updateStatus();


    }

    public void updateStatus() {
        snackMachine.getMoneySlot().calculateBalanceInUSD();
        snackMachine.calculateChange(snackMachine.getSnacks()[selectedRow][selectedCol].peek());
        insertedCash.setText(String.valueOf(df.format(snackMachine.getMoneySlot().getBalanceInUSD())));
        change.setText(String.valueOf(df.format(snackMachine.getChange())));
        confirmButton.setDisable(!snackMachine.acceptMoney(snackMachine.getSnacks()[selectedRow][selectedCol].peek()) && !snackMachine.validateChange());
    }
}