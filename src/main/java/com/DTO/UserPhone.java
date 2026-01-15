package com.DTO;

public class UserPhone {

    private long userId;
    private String phone;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserPhone(int userId, String phone) {
        this.userId = userId;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserPhone{" +
                "userId=" + userId +
                ", userPhone='" + phone + '\'' +
                '}';
    }
}
