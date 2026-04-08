package com.register;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * RegisterServlet
 * Receives JSON POST from the frontend, validates the fields,
 * and stores them in a MySQL table called `users`.
 *
 * Dependencies (add to pom.xml / WEB-INF/lib):
 *   - mysql-connector-j  (com.mysql:mysql-connector-j:8.3.0)
 *   - gson               (com.google.code.gson:gson:2.10.1)
 *   - jakarta.servlet-api or javax.servlet-api (provided)
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    // ── DB config ── change these to match your MySQL setup ──
    private static final String DB_URL      = "jdbc:mysql://localhost:3306/registration_db"
                                            + "?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER     = "root";         // your MySQL username
    private static final String DB_PASSWORD = "yourpassword"; // your MySQL password

    private static final String INSERT_SQL  =
        "INSERT INTO users (name, email, phone) VALUES (?, ?, ?)";

    // ── Load JDBC driver once ──
    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ServletException("MySQL JDBC Driver not found.", e);
        }
    }

    // ── Handle POST ──
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // 1. Read request body
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
        }

        // 2. Parse JSON
        JsonObject json;
        try {
            json = JsonParser.parseString(sb.toString()).getAsJsonObject();
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid JSON\"}");
            return;
        }

        String name  = json.has("name")  ? json.get("name").getAsString().trim()  : "";
        String email = json.has("email") ? json.get("email").getAsString().trim() : "";
        String phone = json.has("phone") ? json.get("phone").getAsString().trim() : "";

        // 3. Basic validation
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"All fields are required\"}");
            return;
        }

        // 4. Insert into MySQL
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.executeUpdate();

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"message\":\"User registered successfully\"}");

        } catch (SQLIntegrityConstraintViolationException e) {
            // Duplicate email
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            resp.getWriter().write("{\"error\":\"Email already registered\"}");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"Database error\"}");
        }
    }
}
