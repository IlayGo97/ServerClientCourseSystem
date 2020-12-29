package bgu.spl.net;

import java.util.LinkedList;
import java.util.List;

public class User {
    private boolean isAdmin;
    private String username;
    private String password;
    private List<Course> registeredCourses;

    public User(String username, String password)
    {
        this.isAdmin = false;
        this.username = username;
        this.password = password;
        registeredCourses = new LinkedList<>();
    }

    
}
