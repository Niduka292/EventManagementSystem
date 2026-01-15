package com.DTO;

import java.sql.Time;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

public class Event {

    private long eventId;
    private String title;
    private Date date;
    private Time time;
    private String venue;
    private long createdBy; // Foreign key to User
    private Timestamp createdAt;
    private static long eventIdNum = 1;

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Event() {
        setEventId(eventIdNum++);
    }

    public Event(String title, Date date, Time time, String venue, long createdBy) {
        setEventId(eventIdNum++);
        setTitle(title);
        setDate(date);
        setTime(time);
        setVenue(venue);
        setCreatedBy(createdBy);

        Instant now = Instant.now();
        setCreatedAt(Timestamp.from(now));
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", venue='" + venue + '\'' +
                ", createdBy=" + createdBy +
                ", createdAt=" + createdAt +
                '}';
    }
}

