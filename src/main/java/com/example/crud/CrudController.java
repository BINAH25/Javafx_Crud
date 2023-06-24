package com.example.crud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CrudController implements Initializable {
    @FXML
    private Button add_btn;

    @FXML
    private Button clear_btn;

    @FXML
    private TableColumn<StudentData, String> column_1;

    @FXML
    private TableColumn<StudentData, String> column_2;

    @FXML
    private TableColumn<StudentData, String> column_3;

    @FXML
    private TableColumn<StudentData, String> column_4;

    @FXML
    private TableColumn<StudentData, String> column_5;

    @FXML
    private ComboBox<?> course;

    @FXML
    private Button delete_btn;

    @FXML
    private TextField full_name;

    @FXML
    private ComboBox<?> gender;

    @FXML
    private TextField student_number;

    @FXML
    private TableView<StudentData> crud_tableView;
    @FXML
    private Button update_btn;

    @FXML
    private ComboBox<?> year;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    private Alert alert;

    public void addStudent(){
        connect = Database.connect();
        try {
            if(student_number.getText().isEmpty()
            || full_name.getText().isEmpty()
            || year.getSelectionModel().getSelectedItem() == null
            || course.getSelectionModel().getSelectedItem() == null
            || gender.getSelectionModel().getSelectedItem() == null){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blanks fields ");
                alert.showAndWait();
            }else{
                String checkData = "SELECT student_number FROM student_info WHERE student_number = " + student_number.getText();
                prepare = connect.prepareStatement(checkData);
                result = prepare.executeQuery();

                if(result.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Student Number: " + student_number.getText() + "already exist");
                    alert.showAndWait();
                }else{
                    String insertData = "INSERT INTO student_info (student_number,full_name,year,course,gender,date) VALUES (?,?,?,?,?,?)";
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1,student_number.getText());
                    prepare.setString(2,full_name.getText());
                    prepare.setString(3,(String) year.getSelectionModel().getSelectedItem());
                    prepare.setString(4,(String) course.getSelectionModel().getSelectedItem());
                    prepare.setString(5,(String) gender.getSelectionModel().getSelectedItem());

                    Date date  = new Date();
                    java.sql.Date sqldate = new java.sql.Date(date.getTime());
                    prepare.setString(6,String.valueOf(sqldate));
                    prepare.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Student Added Successfully");
                    alert.showAndWait();

                    // TO UPDATE THE TABLE VIEW
                    showStudentData();
                    // TO CLEAR THE STUDENT FORM
                    clearForm();

                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateStuden(){

    }

    public void deleteStudent(){

    }
    public void clearForm(){
        student_number.setText("");
        full_name.setText("");
        year.getSelectionModel().clearSelection();
        course.getSelectionModel().clearSelection();
        gender.getSelectionModel().clearSelection();

    }
    public ObservableList<StudentData> StudentDatalist(){
        ObservableList<StudentData> listData = FXCollections.observableArrayList();
        String data = "SELECT * FROM student_info";
        connect = Database.connect();
        try {
            prepare = connect.prepareStatement(data);
            result = prepare.executeQuery();
            StudentData student_data;

            while (result.next()){
                student_data = new StudentData(
                        result.getInt("student_number"),
                        result.getString("full_name"),
                        result.getString("year"),
                        result.getString("course"),
                        result.getString("gender")
                );
                listData.add(student_data);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }

    // GET VALUES FOR THE COMBO
    private String[] yearList = {"First Year","Second Year","Third Year","Fourth Year"};

    public  void studentYearList(){
        List<String> yList = new ArrayList<>();
        for(String data: yearList){
            yList.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(yList);
        year.setItems(listData);
    }
    // GET VALUES FOR THE COMBO
    private String[] courseList = {"Computer Science","Information Technology","Software Engineering","Data Science"};
    public  void studentCourseList(){
        List<String> CList = new ArrayList<>();
        for(String data: courseList){
            CList.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(CList);
        course.setItems(listData);
    }
    // GET VALUES FOR THE COMBO
    private  String[] genderList = {"Male","Female"};
    public  void studentGenderList(){
        List<String> gList = new ArrayList<>();
        for(String data: genderList){
            gList.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(gList);
        gender.setItems(listData);
    }
    private  ObservableList<StudentData> StudentData;
    public  void showStudentData(){
        StudentData = StudentDatalist();
        column_1.setCellValueFactory(new PropertyValueFactory<>("student_number"));
        column_2.setCellValueFactory(new PropertyValueFactory<>("full_name"));
        column_3.setCellValueFactory(new PropertyValueFactory<>("year"));
        column_4.setCellValueFactory(new PropertyValueFactory<>("course"));
        column_5.setCellValueFactory(new PropertyValueFactory<>("gender"));
        crud_tableView.setItems(StudentData);

    }
    public void selectStudentdata(){
        StudentData student_data = crud_tableView.getSelectionModel().getSelectedItem();
        int num = crud_tableView.getSelectionModel().getSelectedIndex();

        if((num - 1) < -1) return;
        student_number.setText(String.valueOf(student_data.getStudent_number()));
        full_name.setText(String.valueOf(student_data.getFull_name()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resoures){

        studentYearList();
        studentCourseList();
        studentGenderList();
        showStudentData();
    }


}


