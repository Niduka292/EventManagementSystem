package com.DAO;

import com.DTO.User;
import com.DTO.UserRole;
import com.Util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private UserPhoneDAO userPhoneDAO = new UserPhoneDAO();

    // Insert new user with phone numbers
    public boolean insertUser(User user, List<String> phoneNumbers) {
        String sql = "INSERT INTO \"User\" (name, email, role, password) VALUES (?, ?, ?, ?) RETURNING UserId";
        Connection conn = null;

        try{
            conn = DBConnectionUtil.getConnection();

            long userId = 0;
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getRole().toString());
                stmt.setString(4, user.getPassword());

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    userId = rs.getLong("UserId");
                    user.setUserId(userId);
                }
            }

            // Insert phone numbers
            if(userId > 0 && phoneNumbers != null && !phoneNumbers.isEmpty()){
                String phoneSql = "INSERT INTO User_phone (userID, userPhone) VALUES (?, ?)";
                try(PreparedStatement phoneStmt = conn.prepareStatement(phoneSql)){
                    for(String phone : phoneNumbers){
                        if(phone != null && !phone.trim().isEmpty()){
                            phoneStmt.setLong(1, userId);
                            phoneStmt.setString(2, phone.trim());
                            phoneStmt.addBatch();
                        }
                    }
                    phoneStmt.executeBatch();
                }
            }

            return true;

        }catch(SQLException e){
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        }finally{
            if(conn != null){
                try{
                    conn.setAutoCommit(true);
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    // Get user by ID with phone numbers
    public User getUserById(Long userId){
        String sql = "SELECT * FROM \"User\" WHERE userID = ?";
        User user = null;

        try(Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                user = new User();
                user.setUserId(rs.getInt("userID"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(UserRole.valueOf(rs.getString("role")));

                List<String> phones = userPhoneDAO.getPhoneNumbersByUserId(userId);
                user.setPhoneNums(phones);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return user;
    }

    // Get user by email (for login)
    public User getUserByEmail(String email){
        String sql = "SELECT * FROM \"User\" WHERE email = ?";
        User user = null;

        try(Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                user = new User();
                user.setUserId(rs.getInt("userID"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(UserRole.valueOf(rs.getString("role")));

                List<String> phones = userPhoneDAO.getPhoneNumbersByUserId(user.getUserId());
                user.setPhoneNums(phones);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return user;
    }

    // Get all users with their phone numbers
    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM \"User\" ORDER BY UserId";

        try(Connection conn = DBConnectionUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("userID"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(UserRole.valueOf(rs.getString("role")));

                List<String> phones = userPhoneDAO.getPhoneNumbersByUserId(user.getUserId());
                user.setPhoneNums(phones);

                users.add(user);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return users;
    }

    // Get all admins with their phone numbers
    public List<User> getAllAdmins(){
        List<User> admins = new ArrayList<>();
        String sql = "SELECT * FROM \"User\" WHERE role=\"ADMIN\" ORDER BY userID";

        try(Connection conn = DBConnectionUtil.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("UserId"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(UserRole.valueOf(rs.getString("role")));

                List<String> phones = userPhoneDAO.getPhoneNumbersByUserId(user.getUserId());
                user.setPhoneNums(phones);

                admins.add(user);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return admins;
    }

    // Get all admins with their phone numbers
    public List<User> getAllNormalUsers(){
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM \"User\" WHERE role=\"NORMAL_USER\" ORDER BY userID";

        try(Connection conn = DBConnectionUtil.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("UserId"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(UserRole.valueOf(rs.getString("role")));

                List<String> phones = userPhoneDAO.getPhoneNumbersByUserId(user.getUserId());
                user.setPhoneNums(phones);

                users.add(user);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return users;
    }


    // Update user information
    public boolean updateUser(User user){
        String sql = "UPDATE \"User\" SET name = ?, email = ?, role = ? WHERE userID = ?";

        try(Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getRole().toString());
            stmt.setLong(4, user.getUserId());

            return stmt.executeUpdate() > 0;

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    // Update password
    public boolean updatePassword(Long userId, String newPassword){
        String sql = "UPDATE \"User\" SET password = ? WHERE userID = ?";

        try(Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, newPassword);
            stmt.setLong(2, userId);

            return stmt.executeUpdate() > 0;

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    // Delete user (will cascade delete phone numbers and registrations)
    public boolean deleteUser(Long userId){
        Connection conn = null;

        try{
            conn = DBConnectionUtil.getConnection();

            // Delete phone numbers first
            userPhoneDAO.deleteAllPhoneNumbers(userId);

            // Delete user
            String sql = "DELETE FROM \"User\" WHERE userID = ?";
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setLong(1, userId);
                stmt.executeUpdate();
            }

            return true;

        }catch(SQLException e){
            if(conn != null){
                try{
                    conn.rollback();
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        }finally{
            if(conn != null){
                try{
                    conn.setAutoCommit(true);
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    // Check if email already exists
    public boolean emailExists(String email){
        String sql = "SELECT COUNT(*) FROM \"User\" WHERE email = ?";

        try(Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return rs.getInt(1) > 0;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    // Get users by role
    public List<User> getUsersByRole(String role){
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM \"User\" WHERE role = ? ORDER BY UserId";

        try(Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, role);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                User user = new User();
                user.setUserId(rs.getInt("UserId"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(UserRole.valueOf(rs.getString("role")));

                List<String> phones = userPhoneDAO.getPhoneNumbersByUserId(user.getUserId());
                user.setPhoneNums(phones);

                users.add(user);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return users;
    }

    // Returns a UserDTO if login is successful, null otherwise
    public User login(String email, String password){
        String sql = "SELECT id, name, email, role FROM User WHERE email = ? AND password = ?";

        try(Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            // Bind parameters safely
            stmt.setString(1, email);
            stmt.setString(2, password);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    // Build and return UserDTO
                    User user = new User();
                    user.setUserId(rs.getLong("userID"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setRole(UserRole.valueOf(rs.getString("role")));
                    return user;
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return null; // login failed
    }
}
