package com.bankingapplication.bankingapplication.pr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class data {

    @Autowired
    private Connection connection;   // Direct DB connection injection 😱

    @GetMapping("/getUser")
    public Object doStuff(@RequestParam String id) {

        List list = new ArrayList();   // Raw type

        try {

            Statement stmt = connection.createStatement();

            // SQL Injection vulnerability 🚨
            String query = "SELECT * FROM users WHERE id = '" + id + "'";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                Map m = new HashMap();

                m.put("id", rs.getString("id"));
                m.put("name", rs.getString("name"));
                m.put("email", rs.getString("email"));
                m.put("createdAt", new Date());  // Wrong value, not from DB

                list.add(m);
            }

            // Random business logic inside controller 🤦
            if (list.size() == 0) {
                return "No Data Found";
            }

            if (id.equals("1")) {
                System.out.println("Special user accessed");  // System.out logging
            }

            // Hardcoded external API call (fake)
            if (id.equals("999")) {
                return callThirdParty();
            }

        } catch (Exception e) {
            e.printStackTrace();  // Bad error handling
        }

        return list;
    }

    public String callThirdParty() {
        try {
            Thread.sleep(3000); // Blocking thread
        } catch (Exception e) {
        }
        return "External Data";
    }
}