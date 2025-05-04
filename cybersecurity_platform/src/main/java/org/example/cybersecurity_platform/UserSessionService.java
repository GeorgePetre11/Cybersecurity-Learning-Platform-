package org.example.cybersecurity_platform;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserSessionService {

    private static final String USERS_SESSION_KEY = "users";
    private static final String LOGGED_IN_USER = "currentUser";

    @SuppressWarnings("unchecked")
    private Map<String, String> getUsers(HttpSession session) {
        Object users = session.getAttribute(USERS_SESSION_KEY);
        if (users == null) {
            Map<String, String> newUsers = new HashMap<>();
            session.setAttribute(USERS_SESSION_KEY, newUsers);
            return newUsers;
        }
        return (Map<String, String>) users;
    }

    public boolean registerUser(HttpSession session, String username, String password) {
        Map<String, String> users = getUsers(session);
        if (users.containsKey(username)) return false;
        users.put(username, password);
        return true;
    }

    public boolean loginUser(HttpSession session, String username, String password) {
        Map<String, String> users = getUsers(session);
        if (password.equals(users.get(username))) {
            session.setAttribute(LOGGED_IN_USER, username);
            return true;
        }
        return false;
    }

    public String getCurrentUser(HttpSession session) {
        return (String) session.getAttribute(LOGGED_IN_USER);
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }
}
