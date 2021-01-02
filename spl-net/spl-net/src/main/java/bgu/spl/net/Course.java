package bgu.spl.net;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Course {
    private int courseNumber;
    private String courseName;
    private List<Integer> kdamCourseList;
    private int maxStudents;
    private List<User> registeredStudents;


    public Course(int courseNumber, String courseName, List<Integer> kdam, int maxStudents )
    {
        this.courseNumber=courseNumber;
        this.courseName = courseName;
        this.kdamCourseList = new CopyOnWriteArrayList<>();
        for (Integer num : kdam)
        {
            this.kdamCourseList.add(num);
        }
        this.maxStudents = maxStudents;
        this.registeredStudents = new CopyOnWriteArrayList<>();
    }
    public List<Integer> getKdamCourseList()
    {
        return kdamCourseList;
    }
    public List<User> getRegisteredStudents()
    {
        return this.registeredStudents;
    }
    public int getMaxStudents()
    {
        return this.maxStudents;
    }
    public int getCourseNumber(){
        return  this.courseNumber;
    }
    public String getCourseName(){
        return this.courseName;
    }

}
