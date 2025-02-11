package org.example.util.db;

import org.example.model.Building;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBBuilding {
    public static List<Building> getBuildingsByCityId(int id) {
        try (Connection conn = DBConnect.GetConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM buildings WHERE city_id = ?");
            statement.setInt(1, id);

            List<Building> buildings = new ArrayList<>();

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Building b = new Building(
                        rs.getInt("id"),
                        rs.getString("street"),
                        rs.getInt("house_number"),
                        rs.getInt("payment_month_per_sq_m")
                );
                buildings.add(b);
            }

            return buildings;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

            return null;
        }
    }

    public static boolean createBuilding(Building building, int cityId) {
        try (Connection conn = DBConnect.GetConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO buildings (street, house_number, payment_month_per_sq_m, city_id) VALUES (?, ?, ?, ?)"
            );

            statement.setString(1, building.getStreetName());
            statement.setInt(2, building.getHouseNumber());
            statement.setInt(3, building.getPaymentMonthPerSqM());
            statement.setInt(4, cityId);

            int res = statement.executeUpdate();

            return res > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

            return false;
        }
    }

    public static boolean deleteBuilding(int id) {
        try (Connection conn = DBConnect.GetConnection()) {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM buildings WHERE id = ?");

            statement.setInt(1, id);

            int res = statement.executeUpdate();

            return res > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

            return false;
        }
    }

    public static boolean updateBuilding(Building building) {
        try (Connection conn = DBConnect.GetConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "UPDATE buildings SET street = ?, house_number = ?, payment_month_per_sq_m = ? WHERE id = ?"
            );

            statement.setString(1, building.getStreetName());
            statement.setInt(2, building.getHouseNumber());
            statement.setInt(3, building.getPaymentMonthPerSqM());
            statement.setInt(4, building.getId());

            int res = statement.executeUpdate();

            return res > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

            return false;
        }
    }
}