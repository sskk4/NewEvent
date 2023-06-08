package com.newevent;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HelloController {
    @FXML
    private JFXButton btn_login;
    @FXML
    private TextField email_up;
    @FXML
    private AnchorPane pane_login;
    @FXML
    private AnchorPane pane_signup;
    @FXML
    private PasswordField txt_password;
    @FXML
    private PasswordField txt_password_up;
    @FXML
    private TextField txt_username;
    @FXML
    private TextField txt_username_up;
    @FXML
    private JFXButton btn_login2;
    @FXML
    private JFXButton btn_singup2;

    Connection conn = null;
ResultSet rs = null;
PreparedStatement pst = null;




    public void LoginpaneShow(){

        pane_login.setVisible(true);
        pane_signup.setVisible(false);
        btn_login2.setStyle(" -fx-background-color: linear-gradient(to right, #4B00B5, #472183);  -fx-background-radius:  20 0 0 20;");
        btn_singup2.setStyle(" -fx-background-color: linear-gradient(to right, #4B00D2, #4B56D2); -fx-background-radius:  0 20 20 0;");

    }

    public void Signuppaneshow(){

        pane_login.setVisible(false);
        pane_signup.setVisible(true);
        btn_login2.setStyle(" -fx-background-color:  linear-gradient(to left, #4B00D2, #4B56D2); -fx-background-radius:  20 0 0 20;");
        btn_singup2.setStyle(" -fx-background-color: linear-gradient(to left, #4B00B5, #472183); -fx-background-radius:  0 20 20 0;");


    }

    private static String your_login;

    public static String getUsername(){

        return your_login;

    }

    @FXML
    private void Login (ActionEvent event) throws Exception{
        conn = mysqlconnect.ConnectDb();
        String sql = "Select * from users where username = ? and password = ?";
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_username.getText());
            pst.setString(2, txt_password.getText());
            rs = pst.executeQuery();
        your_login=txt_username.getText();
            if(rs.next()){
                JOptionPane.showMessageDialog(null,"Username and Password is Correct");
                btn_login.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("PANEL.fxml"));
                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setScene(scene);
                mainStage.initStyle(StageStyle.UNDECORATED);
                mainStage.initStyle(StageStyle.TRANSPARENT);
                scene.setFill(Color.TRANSPARENT);
                mainStage.show();
            }else JOptionPane.showMessageDialog(null,"Invalid Username or Password");
        } catch (Exception e){
    JOptionPane.showMessageDialog(null,e);
        }
    }





    public void add_users(ActionEvent event){

        conn = mysqlconnect.ConnectDb();
        String sql = "insert into users (username,password,type,email) values (?,?,?,?)";
        try
        {
            String email = email_up.getText();
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            Pattern pat = Pattern.compile(emailRegex);

            int approve=0;

            if(txt_username_up.getText().length()<4 )
            {
                txt_username_up.setStyle("-fx-background-color:linear-gradient(to right, #F55050,#F48484); -fx-background-radius:20");

            }
            else
            {
                txt_username_up.setStyle("-fx-background-color:#f1f6f5; -fx-background-radius:20");
                approve++;
            }

            if(txt_password_up.getText().length()<4)
            {
                txt_password_up.setStyle("-fx-background-color:linear-gradient(to right, #F55050,#F48484); -fx-background-radius:20");

            }
            else
            {
                txt_password_up.setStyle("-fx-background-color:#f1f6f5; -fx-background-radius:20");
                approve++;
            }

            if(!pat.matcher(email).matches())
            {
                System.out.println("zle jest");
                email_up.setStyle("-fx-background-color:linear-gradient(to right, #F55050,#F48484); -fx-background-radius:20");
            }
            else
            {
                System.out.println("Email good");
                email_up.setStyle("-fx-background-color:#f1f6f5; -fx-background-radius:20");
                approve++;
            }


            if(approve==3)
            {

                pst = conn.prepareStatement(sql);
                pst.setString(1,txt_username_up.getText());
                pst.setString(2,txt_password_up.getText());
                pst.setString(3,"user");
                pst.setString(4,email_up.getText());
                pst.execute();
                JOptionPane.showMessageDialog(null, "Account created");
                LoginpaneShow();
                approve=0;
            }



        }catch(Exception e){

            JOptionPane.showMessageDialog(null,"Something went wrong");
        }

    }

    @FXML
    private void handleclose(ActionEvent event){
        System.exit(0);
    }

}