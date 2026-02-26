package com.bankingapplication.bankingapplication.pr;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("/v1")
@Component
public class UserManagerUtilControllerHelper {

    @Autowired
    private DataSource ds;  // No constructor injection

    private static Map cache = new HashMap(); // Raw type + not thread safe
    private static List globalList = new ArrayList(); // Memory leak risk

    private String dbUser = "root";           // Hardcoded credentials
    private String dbPassword = "password";   // Hardcoded credentials

    @GetMapping("/user")
    public Object process(@RequestParam String id,
                          @RequestParam(required = false) String role,
                          @RequestHeader Map headers) {

        System.out.println("Headers: " + headers); // Logging sensitive info

        List result = new ArrayList();

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/test", dbUser, dbPassword);

            Statement stmt = conn.createStatement();

            // SQL Injection vulnerability
            String query = "SELECT * FROM users WHERE id = '" + id + "'";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Map user = new HashMap();

                user.put("id", rs.getString("id"));
                user.put("name", rs.getString("name"));
                user.put("password", rs.getString("password")); // Exposing password

                result.add(user);
                cache.put(id, user); // Shared mutable state
            }

            // No resource closing (Connection, Statement, ResultSet)

            if (role.equals("ADMIN")) {  // Null pointer risk
                deleteAllUsers();        // Dangerous side-effect in GET
            }

            if (id.equals("999")) {
                callExternalApi();  // Blocking external call
            }

            // XSS risk
            if (headers.containsKey("X-User-Input")) {
                return "<html>" + headers.get("X-User-Input") + "</html>";
            }

        } catch (Exception e) {
            // Swallowed exception
        }

        globalList.add(result); // Memory keeps growing

        return result;
    }

    public void deleteAllUsers() throws Exception {
        Connection conn = ds.getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("DELETE FROM users"); // No transaction, no validation
    }

    public String callExternalApi() throws Exception {
        URL url = new URL("http://insecure-api.com/data"); // HTTP not HTTPS
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        Thread.sleep(5000); // Blocking thread

        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));

        String inputLine;
        StringBuffer content = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();

        return content.toString();
    }

    public void randomMethod() {
        try {
            int x = 10 / 0; // Runtime crash
        } catch (Exception e) {
        }
    }
}
