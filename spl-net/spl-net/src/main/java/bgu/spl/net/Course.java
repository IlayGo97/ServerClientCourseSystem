package bgu.spl.net;

import java.util.LinkedList;
import java.util.List;

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
        this.kdamCourseList = new LinkedList<>();
        for (Integer num : kdam)
        {
            this.kdamCourseList.add(num);
        }
        this.maxStudents = maxStudents;
        this.registeredStudents = new LinkedList<>();
    }



}
