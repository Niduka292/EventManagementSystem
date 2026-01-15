package com.DTO;

import java.util.ArrayList;
import java.util.List;

public class User {

    private long userId;
    private String name;
    private String email;
    private String password;
    private UserRole role;
    private List<String> phoneNums;
    private static long userIdNum = 1;

    public User(){
        setUserId(userIdNum++);
        setPhoneNums(new ArrayList<String>());
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public User(String name, String email, String password, List<String> phoneNums, UserRole role){
        setUserId(userIdNum++);
        setName(name);
        setEmail(email);
        setPassword(password);
        setPhoneNums(phoneNums);
        setRole(role);
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getPhoneNums() {
        return phoneNums;
    }

    public void setPhoneNums(List<String> phoneNums) {
        this.phoneNums = phoneNums;
    }

    public static long getUserIdNums() {
        return userIdNum;
    }

    public static void setUserIdNum(long userIdNum) {
        User.userIdNum = userIdNum;
    }

    public void printPhoneNums() {
        for(String phoneNo: phoneNums){
            System.out.println(phoneNo+',');
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", contactNumbers='" + getPhoneNums() + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
