package com.DAO;

import com.DTO.Event;
import com.Util.DBConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    // Insert new event (createdAt is set automatically by database)
    public boolean insertEvent(Event event) {
        String sql = "INSERT INTO Event(eventID, title, date, time, venue, createdBy, createdAt) " +
                "VALUES (?, ?, ?, ?, ?, ? CURRENT_TIMESTAMP)";

        try(Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, event.getTitle());
            stmt.setDate(2, event.getDate());
            stmt.setTime(3, event.getTime());
            stmt.setString(4, event.getVenue());
            stmt.setLong(5, event.getCreatedBy());

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                event.setEventId(rs.getInt("EventId"));
                return true;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    // Get event by ID
    public Event getEventById(Long eventId){
        String sql = "SELECT e.*, u.name as creator_name " +
                "FROM Event e " +
                "LEFT JOIN \"User\" u ON e.createdBy = u.UserId " +
                "WHERE e.EventId = ?";
        Event event = null;

        try(Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setLong(1, eventId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                event = new Event();
                event.setEventId(rs.getLong("EventId"));
                event.setTitle(rs.getString("title"));
                event.setDate(rs.getDate("date"));
                event.setTime(rs.getTime("time"));
                event.setVenue(rs.getString("venue"));
                event.setCreatedBy(rs.getLong("createdBy"));
                event.setCreatedAt(rs.getTimestamp("createdAt"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return event;
    }

    // Get all events
    public List<Event> getAllEvents(){
        List<Event> events = new ArrayList<>();
        String sql = "SELECT e.*, u.name as creator_name " +
                "FROM Event e " +
                "LEFT JOIN \"User\" u ON e.createdBy = u.UserId " +
                "ORDER BY e.date DESC, e.time DESC";

        try(Connection conn = DBConnectionUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()){
                Event event = new Event();
                event.setEventId(rs.getLong("EventId"));
                event.setTitle(rs.getString("title"));
                event.setDate(rs.getDate("date"));
                event.setTime(rs.getTime("time"));
                event.setVenue(rs.getString("venue"));
                event.setCreatedBy(rs.getLong("createdBy"));
                event.setCreatedAt(rs.getTimestamp("createdAt"));

                events.add(event);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return events;
    }

    // Get upcoming events(future events only)
    public List<Event> getUpcomingEvents(){
        List<Event> events = new ArrayList<>();
        String sql = "SELECT e.*" +
                "FROM Event e " +
                "LEFT JOIN \"User\" u ON e.createdBy = u.UserId " +
                "WHERE e.date >= CURRENT_DATE " +
                "ORDER BY e.date ASC, e.time ASC";

        try(Connection conn = DBConnectionUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()) {
                Event event = new Event();
                event.setEventId(rs.getLong("EventId"));
                event.setTitle(rs.getString("title"));
                event.setDate(rs.getDate("date"));
                event.setTime(rs.getTime("time"));
                event.setVenue(rs.getString("venue"));
                event.setCreatedBy(rs.getLong("createdBy"));
                event.setCreatedAt(rs.getTimestamp("createdAt"));

                events.add(event);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return events;
    }

    // Update event
    public boolean updateEvent(Event event){
        String sql = "UPDATE Event SET title = ?, date = ?, time = ?, venue = ? " +
                "WHERE EventId = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, event.getTitle());
            stmt.setDate(2, event.getDate());
            stmt.setTime(3, event.getTime());
            stmt.setString(4, event.getVenue());
            stmt.setLong(5, event.getEventId());

            return stmt.executeUpdate() > 0;

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    // Delete event
    public boolean deleteEvent(int eventId) {
        String sql = "DELETE FROM Event WHERE EventId = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Search events by title
    public List<Event> searchEventsByTitle(String searchTerm) {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT e.*" +
                "FROM Event e " +
                "LEFT JOIN \"User\" u ON e.createdBy = u.UserId " +
                "WHERE LOWER(e.title) LIKE LOWER(?) " +
                "ORDER BY e.date DESC";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + searchTerm + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Event event = new Event();
                event.setEventId(rs.getLong("EventId"));
                event.setTitle(rs.getString("title"));
                event.setDate(rs.getDate("date"));
                event.setTime(rs.getTime("time"));
                event.setVenue(rs.getString("venue"));
                event.setCreatedBy(rs.getLong("createdBy"));
                event.setCreatedAt(rs.getTimestamp("createdAt"));

                events.add(event);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return events;
    }
}
