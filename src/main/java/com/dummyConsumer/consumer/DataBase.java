package com.dummyConsumer.consumer;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private static List<User> users = new ArrayList<>();

    public static List<User> getUsers() { return users; }

    public static User getUser(int userId) {
        for(User user : users) {
            if(user.getId() == userId) {
                return user;
            }
        }
        return null;
    }

    public static void addUser(User user) { users.add(user); }

    public static void deleteUser(int userId) {
        for(User user : users) {
            if(user.getId() == userId) {
                users.remove(user);
                break;
            }
        }
    }
}
