package sample;

import Items.Item;
import Items.Snack;
import Money.Coin;
import Money.Note;
import MoneySlot.MoneySlot;
import MoneySlot.NoteBalance;
import MoneySlot.CoinsBalance;
import VendingMachines.SnackMachine;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;


public class HomeScreenController implements Initializable {
    @FXML
    GridPane itemsGrid;
    @FXML
    RadioButton payWithCash, payWithCard;
    @FXML
    HBox coinsBox, notesBox;
    @FXML
    TextField selectedItem, change, insertedCash, searchField;
    @FXML
    Button confirmButton, cancelButton;
    @FXML
    Text currentBalance, toBeBalance;

    public static SnackMachine snackMachine;
    int selectedRow, selectedCol;

    DecimalFormat df = new DecimalFormat("###.##");


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ToggleGroup paymentGroup = new ToggleGroup();
        payWithCard.setToggleGroup(paymentGroup);
        payWithCash.setToggleGroup(paymentGroup);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            this.instantSearch(newValue);
            searchField.setText(newValue);
        });
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
            try {
                selectedRow = itemsGrid.getRowIndex(node);
                selectedCol = itemsGrid.getColumnIndex(node);
                ((VBox) node).setBorder(new Border(new BorderStroke(Color.CYAN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5, 5, 5, 5))));
                ((VBox) node).setId("selected");

                selectedItem.setText(snackMachine.getSnacks()[selectedRow][selectedCol].peek().getName());
                payWithCard.setDisable(false);
                payWithCash.setDisable(false);
                this.updateStatus();
            } catch (NullPointerException e) {
                System.out.println("Click The box itself");
                this.cancel();
            }

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
            if (snackMachine.validateMoney(newCoin)) {
                snackMachine.getMoneySlot().updateBalance(newCoin);
                snackMachine.getCurrentBalance().updateBalance(newCoin);


            } else
                System.out.println("USD only");
        } else {
            Note newNote = new Note("USD", data[1].trim().charAt(0), Integer.parseInt(data[0].trim()));
            if (snackMachine.validateMoney(newNote)) {
                snackMachine.getMoneySlot().updateBalance(newNote);
                snackMachine.getCurrentBalance().updateBalance(newNote);

            } else
                System.out.println("USD only");
        }
        this.updateStatus();


    }

    public void updateStatus() {
        snackMachine.calculateChange(snackMachine.getSnacks()[selectedRow][selectedCol].peek());
        snackMachine.getMoneySlot().calculateBalanceInUSD();
        snackMachine.getCurrentBalance().calculateBalanceInUSD();
        currentBalance.setText("Current balance: " + df.format(snackMachine.getCurrentBalance().getBalanceInUSD()));
        toBeBalance.setText("To Be balance: " + df.format(snackMachine.getCurrentBalance().getBalanceInUSD() - snackMachine.getChange()));
        insertedCash.setText(String.valueOf(df.format(snackMachine.getMoneySlot().getBalanceInUSD())));
        change.setText(String.valueOf(df.format(snackMachine.getChange())));
        confirmButton.setDisable(!snackMachine.acceptMoney(snackMachine.getSnacks()[selectedRow][selectedCol].peek()));
    }

    public void confirm() {
        if (snackMachine.purchase(selectedRow, selectedCol)) {
            Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION);
            successAlert.setContentText("Please take your snack and change");
            this.updateStatus();
            successAlert.show();
            this.clearAll();
            itemsGrid.getChildren().clear();
            this.mapItemsOnGrid();
            snackMachine.clearMoneySlot();
        } else {
            Alert failureAlert = new Alert(Alert.AlertType.ERROR);
            failureAlert.setContentText("Sorry, SNACKERS cannot serve you with this order. We don't have enough variance of change");
            failureAlert.show();
            this.cancel();
        }


    }

    public void sortByPrice() {

        Arrays.sort(snackMachine.getSnacks(), Comparator.comparingDouble(o -> o[0].peek().getSellingPrice()));
        itemsGrid.getChildren().clear();
        this.mapItemsOnGrid();
    }

    public void sortByName() {

        Arrays.sort(snackMachine.getSnacks(), Comparator.comparing(o -> o[0].peek().getName()));
        itemsGrid.getChildren().clear();
        this.mapItemsOnGrid();
    }

    public void instantSearch(String token) {
        itemsGrid.getChildren().clear();
        if (token.equals(""))
            this.mapItemsOnGrid();
        for (int row = 0; row < 4; row++)
            for (int col = 0; col < 2; col++) {
                if (snackMachine.getSnacks()[row][col].peek().getName().toLowerCase().startsWith(token.toLowerCase()))
                    itemsGrid.add(createCustomizeLabel(snackMachine.getSnacks()[row][col].peek(), row, col), col, row);
            }
    }

    public void cancel() {
        snackMachine.returnMoney();
        this.clearAll();
    }

    public void clearAll() {
        selectedItem.clear();
        change.clear();
        insertedCash.clear();
        snackMachine.setChange(0.0);
        currentBalance.setText("Current balance: " + df.format(snackMachine.getCurrentBalance().getBalanceInUSD()));
        toBeBalance.setText("To Be balance: " + df.format(snackMachine.getCurrentBalance().getBalanceInUSD() - snackMachine.getChange()));

        VBox prevSelected = (VBox) itemsGrid.lookup("#selected");
        if (prevSelected != null) {
            prevSelected.setId("unSelected");
            prevSelected.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }
        payWithCash.setDisable(true);
        payWithCash.setSelected(false);
        payWithCard.setSelected(false);
        payWithCard.setDisable(true);
        notesBox.setDisable(true);
        coinsBox.setDisable(true);
        confirmButton.setDisable(true);
        searchField.clear();

    }

    public void LoadScene() {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("reports.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNIFIED);
            stage.setTitle("Reports");
            stage.setScene(new Scene(root1));
            stage.show();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
