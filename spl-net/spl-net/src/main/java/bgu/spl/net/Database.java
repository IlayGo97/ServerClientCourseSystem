
package bgu.spl.net;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {

	ConcurrentHashMap<Integer,Course> courses;
	ConcurrentHashMap<String,User> users;

	private static class SingleHolder{
		private static Database instance = new Database();
	}
	//to prevent user from creating new Database
	private Database() {
		this.courses = new ConcurrentHashMap<>();
		this.users = new ConcurrentHashMap<>();
	}


	/**
	 * Retrieves the single instance of this class.
	 */
	public static Database getInstance() {
		return SingleHolder.instance;
	}
	
	/**
	 * loades the courses from the file path specified 
	 * into the Database, returns true if successful.
	 */
	boolean initialize(String coursesFilePath) {
		File file = new File(coursesFilePath);
		try(BufferedReader br = new BufferedReader(new FileReader(file)))
		{
			String line = br.readLine();
			while(line!=null)
			{
				int courseNum = Integer.parseInt(line.substring(0,line.indexOf('|')));
				line = line.substring(line.indexOf('|')+1);
				String courseName =line.substring(0,line.indexOf('|'));
				line = line.substring(line.indexOf('|')+1);
				String temp = line.substring(1,line.indexOf(']'));
				String[] temparr = temp.split(",");
				List<String> kdamCourses = new ArrayList<>();
				kdamCourses = Arrays.asList(temparr);
				List<Integer> realKdamCourses = new ArrayList<>();
				for(String s : kdamCourses)
					realKdamCourses.add(Integer.parseInt(s));
				line = line.substring(line.indexOf('|')+1);
				int maxStudents = Integer.parseInt(line.substring(0,line.indexOf('|')));
				this.courses.put(courseNum, new Course(courseNum,courseName,realKdamCourses,maxStudents));
				line = br.readLine();
			}
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	public ConcurrentHashMap<Integer,Course> getCourses()
	{
		return this.courses;
	}
	public ConcurrentHashMap<String,User> getUsers()
	{
		return this.users;
	}


}
