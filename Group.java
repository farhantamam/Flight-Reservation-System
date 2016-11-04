import java.util.*;

/**
 * Programming Assignment 1 Solution
 * @author Mohmad Tamam
 * Copyright 2016
 * @version 1.0
 *
 */

/**
 * Represents a Group of Passengers with a group name to be seated on the Plane.
 *
 */
public class Group {

	private ArrayList group = new ArrayList<Passenger>();
	private String name;
	
	/**
	 * Class constructor specifying name of the Group and names of its members.
	 * @param group_name name of the Group
	 * @param passengerNames array containing names of Group members
	 */
	public Group(String group_name, String[] passengerNames) 
	{
		name = group_name;
				
		for(String name: passengerNames)
		{
			Passenger p = new Passenger(name, group_name);
			group.add(p);
		}
		
	}
	
	/**
	 * Returns an ArrayList containing Passengers in this Group.
	 * @return ArrayList of Passengers in this Group.
	 */
	public ArrayList<Passenger> getGroupArray()
	{
		return group;
	}
	
	/**
	 * Returns a string containing (space separated) seat numbers of all group members 
	 * in the order they were seated.
	 * @return string containing seat numbers of all the group members
	 */
	public String getSeatNumber() 
	{
		ArrayList<Passenger> thisGroup = getGroupArray();
		String result = "";
		for(Passenger p: thisGroup) 
		{
			result+=(p.getSeatNumber() + " ");
		}
		return result;
	}	

}
