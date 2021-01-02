package bgu.spl.net;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class User {
    private boolean isAdmin;
    private String username;
    private String password;
    private List<Integer> registeredCourses;

    public User(String username, String password)
    {
        this.isAdmin = false;
        this.username = username;
        this.password = password;
        registeredCourses = new CopyOnWriteArrayList<>();
    }
    public void makeAdmin()
    {
        this.isAdmin=true;
    }
    public boolean verifyPassword(String passAtempt)
    {
        return passAtempt.equals(password);
    }
    public List<Integer> getRegisteredCourses(){
        return this.registeredCourses;
    }
    public boolean isAdmin()
    {
        return isAdmin;
    }

}
