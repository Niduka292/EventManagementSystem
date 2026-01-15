package com.app;

import com.Util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Connection conn = null;
        try{
            conn = DBConnectionUtil.getConnection();
        }catch(SQLException e){
            e.printStackTrace();
        }

        if(conn != null){
            System.out.println("Connection Established");
        }else{
            System.out.println("Connection Not Established");
        }
    }
}