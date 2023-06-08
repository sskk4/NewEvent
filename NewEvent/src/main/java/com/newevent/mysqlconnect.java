package com.newevent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import javax.swing.JOptionPane;

public class mysqlconnect {


    Connection conn = null;
    public static Connection ConnectDb(){

        try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/market","root","");
            System.out.println("Connected with database");
        return conn;
        }

        catch(Exception e) {

            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }

    public static ObservableList<Events> getDataEvents(){
        Connection conn = ConnectDb();
        ObservableList<Events> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT USERS.username, Events.* FROM Events JOIN Users ON Events.id_user = Users.user_id;");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new Events(Integer.parseInt(rs.getString("id_eventu")),
                        rs.getString("name"),
                        rs.getString("city"),
                        rs.getString("date"),
                        Integer.parseInt(rs.getString("price")),
                        Integer.parseInt(rs.getString("amount")),
                        Integer.parseInt(rs.getString("id_user")),
                        rs.getString("username")));


            }
        }catch (Exception e){

        }
        return list;
    }




}
