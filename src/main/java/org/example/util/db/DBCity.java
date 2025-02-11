package org.example.util.db;

import org.example.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBCity {
    public static List<City> getAllCities() {
        try (Connection conn = DBConnect.GetConnection()){
            Statement statement = conn.createStatement();

            List<City> cities = new ArrayList<>();

            ResultSet rs = statement.executeQuery("SELECT * FROM cities ORDER BY id");
            while (rs.next()) {
                City city = new City(rs.getInt("id"), rs.getString("name"));
                cities.add(city);
            }

            return cities;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

            return null;
        }
    }

    public static boolean createCity(City city) {
        try (Connection conn = DBConnect.GetConnection()){
            PreparedStatement statement = conn.prepareStatement("INSERT INTO cities (name) VALUES (?)");

            statement.setString(1, city.getName());

            int res = statement.executeUpdate();

            return res > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

            return false;
        }
    }

    public static boolean deleteCity(int id) {
        try (Connection conn = DBConnect.GetConnection()){
            PreparedStatement statement = conn.prepareStatement("DELETE FROM cities WHERE id = ?");

            statement.setInt(1, id);

            int res = statement.executeUpdate();

            return res > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

            return false;
        }
    }

    public static boolean updateCityName(int id, String newName) {
        try (Connection conn = DBConnect.GetConnection()) {
            PreparedStatement statement = conn.prepareStatement("UPDATE cities SET name = ? WHERE id = ?");

            statement.setString(1, newName);
            statement.setInt(2, id);

            int res = statement.executeUpdate();

            return res > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

            return false;
        }
    }


    public static City getCityById(int id) {
        try (Connection conn = DBConnect.GetConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM cities WHERE id = ?");

            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {

                return new City(rs.getInt("id"), rs.getString("name"));
            } else {

                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

            return null;
        }
    }
}
