package com.example.crud;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Database {

    public static Connection connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/javafxCRUD", "root", "");
            return connect;

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
