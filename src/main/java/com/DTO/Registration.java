package com.DTO;

import java.sql.Timestamp;

public class Registration {

    private long registrationId;
    private Timestamp registrationDate;
    private RegistrationStatus status;
    private long eventId;
    private long userId;
    private static long regIdNum = 1;

    public long getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(long registrationId) {
        this.registrationId = registrationId;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    public RegistrationStatus getStatus() {
        return status;
    }

    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public static long getRegIdNum() {
        return regIdNum;
    }

    public static void setRegIdNum(long regIdNum) {
        Registration.regIdNum = regIdNum;
    }

    public Registration() {
        setRegistrationId(regIdNum++);
    }

    public Registration(Timestamp registrationDate, RegistrationStatus status, long eventId, long userId) {
        setRegistrationId(regIdNum++);
        setRegistrationDate(registrationDate);
        setStatus(status);
        setEventId(eventId);
        setUserId(userId);

    }

    @Override
    public String toString() {
        return "Registration{" +
                "registrationId=" + registrationId +
                ", registrationDate=" + registrationDate +
                ", status='" + status + '\'' +
                ", eventId=" + eventId +
                ", userId=" + userId +
                '}';
    }
}
