
package bgu.spl.net;

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
		// TODO: implement
		return false;
	}


}
