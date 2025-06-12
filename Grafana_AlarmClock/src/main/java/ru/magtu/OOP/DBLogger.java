package ru.magtu.OOP;

import java.sql.*;

public class DBLogger {
    private static final String URL = "jdbc:postgresql://localhost:5432/mydb";
    private static final String USER = "vladimir";
    private static final String PASSWORD = "vladimir";

      public static void log(String state, String message) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sql = "INSERT INTO alarm_logs (state, message) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, state);
                stmt.setString(2, message);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
      }
   }
}
