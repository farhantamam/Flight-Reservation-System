/**
 * Programming Assignment 1 Solution
 * @author Mohmad Tamam
 * Copyright 2016
 * @version 1.0
 *
 */
/**
 * Represents the Passenger class that has a name, seat number, 
 * weather or not it belongs to a Group 
 * and a group name if it does belong to a Group.
 * 
 */
public class Passenger {
	
	private String name;
	private Boolean belongToGroup;
	private String group_name;
	private String seatNumber;
	
	/**
	 * Class constructor specifying the name of the Passenger; this passenger does not belong to a Group.
	 * @param name name of the Passenger
	 * postcondition: Passenger of this name is created.
	 * postcondition: This passenger does not belong to a Group.
	 * 
	 */
	public Passenger(String name)
	{
		this.name = name;
		belongToGroup = false;
		group_name = null;
		seatNumber = null;
	}
	
	/**
	Class constructor specifying the name of the Passenger and name of the Group it belongs to.
	@param name name of the Passenger
	@param group_name name of the Group this passenger belongs to
	postcondition: Passenger of this name is created.
	postcondition: This passenger belongs to a Group.
	*/
	public Passenger(String name, String group_name)
	{
		this.name = name;
		belongToGroup = true;	
		this.group_name = group_name;
		seatNumber = null;
	}
	
	/**
	Changes the seat number of the Passenger.
	@param seat new seat number
	postcondition: seat number is changed to the new seat number.
	*/
	public void setSeatNumber(String seat) 
	{
		seatNumber = seat;
	}
	
	/**
	Returns the current seat number of this Passenger.
	@return the current seat number
	*/
	public String getSeatNumber() 
	{
		return seatNumber;
	}
	
	/**
	Returns the name of this Passenger.
	@return name name of this Passenger
	*/
	public String getName() 
	{
		return name;
	}
	
	/**
	Returns true if this Passenger belongs to a group.
	@return true if this Passenger belongs to a group
	or
	 		false if this Passenger does not belong to a group
	*/
	public Boolean belongToGroup() 
	{
		return belongToGroup;
	}
	
	/**
	Returns the group name this Passenger belongs to.
	precondition: this Passenger belongs to a group
	@return group_name group name this Passenger belongs to
	*/
	public String getGroupName() 
	{
			return group_name;
	}
}
