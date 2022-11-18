package com.example.velvethills;


import com.example.velvethills.Models.History;
import com.example.velvethills.Models.Order;
import com.example.velvethills.Models.User;
import com.example.velvethills.Utils.Config;
import com.example.velvethills.Utils.DBUtils;
import com.example.velvethills.Utils.SceneUtils;
import com.example.velvethills.Utils.SessionUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.concurrent.atomic.AtomicReference;

public class MainController {

    /**
     * Top panel
     */
    @FXML
    private Button exitBtn;

    @FXML
    public Label userInfo;

    @FXML
    public Label sessionInfo;

    @FXML
    private ImageView avatar;

    /**
     * Left panel
     */
    @FXML
    private VBox navbar;
    @FXML
    private Button logOutBtn;
    @FXML
    private Button goToUslugiBtn;
    @FXML
    private Button goToOrdersBtn;
    @FXML
    private Button goToVhodHistoryBtn;
    @FXML
    private Button goToAppInfoBtn;

    /**
     * Content
     */
    @FXML
    private Label pageTitle;

    /**
     * Content>Pages
     */
    @FXML
    private GridPane pgUslugi;
    @FXML
    private GridPane pgOrders;
    @FXML
    private GridPane pgVhodHistory;
    @FXML
    private GridPane pgAppInfo;

    /**
     * Content>Pages>HistoryPage
     */
    @FXML
    private Button historySearchBtn;
    @FXML
    private TextField historySearchField;
    @FXML
    private TableView<History> tableVhodHistory;
    @FXML
    private TableColumn<History, String> historyIdCol;
    @FXML
    private TableColumn<History, String> historyTimeCol;
    @FXML
    private TableColumn<History, String> historyLoginCol;
    @FXML
    private TableColumn<History, String> historyTypeCol;
    @FXML
    ObservableList<History> historyList = FXCollections.observableArrayList();

    /**
     * Content>Pages>OrdersPage
     */
    @FXML
    private HBox ordersActionPanel;
    @FXML
    private Button orderSearchBtn;
    @FXML
    private TextField orderSearchField;
    @FXML
    private Button orderAddBtn;
    @FXML
    private TableView<Order> tableOrders;
    @FXML
    private TableColumn<Order, String> orderIdCol;
    @FXML
    private TableColumn<Order, String> orderCodeCol;
    @FXML
    private TableColumn<Order, String> orderDateCreateCol;
    @FXML
    private TableColumn<Order, String> orderTimeCol;
    @FXML
    private TableColumn<Order, String> orderDateCloseCol;
    @FXML
    private TableColumn<Order, String> orderTimeProcatCol;
    @FXML
    private TableColumn<Order, String> orderKlientCol;
    @FXML
    private TableColumn<Order, String> orderStatusCol;
    @FXML
    ObservableList<Order> ordersList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        /**
         * Animations
         */
        final String IDLE_BUTTON_STYLE = "-fx-background-color: transparent;";
        final String HOVERED_BUTTON_STYLE = "-fx-background-color: #fff5;";


        exitBtn.setStyle(IDLE_BUTTON_STYLE);
        exitBtn.setOnMouseEntered(e -> exitBtn.setStyle(HOVERED_BUTTON_STYLE));
        exitBtn.setOnMouseExited(e -> exitBtn.setStyle(IDLE_BUTTON_STYLE));

        logOutBtn.setStyle(IDLE_BUTTON_STYLE);
        logOutBtn.setOnMouseEntered(e -> logOutBtn.setStyle(HOVERED_BUTTON_STYLE));
        logOutBtn.setOnMouseExited(e -> logOutBtn.setStyle(IDLE_BUTTON_STYLE));

        goToUslugiBtn.setStyle(IDLE_BUTTON_STYLE);
        goToUslugiBtn.setOnMouseEntered(e -> goToUslugiBtn.setStyle(HOVERED_BUTTON_STYLE));
        goToUslugiBtn.setOnMouseExited(e -> goToUslugiBtn.setStyle(IDLE_BUTTON_STYLE));

        goToOrdersBtn.setStyle(IDLE_BUTTON_STYLE);
        goToOrdersBtn.setOnMouseEntered(e -> goToOrdersBtn.setStyle(HOVERED_BUTTON_STYLE));
        goToOrdersBtn.setOnMouseExited(e -> goToOrdersBtn.setStyle(IDLE_BUTTON_STYLE));

        goToVhodHistoryBtn.setStyle(IDLE_BUTTON_STYLE);
        goToVhodHistoryBtn.setOnMouseEntered(e -> goToVhodHistoryBtn.setStyle(HOVERED_BUTTON_STYLE));
        goToVhodHistoryBtn.setOnMouseExited(e -> goToVhodHistoryBtn.setStyle(IDLE_BUTTON_STYLE));

        goToAppInfoBtn.setStyle(IDLE_BUTTON_STYLE);
        goToAppInfoBtn.setOnMouseEntered(e -> goToAppInfoBtn.setStyle(HOVERED_BUTTON_STYLE));
        goToAppInfoBtn.setOnMouseExited(e -> goToAppInfoBtn.setStyle(IDLE_BUTTON_STYLE));

        /**
         * Default
         */
        refreshTableHistoryList();
        refreshTableOrdersList();
    }

    /**
     * Content>Pages>HistoryPage
     */
    private void refreshTableHistoryList() {
        DBUtils db = new DBUtils();
        historyList = db.getHistoryList();
        historyIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        historyTimeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        historyLoginCol.setCellValueFactory(new PropertyValueFactory<>("login"));
        historyTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableVhodHistory.setItems(historyList);
    }

    private void refreshTableHistoryList(String searchLogin) {
        DBUtils db = new DBUtils();
        historyList = db.getHistoryList(searchLogin);
        historyTimeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        historyLoginCol.setCellValueFactory(new PropertyValueFactory<>("login"));
        historyTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableVhodHistory.setItems(historyList);
    }

    /**
     * Content>Pages>OrdersPage
     */

    private void refreshTableOrdersList() {
        DBUtils db = new DBUtils();
        ordersList = db.getOrdersList();
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderCodeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        orderDateCreateCol.setCellValueFactory(new PropertyValueFactory<>("dateCreate"));
        orderTimeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        orderDateCloseCol.setCellValueFactory(new PropertyValueFactory<>("dateClose"));
        orderTimeProcatCol.setCellValueFactory(new PropertyValueFactory<>("timeProcat"));
        orderKlientCol.setCellValueFactory(new PropertyValueFactory<>("klient"));
        orderStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableOrders.setItems(ordersList);
    }

    private void refreshTableOrdersList(String searchCode) {
        DBUtils db = new DBUtils();
        ordersList = db.getOrdersList(searchCode);
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderCodeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        orderDateCreateCol.setCellValueFactory(new PropertyValueFactory<>("dateCreate"));
        orderTimeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        orderDateCloseCol.setCellValueFactory(new PropertyValueFactory<>("dateClose"));
        orderTimeProcatCol.setCellValueFactory(new PropertyValueFactory<>("timeProcat"));
        orderKlientCol.setCellValueFactory(new PropertyValueFactory<>("klient"));
        orderStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableOrders.setItems(ordersList);
    }

    /**
     * Top panel
     */
    public void setUser(User user) {
        String avaPath = Config.resourcesPath + "drawables/" + user.getFio().split(" ")[0] + ".jpeg";
        Image avaImage = new Image(avaPath);
        avatar.setImage(avaImage);
        userInfo.setText(user.getFio() + "\nДолжность: " + user.getStatus());
        SessionUtils.startSession(sessionInfo);
        if (!user.getStatus().equals("Администратор")) {
            navbar.getChildren().remove(goToVhodHistoryBtn);
        } else {
            ordersActionPanel.getChildren().remove(orderAddBtn);
        }
    }

    @FXML
    private void exit() {
        System.exit(0);
    }

    /**
     * Left panel
     */
    @FXML
    private void handleClicksNav(ActionEvent event) {
        if (event.getSource() == logOutBtn) {
            new SceneUtils().changeScene(exitBtn.getScene(), "login-view.fxml", null);
        }
        if (event.getSource() == goToUslugiBtn) {
            pageTitle.setText("Услуги");
            pgUslugi.toFront();
        }
        if (event.getSource() == goToOrdersBtn) {
            pageTitle.setText("Заказы");
            pgOrders.toFront();
        }
        if (event.getSource() == goToVhodHistoryBtn) {
            pageTitle.setText("История входов");
            pgVhodHistory.toFront();
        }
        if (event.getSource() == goToAppInfoBtn) {
            pageTitle.setText("О приложении");
            pgAppInfo.toFront();
        }
    }

    @FXML
    private void handleClicksSerch(ActionEvent event) {
        if (event.getSource() == historySearchBtn) {
            refreshTableHistoryList(historySearchField.getText());
        }
        if (event.getSource() == orderSearchBtn) {
            refreshTableOrdersList(orderSearchField.getText());
        }
    }

    @FXML
    private void handleClicksAdd(ActionEvent event) {
        if (event.getSource() == orderAddBtn) {
            Parent root;
            try {
                AtomicReference<Double> xOffset = new AtomicReference<>((double) 101);
                AtomicReference<Double> yOffset = new AtomicReference<>((double) 101);
                FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("add-order-view.fxml"));
                root = loader.load();

                Stage stage = new Stage();

                stage.initStyle(StageStyle.DECORATED.UNDECORATED);

                root.setOnMouseMoved(mouseEvent -> {
                    xOffset.set(mouseEvent.getSceneX());
                    yOffset.set(mouseEvent.getSceneY());
                });

                root.setOnMouseDragged(mouseEvent -> {
                    if (yOffset.get() < Config.draggedYZone) {
                        stage.setX(mouseEvent.getScreenX() - xOffset.get());
                        stage.setY(mouseEvent.getScreenY() - yOffset.get());
                    }
                });
                stage.setTitle("Добавление заказа");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
