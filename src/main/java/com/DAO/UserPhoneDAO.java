package com.DAO;

import com.Util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserPhoneDAO {

    // Add phone number for a user
    public boolean addPhoneNumber(Long userId, String phoneNumber) {
        String sql = "INSERT INTO User_phone (UserId, UserPhone) VALUES (?, ?)";

        try(Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setLong(1, userId);
            stmt.setString(2, phoneNumber);

            return stmt.executeUpdate() > 0;

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    // Get all phone numbers for a user
    public List<String> getPhoneNumbersByUserId(Long userId) {
        List<String> phoneNumbers = new ArrayList<>();
        String sql = "SELECT UserPhone FROM User_phone WHERE UserId = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                phoneNumbers.add(rs.getString("UserPhone"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return phoneNumbers;
    }

    // Delete a specific phone number
    public boolean deletePhoneNumber(Long userId, String phoneNumber) {
        String sql = "DELETE FROM User_phone WHERE UserId = ? AND UserPhone = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            stmt.setString(2, phoneNumber);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete all phone numbers for a user
    public boolean deleteAllPhoneNumbers(Long userId) {
        String sql = "DELETE FROM User_phone WHERE UserId = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update phone numbers for a user (delete old, insert new)
    public boolean updatePhoneNumbers(Long userId, List<String> phoneNumbers) {
        Connection conn = null;
        try {
            conn = DBConnectionUtil.getConnection();
            conn.setAutoCommit(false);

            // Delete existing phone numbers
            String deleteSql = "DELETE FROM User_phone WHERE UserId = ?";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                deleteStmt.setLong(1, userId);
                deleteStmt.executeUpdate();
            }

            // Insert new phone numbers
            if (phoneNumbers != null && !phoneNumbers.isEmpty()) {
                String insertSql = "INSERT INTO User_phone (UserId, UserPhone) VALUES (?, ?)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    for (String phone : phoneNumbers) {
                        if (phone != null && !phone.trim().isEmpty()) {
                            insertStmt.setLong(1, userId);
                            insertStmt.setString(2, phone.trim());
                            insertStmt.addBatch();
                        }
                    }
                    insertStmt.executeBatch();
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}