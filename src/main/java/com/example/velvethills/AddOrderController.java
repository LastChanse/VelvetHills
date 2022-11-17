package com.example.velvethills;

import com.example.velvethills.Models.Order;
import com.example.velvethills.Utils.AlertUtils;
import com.example.velvethills.Utils.Config;
import com.example.velvethills.Utils.DBUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.concurrent.atomic.AtomicReference;

public class AddOrderController {

    @FXML
    private TextField orderId;

    @FXML
    private TextField orderCode;

    @FXML
    private TextField orderDateCreate;

    @FXML
    private TextField orderTime;

    @FXML
    private TextField orderDateClose;

    @FXML
    private TextField orderTimeProcat;

    @FXML
    private TextField orderKlient;

    @FXML
    private TextField orderStatus;

    @FXML
    private Button exitOBtn;

    @FXML
    private Button createBarcode;

    @FXML
    private Button addOrder;

    @FXML
    public void initialize() {
        /**
         * Animations
         */
        final String IDLE_BUTTON_STYLE = "-fx-background-color:  #92D050;";
        final String HOVERED_BUTTON_STYLE = "-fx-background-color: #92D050aa;";

        createBarcode.setStyle(IDLE_BUTTON_STYLE);
        createBarcode.setOnMouseEntered(e -> createBarcode.setStyle(HOVERED_BUTTON_STYLE));
        createBarcode.setOnMouseExited(e -> createBarcode.setStyle(IDLE_BUTTON_STYLE));

        addOrder.setStyle(IDLE_BUTTON_STYLE);
        addOrder.setOnMouseEntered(e -> addOrder.setStyle(HOVERED_BUTTON_STYLE));
        addOrder.setOnMouseExited(e -> addOrder.setStyle(IDLE_BUTTON_STYLE));

        /**
         * OnMouseClick on Buttons
         */
        exitOBtn.setOnMouseClicked(mouseEvent -> {
            Stage stage = (Stage) exitOBtn.getScene().getWindow();
            stage.close();
        });

        addOrder.setOnMouseClicked(mouseEvent -> {
            if (
                            orderId.getText().equals("") ||
                            orderCode.getText().equals("") ||
                            orderDateCreate.getText().equals("") ||
                            orderTime.getText().equals("") ||
                            orderDateClose.getText().equals("") ||
                            orderTimeProcat.getText().equals("") ||
                            orderKlient.getText().equals("") ||
                            orderStatus.getText().equals("")
            ) {
                AlertUtils.showAlert("Поля не могут быть пустыми при добавлении!", Alert.AlertType.ERROR);
            } else {

                DBUtils db = new DBUtils();
                db.addOrder(new Order(
                        Integer.parseInt(orderId.getText()),
                        orderCode.getText(),
                        orderDateCreate.getText(),
                        orderTime.getText(),
                        orderDateClose.getText(),
                        orderTimeProcat.getText(),
                        orderKlient.getText(),
                        orderStatus.getText()
                ));
                Stage stage = (Stage) exitOBtn.getScene().getWindow();
                stage.close();
            }
        });

        createBarcode.setOnMouseClicked(mouseEvent -> {
            if (orderId.getText().equals("") || orderTimeProcat.getText().equals("")) {
                AlertUtils.showAlert("Для печати штрих-кода необходимо чтобы поля" +
                        "\nномер и время проката были заполнены", Alert.AlertType.ERROR);
            } else {
                Parent root;
                try {
                    AtomicReference<Double> xOffset = new AtomicReference<>((double) 101);
                    AtomicReference<Double> yOffset = new AtomicReference<>((double) 101);
                    FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("barcode-view.fxml"));
                    root = loader.load();

                    BarcodeController bc = loader.getController();
                    bc.getData(new Order(
                            Integer.parseInt(orderId.getText()),
                            orderCode.getText(),
                            orderDateCreate.getText(),
                            orderTime.getText(),
                            orderDateClose.getText(),
                            orderTimeProcat.getText(),
                            orderKlient.getText(),
                            orderStatus.getText()
                    ));

                    Stage stage = new Stage();

                    stage.initStyle(StageStyle.DECORATED.UNDECORATED);

                    root.setOnMouseMoved(mouseEventRoot -> {
                        xOffset.set(mouseEvent.getSceneX());
                        yOffset.set(mouseEvent.getSceneY());
                    });

                    root.setOnMouseDragged(mouseEventRoot -> {
                        if (yOffset.get() < Config.draggedYZone) {
                            stage.setX(mouseEvent.getScreenX() - xOffset.get());
                            stage.setY(mouseEvent.getScreenY() - yOffset.get());
                        }
                    });
                    stage.setTitle("ШТРИХ-КОД");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
