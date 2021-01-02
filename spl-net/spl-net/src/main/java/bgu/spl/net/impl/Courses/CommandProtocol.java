package bgu.spl.net.impl.Courses;

import bgu.spl.net.Course;
import bgu.spl.net.Database;
import bgu.spl.net.User;
import bgu.spl.net.api.MessagingProtocol;

import java.util.concurrent.ConcurrentHashMap;


public class CommandProtocol implements MessagingProtocol<String> {

    Database db;
    User loggedUser;

    public CommandProtocol()
    {
        this.db = Database.getInstance();
        loggedUser = null;
    }

    @Override
    public String process(String msg) {
        String[] words = msg.split(" ");
        switch (words[0])
        {
            case "ADMINREG" :
            {
                String username = words[1];
                String password = words[2];
                return ADMINREG(username, password);

            }
            case "STUDENTREG" :
            {
                String username = words[1];
                String password = words[2];
                return STUDENTREG(username, password);

            }
            case "LOGIN" :
            {
                String username = words[1];
                String password = words[2];
                return LOGIN(username, password);

            }
            case "LOGOUT" :
            {
                return LOGOUT();

            }
            case "COURSEREG" :
            {
                int courseNum = Integer.parseInt(words[1]);
                return COURSEREG(courseNum);

            }
            case "KDAMCHECK" :
            {
                int courseNum = Integer.parseInt(words[1]);
                return KDAMCHECK(courseNum);

            }
            case "COURSESTAT" :
            {
                int courseNum = Integer.parseInt(words[1]);
                return COURSESTAT(courseNum);

            }
            case "STUDENTSTAT" :
            {
                String username = words[1];
                return STUDENTSTAT(username);

            }
            case "ISREGISTERED" :
            {
                int courseNum = Integer.parseInt(words[1]);
                return ISREGISTERED(courseNum);

            }
            case "UNREGISTER" :
            {
                int courseNum = Integer.parseInt(words[1]);
                return UNREGISTER(courseNum);

            }
            case "MYCOURSES" :
            {
                return MYCOURSES();
            }
            default:
                return null;
        }
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }

    private String ADMINREG(String username, String password)
    {
        ConcurrentHashMap<String,User> u =  db.getUsers();
        if(u.containsKey(username))
            return "ERROR 1";
        User toAdd = new User(username, password);
        toAdd.makeAdmin();
        return "ACK 1 Admin registered succesfully";
    }
    private String STUDENTREG(String username, String password)
    {
        ConcurrentHashMap<String,User> u =  db.getUsers();
        if(u.containsKey(username))
            return "ERROR 2";
        User toAdd = new User(username, password);
        return "ACK 2 user registered succesfully";
    }
    private String LOGIN(String username, String password)
    {
        ConcurrentHashMap<String,User> u =  db.getUsers();
        if(!u.containsKey(username))
            return "ERROR 3";
        User currUser = u.get(username);
        if(currUser.verifyPassword(password))
            return "ACK 3";
        else
            return "ERROR 3";
    }
    private String LOGOUT()
    {
        if(loggedUser == null)
            return "ERROR 4";
        else
        {
            loggedUser = null;
            return "ACK 4";
        }
    }
    private String COURSEREG(int courseNum)
    {
        // checking if user is logged and if course exists
        if(loggedUser == null || !db.getCourses().containsKey(courseNum))
            return "ERROR 5";
        //checking if students has all kdam courses
        Course currCourse = db.getCourses().get(courseNum);
        for(Integer tempKdamCourse : currCourse.getKdamCourseList())
        {
            if(!loggedUser.getRegisteredCourses().contains(tempKdamCourse))
                return "ERROR 5";
        }
        //checking slots in course
        if(currCourse.getRegisteredStudents().size()<=currCourse.getMaxStudents())
        {
            return "ACK 5";
        }
        else
            return "ERROR 5";
    }
    private String KDAMCHECK(int courseNum) //TODO check the order of the list (if its the same as file)
    {
        if(loggedUser == null || !db.getCourses().containsKey(courseNum))
            return "ERROR 6";
        Course currCourse = db.getCourses().get(courseNum);
        String courseslist = currCourse.getKdamCourseList().toString();
        courseslist= courseslist.replaceAll(" ","");
        return "ACK 6\n"+courseslist;
    }
    private String COURSESTAT(int courseNum)
    {
        if(loggedUser == null|| !loggedUser.isAdmin() || !db.getCourses().containsKey(courseNum))
            return "ERROR 7";
        Course currCourse = db.getCourses().get(courseNum);
        String CourseNameAndNum = "Course: ("+currCourse.getCourseNumber()+") "+currCourse.getCourseName();
        String SeatsAvailable = "Seats Available: "+currCourse.getRegisteredStudents().size()+"/"+currCourse.getMaxStudents();
        String StudentsRegistered="Students Registered: "+currCourse.getRegisteredStudents().toString().replaceAll(" ","");
        return "ACK 7 \n"+CourseNameAndNum+"\n"+SeatsAvailable+"\n"+StudentsRegistered;

    }
    private String STUDENTSTAT(String Username)
    {
        if(loggedUser == null|| !loggedUser.isAdmin() || !db.getUsers().containsKey(Username))
            return "ERROR 8";
        String StudentUser = "Student: "+Username;
        String CoursesList = "Courses: "+db.getUsers().get(Username).getRegisteredCourses().toString().replaceAll(" ","");
        return "ACK 8\n"+StudentUser+"\n"+CoursesList;
    }
    private String ISREGISTERED(int courseNum)
    {
        if(loggedUser == null || !db.getCourses().containsKey(courseNum))
            return "ERROR 9";
        if(loggedUser.getRegisteredCourses().contains(courseNum))
        {
            return "ACK 9\n"+"REGISTERED";
        }
        else
        {
            return "ACK 9\n"+"NOT REGISTERED";
        }
    }
    private String UNREGISTER(int courseNum)
    {
        if(loggedUser == null || !loggedUser.getRegisteredCourses().contains(courseNum))
            return "ERROR 10";
        loggedUser.getRegisteredCourses().remove(courseNum);
        return "ACK 10";
    }
    private String MYCOURSES()
    {
        if(loggedUser == null)
            return "ERROR 11";
        return "ACK 11\n"+loggedUser.getRegisteredCourses().toString().replaceAll(" ","");
    }

}
