package server;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

import data.User;

import java.security.MessageDigest;

public class UserManager {
    private ConcurrentLinkedQueue<User> users;
    private DatabaseManager databaseManager;

    public static int UNKNOWN_USER_ID = 0;

    public UserManager (DatabaseManager databaseManager) {
        users = new ConcurrentLinkedQueue<>();
        this.databaseManager = databaseManager;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            byte[] bytes = sha1.digest(password.getBytes());
            String result = "";
            for (byte b : bytes) {
                result += String.format("%02x", b);
            }
            //System.out.println(password + " -> " + result);
            return result;
        } catch(Exception e) {
            System.out.println("Ошибка хэширования пароля : " + e);
        }
        return password;
    }

    public int getUserId(String username, String password) {
        String hash = hashPassword(password);
        Optional<User> result = users.stream()
            .filter(user -> (user.getUsername().equals(username) && user.getPassword().equals(hash)))
            .findFirst();
            
        if(result.isPresent()) {
            return result.get().getId();
        } else {
            return UNKNOWN_USER_ID;
        }
    }

    public boolean add (String username, String password) {
        try {
            int userId = getUserId(username, password);
            if(userId == UNKNOWN_USER_ID) {
                String hash = hashPassword(password);
                userId = databaseManager.usersGetNextSequenceNumber();
                User user = new User(userId, username, hash);
                databaseManager.insertUser(user);
                users.add(user);
            } else {
                System.out.println("Пользователь " + username + " уже существует");
            }
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка добавления пользователя : " + e);
            return false;
        }
    }

    public boolean loadUsers() {
        try {
            users = databaseManager.getUsers();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
}
