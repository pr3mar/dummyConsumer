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

    private static boolean isUnique(int userId) {
        for(User user : users) {
            if(userId == user.getId()) {
                return false;
            }
        }
        return true;
    }

    private static int getUniqueId() {
        return users.size();
    }

    public static void addUser(User user) {

        if(user.getMeta().equals("")) {
            user.setMeta("[u] No meta data.");
        }

        if(isUnique(user.getId())) {
            users.add(user);
        } else {
            user.setId(getUniqueId());
            users.add(user);
        }
    }

    public static void deleteUser(int userId) {
        for(User user : users) {
            if(user.getId() == userId) {
                users.remove(user);
                break;
            }
        }
    }

    public static boolean isEmpty() { return users.isEmpty(); }
}
