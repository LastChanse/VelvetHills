package com.example.velvethills.Utils;

import com.example.velvethills.Models.History;
import com.example.velvethills.Models.Order;
import com.example.velvethills.Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import java.sql.*;

public class DBUtils {

    public static User logInUser(Scene scene, String login, String password) {
        User user = new User();
        String statusId = new String();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(Config.DBUrl, Config.DBUser, Config.DBPassword);
            preparedStatement = connection.prepareStatement("SELECT * FROM sotrudnics WHERE Login=?");
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                AlertUtils.showAlert("Предоставленные учетные данные неверны", Alert.AlertType.ERROR);
            } else {
                while (resultSet.next()) {
                    user.setId(Integer.parseInt(resultSet.getString("idSotrudnic")));
                    user.setLogin(resultSet.getString("Login"));
                    user.setPassword(resultSet.getString("Password"));
                    user.setFio(resultSet.getString("FIO"));
                    statusId = resultSet.getString("Dolshnosts_idDolshnost");
                    if (user.getPassword().equals(password)) {
                        try {
                            PreparedStatement prSt = connection.prepareStatement("SELECT * FROM dolshnosts WHERE idDolshnost=?");
                            prSt.setString(1, statusId);

                            ResultSet resSet = prSt.executeQuery();

                            resSet.next();

                            user.setStatus(resSet.getString("NameDolshnost"));

                        } catch (SQLException e) {
                            AlertUtils.showAlert("Не удалось получить данные о должности из базы данных", Alert.AlertType.ERROR);
                            throw new RuntimeException(e);
                        }
                        Statement statement = connection.createStatement();
                        statement.executeUpdate("update vxod set DateTime=current_timestamp() where sotrudnics_idSotrudnic = '"+user.getId()+"'");
                        return user;
                    } else {
                        AlertUtils.showAlert("Предоставленные учетные данные неверны", Alert.AlertType.ERROR);
                    }
                }
            }
        } catch (SQLException e) {
            AlertUtils.showAlert("Ошибка подключения к базе данных", Alert.AlertType.ERROR);
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public ObservableList<History> getHistoryList() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<History> historyList = FXCollections.observableArrayList();

        try {
            connection = DriverManager.getConnection(Config.DBUrl, Config.DBUser, Config.DBPassword);
            preparedStatement = connection.prepareStatement("SELECT V.idVxod, V.DateTime, V.TypeVxod, S.Login FROM session1.vxod V, session1.sotrudnics S " +
                    "WHERE V.sotrudnics_idSotrudnic=S.idSotrudnic GROUP BY V.idVxod");

            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                AlertUtils.showAlert("Предоставленные учетные данные неверны", Alert.AlertType.ERROR);
            } else {
                while (resultSet.next()) {
                    historyList.add(new History(
                            resultSet.getInt("idVxod"),
                            resultSet.getString("DateTime"),
                            resultSet.getString("Login"),
                            resultSet.getString("TypeVxod")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return historyList;
    }

    public ObservableList<History> getHistoryList(String searchLogin) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<History> historyList = FXCollections.observableArrayList();
        ObservableList<History> filteredItems = FXCollections.observableArrayList();

        try {
            connection = DriverManager.getConnection(Config.DBUrl, Config.DBUser, Config.DBPassword);
            preparedStatement = connection.prepareStatement("SELECT V.idVxod, V.DateTime, V.TypeVxod, S.Login FROM session1.vxod V, session1.sotrudnics S " +
                    "WHERE V.sotrudnics_idSotrudnic=S.idSotrudnic GROUP BY V.idVxod");
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                AlertUtils.showAlert("Предоставленные учетные данные неверны", Alert.AlertType.ERROR);
            } else {
                while (resultSet.next()) {
                    historyList.add(new History(
                            resultSet.getInt("idVxod"),
                            resultSet.getString("DateTime"),
                            resultSet.getString("Login"),
                            resultSet.getString("TypeVxod")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (!historyList.isEmpty() && !(historyList == null)) {
            for (History li : historyList)
                if (li.getLogin().contains(searchLogin))
                    filteredItems.add(li);
        }

        return filteredItems;
    }

    public ObservableList<Order> getOrdersList() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<Order> ordersList = FXCollections.observableArrayList();

        try {
            connection = DriverManager.getConnection(Config.DBUrl, Config.DBUser, Config.DBPassword);
            preparedStatement = connection.prepareStatement("SELECT Z.idZacaz, Z.CodeZacaza, Z.DateCreate, Z.TimeZacaz, Z.DateClose, Z.TimeProcat," +
                    " K.FIO, S.StatusName FROM zacazs Z, klients K, stasuszacaza S " +
                    "WHERE Z.Klients_idKlient=K.idKlient AND Z.StasusZacaza_idStasusZacaza=S.idStasusZacaza GROUP BY Z.idZacaz");

            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                AlertUtils.showAlert("Предоставленные учетные данные неверны", Alert.AlertType.ERROR);
            } else {
                while (resultSet.next()) {
                    ordersList.add(new Order(
                            resultSet.getInt("idZacaz"),
                            resultSet.getString("CodeZacaza"),
                            resultSet.getString("DateCreate"),
                            resultSet.getString("TimeZacaz"),
                            resultSet.getString("DateClose"),
                            resultSet.getString("TimeProcat"),
                            resultSet.getString("FIO"),
                            resultSet.getString("StatusName")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ordersList;
    }

    public ObservableList<Order> getOrdersList(String searchCode) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<Order> ordersList = FXCollections.observableArrayList();
        ObservableList<Order> filteredItems = FXCollections.observableArrayList();

        try {
            connection = DriverManager.getConnection(Config.DBUrl, Config.DBUser, Config.DBPassword);
            preparedStatement = connection.prepareStatement("SELECT Z.idZacaz, Z.CodeZacaza, Z.DateCreate, Z.TimeZacaz, Z.DateClose, Z.TimeProcat," +
                    " K.FIO, S.StatusName FROM zacazs Z, klients K, stasuszacaza S " +
                    "WHERE Z.Klients_idKlient=K.idKlient AND Z.StasusZacaza_idStasusZacaza=S.idStasusZacaza GROUP BY Z.idZacaz");

            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                AlertUtils.showAlert("Предоставленные учетные данные неверны", Alert.AlertType.ERROR);
            } else {
                while (resultSet.next()) {
                    ordersList.add(new Order(
                            resultSet.getInt("idZacaz"),
                            resultSet.getString("CodeZacaza"),
                            resultSet.getString("DateCreate"),
                            resultSet.getString("TimeZacaz"),
                            resultSet.getString("DateClose"),
                            resultSet.getString("TimeProcat"),
                            resultSet.getString("FIO"),
                            resultSet.getString("StatusName")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (!ordersList.isEmpty() && !(ordersList == null)) {
            for (Order li : ordersList)
                if (li.getCode().contains(searchCode))
                    filteredItems.add(li);
        }

        return filteredItems;
    }

    public void addOrder(Order order) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList<Order> ordersList = FXCollections.observableArrayList();
        ObservableList<Order> filteredItems = FXCollections.observableArrayList();

        try {
            connection = DriverManager.getConnection(Config.DBUrl, Config.DBUser, Config.DBPassword);
            Statement statement = connection.createStatement();
            //              INSERT INTO `zacazs` (`idZacaz`, `CodeZacaza`, `DateCreate`, `TimeZacaz`, `DateClose`, `TimeProcat`, `Klients_idKlient`, `StasusZacaza_idStasusZacaza`) VALUES ('53', '51', '2000-01-23', '00:00:00', '0000-00-00', '00:00:00', '45462575', '1');
            System.out.println("INSERT INTO zacazs (idZacaz, CodeZacaza, DateCreate, TimeZacaz, DateClose, TimeProcat, Klients_idKlient, StasusZacaza_idStasusZacaza) VALUES ('"+order.getId()+"','" +
                    order.getCode()+"','"+
                    order.getDateCreate()+"','"+
                    order.getTime()+"','"+
                    order.getDateClose()+"','"+
                    order.getTimeProcat()+"','"+
                    order.getKlient()+"','"+
                    order.getStatus()+"')");
            statement.executeUpdate("INSERT INTO zacazs (idZacaz, CodeZacaza, DateCreate, TimeZacaz, DateClose, TimeProcat, Klients_idKlient, StasusZacaza_idStasusZacaza) VALUES ('"+order.getId()+"','" +
                    order.getCode()+"','"+
                    order.getDateCreate()+"','"+
                    order.getTime()+"','"+
                    order.getDateClose()+"','"+
                    order.getTimeProcat()+"','"+
                    order.getKlient()+"','"+
                    order.getStatus()+"')");
    } catch (SQLException e) {
            AlertUtils.showAlert(e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
            //throw new RuntimeException(e);
        }
    }
}
