package Server;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String, String> Users = new HashMap<>();
    private static final String User_File = "UserInfo.txt";

    public UserManager() {
        loadUsers();
    }

    public synchronized boolean Signup(String Username, String Password) {
        if (Users.containsKey(Username)) {
            return false;
        }
        Users.put(Username, Password);
        saveUsers();
        return true;
    }
    private void saveUsers() {
        try (PrintWriter PW = new PrintWriter(new PrintWriter(User_File))) {
            for (Map.Entry<String, String> entry : Users.entrySet()) {
                PW.println(entry.getKey() + ":" + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUsers() {
        try (BufferedReader BR = new BufferedReader(new FileReader(User_File))) {
            String Line;
            while ((Line = BR.readLine()) != null) {
                String[] Parts = Line.split(":");
                if (Parts.length == 2) {
                    Users.put(Parts[0], Parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
