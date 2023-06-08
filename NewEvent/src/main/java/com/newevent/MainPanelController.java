package com.newevent;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static java.lang.Integer.parseInt;

public class MainPanelController {


    @FXML
    private AnchorPane pane__welcome;
    @FXML
    private TextField txt_newpassword;
    @FXML
    private JFXButton btn_changepassword;
    @FXML
    private JFXButton btn_newpassword;
    @FXML
    private AnchorPane pane_user_profile;
    @FXML
    private AnchorPane pane_events_user;
    @FXML
    private AnchorPane pane_admin;

    @FXML
    private TableColumn<Bought, Integer> col_amount2;
    @FXML
    private TableColumn<Bought, String> col_city2;
    @FXML
    private TableColumn<Bought, String> col_name2;
    @FXML
    private TableColumn<Bought, String> col_date2;


    @FXML
    private TableColumn<Events, Integer> col_amount1;
    @FXML
    private TableColumn<Events, String> col_city1;
    @FXML
    private TableColumn<Events, String> col_name1;
    @FXML
    private TableColumn<Events, Integer> col_price1;
    @FXML
    private TableColumn<Events, Integer> col_id1;
    @FXML
    private TableColumn<Events, String> col_date1;


    @FXML
    private TableColumn<Events, Integer> col_amount;
    @FXML
    private TableColumn<Events, String> col_city;
    @FXML
    private TableColumn<Events, String> col_name;
    @FXML
    private TableColumn<Events, Integer> col_price;
    @FXML
    private TableColumn<Events, Integer> col_id;
    @FXML
    private TableColumn<Events, String> col_date;
    @FXML
    private TableColumn<Events, String> col_whoadd;

    @FXML
    private TableView<Events> table_events2;
    @FXML
    private TableView<Events> table_events;
    @FXML
    private TableView<Bought> table_yourevents;


    @FXML
    private TextField txt_amount;
    @FXML
    private TextField txt_city;
    @FXML
    private TextField txt_name;
    @FXML
    private TextField txt_price;
    @FXML
    private DatePicker txt_date;
    @FXML
    private TextField txt_id;
    @FXML
    private TextField txt_idtobuy;
    @FXML
    private TextField filterField;

    @FXML
    private TextField filterField1;
    @FXML
    private TextField txt_amounttobuy;
    @FXML
    private Label txt_username_welcome;
    @FXML
    private Label txt_profiletype;
    @FXML
    private Label txt_profilemail;
    @FXML
    private Label txt_profilename;
    @FXML
    private Button btn_logout;
    @FXML
    private JFXButton btn_editeventadmin;

////////////////////////////////////// do laczenia z baza

    int index = -1;
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;


///////////////////////////////////////    do panelu glownego


    public void Paneadminshow() throws SQLException {
        pane_admin.setVisible(true);
        pane__welcome.setVisible(false);
        pane_events_user.setVisible(false);

        showEvents();
    }

    public void Panewelcomeshow() {
        pane_admin.setVisible(false);
        pane__welcome.setVisible(true);
        pane_events_user.setVisible(false);
        pane_user_profile.setVisible(false);

        String currentUsername = HelloController.getUsername();
        txt_username_welcome.setText(currentUsername);

    }

    public void Paneeventshow(){
        pane_admin.setVisible(false);
        pane__welcome.setVisible(false);
        pane_events_user.setVisible(true);
        pane_user_profile.setVisible(false);

        showEventsForUser();

    }

    public void Paneuserprofileshow(){
        showPurchasedTickets();
        Panechangepasswordhide();
        pane_admin.setVisible(false);
        pane__welcome.setVisible(false);
        pane_events_user.setVisible(false);
        pane_user_profile.setVisible(true);

        txt_profiletype.setText(rola);
        txt_profilemail.setText(mail);
        txt_profilename.setText(currentUsername);

    }


/////////////////////////////////////// do panelu z profilem użytkownika

    public ObservableList<Bought> getDataBought(){
        Connection conn = mysqlconnect.ConnectDb();
        ObservableList<Bought> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT events.name, events.city, events.date, bought.ile_sztuk FROM events INNER JOIN bought ON events.id_eventu = bought.id_wydarzenia WHERE bought.id_kupujacego = "+userid+";");

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new Bought(
                        rs.getString("name"),
                        rs.getString("city"),
                        rs.getString("date"),
                        parseInt(rs.getString("ile_sztuk"))));
            }
        }catch (Exception e){

        }
        return list;
    }
    public void Panechangepasswordshow(){

        txt_newpassword.setVisible(true);
        btn_newpassword.setVisible(true);
        btn_changepassword.setOnAction(event->Panechangepasswordhide());

    }
    public void Panechangepasswordhide(){

        txt_newpassword.setVisible(false);
        btn_newpassword.setVisible(false);
        btn_changepassword.setOnAction(event->Panechangepasswordshow());

    }
    public void ChangePassword(){


        try{


            if(txt_newpassword.getText().length()<4 || txt_newpassword.getText()==null)
            {
                JOptionPane.showMessageDialog(null,"PASSWORD TOO SHORT");
            }
            else
            {
                conn = mysqlconnect.ConnectDb();
                String sql="UPDATE users set password='"+txt_newpassword.getText()+"' WHERE user_id="+userid+";";
                pst = conn.prepareStatement(sql);
                JOptionPane.showMessageDialog(null,"PASSWORD CHANGED");
                pst.executeUpdate();
                logout();
            }



        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e);
        }

}
    public void showPurchasedTickets(){


        try{

            ObservableList<Bought> listM3;
            conn = mysqlconnect.ConnectDb();



            col_name2.setCellValueFactory(new PropertyValueFactory<Bought, String>("name"));
            col_city2.setCellValueFactory(new PropertyValueFactory<Bought, String>("city"));

            col_amount2.setCellValueFactory(new PropertyValueFactory<Bought, Integer>("amount"));
            col_date2.setCellValueFactory(new PropertyValueFactory<Bought, String>("date"));


            listM3 = getDataBought();
            table_yourevents.setItems(listM3);
            System.out.println(listM3);
        }catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,e);
        }


}



/////////////////////////////////////// for all

    public void logout() throws IOException {

        btn_logout.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("LOGOWANIE.fxml"));
        Stage mainStage = new Stage();
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.initStyle(StageStyle.UNDECORATED);
        mainStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        mainStage.show();
    }

    @FXML
    private void handleclose(ActionEvent event) {
        System.exit(0);
    }


///////////////////////////////////////////    do panelu admina


    ObservableList<Events> dataList;
    ObservableList<Events> dataList2;
    @FXML
    public void searchEvents(int choice) {


        if(choice==1)
        {
            col_id.setCellValueFactory(new PropertyValueFactory<Events, Integer>("id"));
            col_name.setCellValueFactory(new PropertyValueFactory<Events, String>("name"));
            col_city.setCellValueFactory(new PropertyValueFactory<Events, String>("city"));
            col_price.setCellValueFactory(new PropertyValueFactory<Events, Integer>("price"));
            col_amount.setCellValueFactory(new PropertyValueFactory<Events, Integer>("amount"));
            col_date.setCellValueFactory(new PropertyValueFactory<Events, String>("date"));
            col_whoadd.setCellValueFactory(new PropertyValueFactory<Events, String>("username"));
            dataList = mysqlconnect.getDataEvents();

            table_events.setItems(dataList);


            FilteredList<Events> filteredData = new FilteredList<>(dataList, b -> true);
            filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(event -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (event.getName().toLowerCase().contains(lowerCaseFilter)) {
                         return true;
                    } else if (event.getCity().toLowerCase().contains(lowerCaseFilter)) {
                         return true;
                    }
                    else
                        return false;
                });
            });
            SortedList<Events> sortedData = new SortedList<>(filteredData);


            sortedData.comparatorProperty().bind(table_events.comparatorProperty());
            table_events.setItems(sortedData);
        }


        else if(choice==2)
        {
            col_id1.setCellValueFactory(new PropertyValueFactory<Events, Integer>("id"));
            col_name1.setCellValueFactory(new PropertyValueFactory<Events, String>("name"));
            col_city1.setCellValueFactory(new PropertyValueFactory<Events, String>("city"));
            col_price1.setCellValueFactory(new PropertyValueFactory<Events, Integer>("price"));
            col_amount1.setCellValueFactory(new PropertyValueFactory<Events, Integer>("amount"));
            col_date1.setCellValueFactory(new PropertyValueFactory<Events, String>("date"));
            dataList2 = mysqlconnect.getDataEvents();

            table_events2.setItems(dataList2);

            FilteredList<Events> filteredData2 = new FilteredList<>(dataList2, b -> true);
            filterField1.textProperty().addListener((observable, oldValue1, newValue1) -> {
                filteredData2.setPredicate(event2 -> {
                    if (newValue1 == null || newValue1.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue1.toLowerCase();

                    if (event2.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (event2.getCity().toLowerCase().contains(lowerCaseFilter)) {
                       return true;
                    }
                    else
                        return false;
                });
            });
            SortedList<Events> sortedData2 = new SortedList<>(filteredData2);



            sortedData2.comparatorProperty().bind(table_events2.comparatorProperty());
            table_events2.setItems(sortedData2);
        }

    }
    public void validationAdd()
    {
        validationAdminPane(1);
    }

    public void validationUpdate()
    {
        validationAdminPane(2);
    }

    public void validationAdminPane(int choice){

        int approve=0;

        if(txt_name.getText().length()<3 || txt_city.getText()==null)
        {

            txt_name.setStyle("-fx-background-color:linear-gradient(to right, #F55050,#F48484); -fx-background-radius:10");
        }
        else
        {
            txt_name.setStyle("-fx-background-color:linear-gradient(to right, #FAF7F0, #EAEAEA); -fx-background-radius:10");
            approve++;
        }


        if(txt_city.getText().length()<3 || txt_city.getText()==null)
        {

            txt_city.setStyle("-fx-background-color:linear-gradient(to right, #F55050,#F48484); -fx-background-radius:10");
        }
        else
        {
            txt_city.setStyle("-fx-background-color:linear-gradient(to right, #FAF7F0, #EAEAEA); -fx-background-radius:10");
            approve++;
        }


        if( txt_price.getText()==null || txt_price.getText().equals("") || parseInt(txt_price.getText())<0)
        {

            txt_price.setStyle("-fx-background-color:linear-gradient(to right, #F55050,#F48484); -fx-background-radius:10");
        }
        else
        {
            txt_price.setStyle("-fx-background-color:linear-gradient(to right, #FAF7F0, #EAEAEA); -fx-background-radius:10");
            approve++;
        }


        if( txt_amount.getText()==null || txt_amount.getText().equals("") || parseInt(txt_amount.getText())<0 )
        {

            txt_amount.setStyle("-fx-background-color:linear-gradient(to right, #F55050,#F48484); -fx-background-radius:10");
        }
        else
        {
            txt_amount.setStyle("-fx-background-color:linear-gradient(to right, #FAF7F0, #EAEAEA); -fx-background-radius:10");
            approve++;
        }
        if(txt_date.getValue()==null)
        {
            txt_date.setStyle("-fx-background-color:linear-gradient(to right, #FAF7F0, #EAEAEA);");
        }

        if(approve==4)
        {
            if(choice==1)
            {
                addEvents();
            }
            else if(choice==2)
            {
                Edit();
            }

            txt_name.setStyle("-fx-background-color:linear-gradient(to right, #FAF7F0, #EAEAEA); -fx-background-radius:10");
            txt_amount.setStyle("-fx-background-color:linear-gradient(to right, #FAF7F0, #EAEAEA); -fx-background-radius:10");
            txt_price.setStyle("-fx-background-color:linear-gradient(to right, #FAF7F0, #EAEAEA); -fx-background-radius:10");
            txt_city.setStyle("-fx-background-color:linear-gradient(to right, #FAF7F0, #EAEAEA); -fx-background-radius:10");

            approve=0;
        }

    }

    public void showEvents() throws SQLException {

        txt_id.setText("");
        txt_name.setText("");
        txt_date.setValue(null);
        txt_city.setText("");
        txt_price.setText("");
        txt_amount.setText("");
        filterField.setText("");

        ObservableList<Events> listM;
        conn = mysqlconnect.ConnectDb();


        col_id.setCellValueFactory(new PropertyValueFactory<Events, Integer>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<Events, String>("name"));
        col_city.setCellValueFactory(new PropertyValueFactory<Events, String>("city"));
        col_price.setCellValueFactory(new PropertyValueFactory<Events, Integer>("price"));
        col_amount.setCellValueFactory(new PropertyValueFactory<Events, Integer>("amount"));
        col_date.setCellValueFactory(new PropertyValueFactory<Events, String>("date"));
        col_whoadd.setCellValueFactory(new PropertyValueFactory<Events, String>("username"));

        listM = mysqlconnect.getDataEvents();
        table_events.setItems(listM);

        searchEvents(1);

    }
    public void addEvents() {



        try {


            if( txt_city.getText().length()>=3 && parseInt(txt_price.getText())>0 && parseInt(txt_amount.getText())>0)
            {

                conn = mysqlconnect.ConnectDb();
                String sql = "insert into Events (name,city,date,price,amount,id_user)values(?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, txt_name.getText());
                pst.setString(2, txt_city.getText());
                pst.setString(3, String.valueOf(txt_date.getValue()));
                pst.setString(4, txt_price.getText());
                pst.setString(5, txt_amount.getText());
                pst.setString(6, String.valueOf(userid));

                pst.execute();

                JOptionPane.showMessageDialog(null, "Event add ");
                showEvents();

            }
            else
            {
                JOptionPane.showMessageDialog(null, "Something went wrong.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }
    @FXML
    void getSelected(MouseEvent event) {

        index = table_events.getSelectionModel().getSelectedIndex();

        if (index <= -1) {

            return;
        }
        txt_id.setText(col_id.getCellData(index).toString());
        txt_name.setText(col_name.getCellData(index));
        txt_city.setText(col_city.getCellData(index));
        txt_date.setValue(LocalDate.parse(col_date.getCellData(index)));
        txt_price.setText(col_price.getCellData(index).toString());
        txt_amount.setText(col_amount.getCellData(index).toString());

    }
    public void Edit() {


        try {
            conn = mysqlconnect.ConnectDb();
            String value1 = txt_id.getText();
            String value2 = txt_name.getText();
            String value3 = txt_city.getText();
            String value4 = String.valueOf(txt_date.getValue());
            String value5 = txt_price.getText();
            String value6 = txt_amount.getText();

            String sql = "update events set name='" + value2 + "', " +
                    "city='" + value3 + "', " +
                    "date='" + value4 + "', " +
                    "price='" + value5 + "', " +
                    "amount='" + value6 + "' " +
                    "WHERE id_eventu='" + value1 + "' ";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Updated");
            showEvents();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }

    }
    public void Delete() {

        conn = mysqlconnect.ConnectDb();
        String sql = "delete from events where id_eventu=?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_id.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Deleted");
            showEvents();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

//////////////////////////////////////////////////////////////     do panelu z kupowaniem biletu
    public void showEventsForUser() {
        txt_amounttobuy.setText("");
        txt_idtobuy.setText("");
        filterField1.setText("");



        ObservableList<Events> listM2;

        col_id1.setCellValueFactory(new PropertyValueFactory<Events, Integer>("id"));
        col_name1.setCellValueFactory(new PropertyValueFactory<Events, String>("name"));
        col_city1.setCellValueFactory(new PropertyValueFactory<Events, String>("city"));
        col_price1.setCellValueFactory(new PropertyValueFactory<Events, Integer>("price"));
        col_amount1.setCellValueFactory(new PropertyValueFactory<Events, Integer>("amount"));
        col_date1.setCellValueFactory(new PropertyValueFactory<Events, String>("date"));


        listM2 = mysqlconnect.getDataEvents();
        table_events2.setItems(listM2);
        searchEvents(2);
    }
    public void getSelectedtobuy(){

        index = table_events2.getSelectionModel().getSelectedIndex();

        if (index <= -1) {

            return;
        }
        txt_idtobuy.setText(col_id1.getCellData(index).toString());


    }
    public void buyTicket(){


if(txt_amounttobuy.getText().equals(""))
{
    txt_amounttobuy.setStyle("-fx-background-color:linear-gradient(to right, #F55050,#F48484); -fx-background-radius:10");
}
else
{
    txt_amounttobuy.setStyle("-fx-background-color:linear-gradient(to right, #CEEDC7,#EAEAEA); -fx-background-radius:10");
}
if(txt_idtobuy.getText().equals(""))
{
    txt_idtobuy.setStyle("-fx-background-color:linear-gradient(to right, #F55050,#F48484); -fx-background-radius:10");
}
else
{
    txt_idtobuy.setStyle("-fx-background-color:linear-gradient(to right, #CEEDC7,#EAEAEA); -fx-background-radius:10");
}
        try {
            int ile_sztuk= parseInt(txt_amounttobuy.getText());
            int idbiletu= parseInt(txt_idtobuy.getText());
            String ile_jest;
            int ile_zostanie;
            if (ile_sztuk < 0) {
                JOptionPane.showMessageDialog(null, " Error");
                txt_amounttobuy.setStyle("-fx-background-color:linear-gradient(to right, #F55050,#F48484); -fx-background-radius:10");
            }
            else{
            conn = mysqlconnect.ConnectDb();
            String sql1 = "Select amount from events where id_eventu = ?";
            pst = conn.prepareStatement(sql1);
            pst.setString(1, String.valueOf(idbiletu));
            rs = pst.executeQuery();
            rs.next();
            ile_jest = rs.getString(1);
            ile_zostanie = parseInt(ile_jest) - ile_sztuk;
            if (ile_zostanie < 0) {
                JOptionPane.showMessageDialog(null, "You can't buy that much");
                txt_amounttobuy.setStyle("-fx-background-color:linear-gradient(to right, #F55050,#F48484); -fx-background-radius:10");
            } else {
                String sql2 = "UPDATE events set amount = " + ile_zostanie + " WHERE id_eventu = " + idbiletu + ";";
                pst = conn.prepareStatement(sql2);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, ile_sztuk + "  tickets bought.");
                txt_amounttobuy.setStyle("-fx-background-color:linear-gradient(to right,#CEEDC7,#EAEAEA); -fx-background-radius:10;");
                showEventsForUser();
                String sql3 = "INSERT INTO bought(id_kupujacego,id_wydarzenia,ile_sztuk) values(" + userid + "," + idbiletu + "," + ile_sztuk + ");";
                pst = conn.prepareStatement(sql3);
                pst.execute();
            }


        }

        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }

    }

/////////////////////////////////////// przy włączaniu

    int userid;
    String rola;
    String mail;
    String currentUsername;

    @FXML
    private void initialize(){
        currentUsername = HelloController.getUsername();
        txt_username_welcome.setText(currentUsername);


        try{
            conn = mysqlconnect.ConnectDb();
            String sql = "Select type,user_id,email from users where username = ?";

            pst = conn.prepareStatement(sql);
            pst.setString(1, currentUsername);
            rs = pst.executeQuery();
            rs.next();

             rola = rs.getString(1);
             userid = rs.getInt(2);
             mail = rs.getString(3);

            System.out.println("User id: "+userid+" \nRola: "+rola);
            if(rola.equals("user")){
                btn_editeventadmin.setVisible(false);

            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
