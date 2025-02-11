package org.example.util.db;

import org.example.model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBRoom {
    public static List<Room> getRoomsByBuildingId(int id) {
        try (Connection conn = DBConnect.GetConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM rooms WHERE building_id = ?");
            statement.setInt(1, id);

            List<Room> rooms = new ArrayList<>();

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Room r = new Room(
                        rs.getInt("id"),
                        rs.getInt("number"),
                        rs.getFloat("area")
                );
                rooms.add(r);
            }

            return rooms;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

            return null;
        }
    }

    public static boolean createRoom(Room room, int buildingId) {
        try (Connection conn = DBConnect.GetConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO rooms (number, area, building_id) VALUES (?, ?, ?)"
            );

            statement.setInt(1, room.getNumber());
            statement.setFloat(2, room.getArea());
            statement.setInt(3, buildingId);

            int res = statement.executeUpdate();

            return res > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

            return false;
        }
    }

    public static boolean deleteRoom(int id) {
        try (Connection conn = DBConnect.GetConnection()) {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM rooms WHERE id = ?");

            statement.setInt(1, id);

            int res = statement.executeUpdate();

            return res > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

            return false;
        }
    }

    public static boolean updateRoom(Room room) {
        try (Connection conn = DBConnect.GetConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "UPDATE rooms SET number = ?, area = ? WHERE id = ?"
            );

            statement.setInt(1, room.getNumber());
            statement.setFloat(2, room.getArea());
            statement.setInt(3, room.getId());

            int res = statement.executeUpdate();

            return res > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

            return false;
        }
    }
}