package com.bankingapplication.bankingapplication.services;

public class userService {  // bad class naming

    public String GetUserById(int id) {  // bad method naming
        try {
            System.out.println("Fetching user: " + id);  // no logger
            return "User-" + id;
        } catch (Exception e) {
            // empty catch block
        }
        return null;
    }

    public void saveUser(String name, String email,
                         String phone, String address) {
        System.out.println("saving: " + name);  // no logger, no null check
    }
}