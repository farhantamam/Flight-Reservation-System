import java.io.*;
import java.util.*;

/**
 * Programming Assignment 1 Solution
 * @author Mohmad Tamam
 * Copyright 2016
 * @version 1.0
 *
 */

/**
 * Represents a Plane that can seat individual Passengers and Passengers in a Group.
 * It can also add and remove these Passengers and Groups.
 * It also has a method to help populate the Plane from prior program runs.
 *
 */
public class Plane {
	
	private Passenger[][] seats;
	
	/**
	 * Class constructor that holds the data structure for the Plane.
	 */
	public Plane()
	{
		//rows 0 and 1 are for first class and the rest (2-21) are for economy 
		seats = new Passenger[22][6];
		seats[0] = new Passenger[4];
		seats[1] = new Passenger[4];
	}
	
	/**
	 * Adds an individual Passenger with seat preference and class of service.
	 * @param p passenger to be added
	 * @param seatPref	seat preference requested for Passenger
	 * @param serviceClass	class of service for this Passenger 
	 * @return true	if Passenger is successfully assigned a seat
	 * or
	 * 			false	if Passenger was not assigned a seat
	 */
	public boolean addPassenger(Passenger p, String seatPref, String serviceClass)
	{
		if(serviceClass.equals("Economy"))
		{
			if(seatPref.equals("W"))
			{
				for (int i = 2; i < getSeats().length; i++) 
				{
					for (int j=0; j<getSeats()[2].length; j+=5)
					{
						if (getSeats()[i][j] == null)
						{
							getSeats()[i][j]= p;
							if (j == 5)
							{
								p.setSeatNumber(i+8+"F");							
								return true;
							}
							else if(j == 0)
							{
								p.setSeatNumber(i+8+"A");							
								return true;
							}
						}	
					}
				}					
			}
			else if(seatPref.equals("C"))
			{
				for (int i = 2; i < getSeats().length; i++) 
				{
					for (int j=1; j<getSeats()[2].length; j+=3)
					{
						if (getSeats()[i][j]== null)
						{
							getSeats()[i][j]= p;
							if (j == 4)
							{
								p.setSeatNumber(i+8+"E");							
								return true;
							}
							else if(j == 1)
							{
								p.setSeatNumber(i+8+"B");							
								return true;
							}
						}	
					}
				}					
			}
			else if(seatPref.equals("A"))
			{
				for (int i = 2; i < getSeats().length; i++) 
				{
					for (int j=2; j<4; j+=1)
					{
						if (getSeats()[i][j]== null)
						{
							getSeats()[i][j]= p;
							if (j == 3)
							{
								p.setSeatNumber(i+8+"D");							
								return true;
							}
							else if(j == 2)
							{
								p.setSeatNumber(i+8+"C");							
								return true;
							}
						}	
					}
				}					
			}
		}
		
		else if(serviceClass.equals("First"))
		{
			if(seatPref.equals("W"))
			{
				for (int i = 0; i < 2; i++) 
				{
					for (int j=0; j<3; j+=3)
					{
						if (getSeats()[i][j] == null)
						{
							getSeats()[i][j]= p;
							if (j == 3)
							{
								p.setSeatNumber(i+1+"D");							
								return true;
							}
							else if(j == 0)
							{
								p.setSeatNumber(i+1+"A");							
								return true;
							}
						}	
					}
				}					
			}
			else if(seatPref.equals("A"))
			{
				for (int i = 0; i < 2; i++) 
				{
					for (int j=1; j<3; j+=1)
					{
						if (getSeats()[i][j]== null)
						{
							getSeats()[i][j]= p;
							if (j == 2)
							{
								p.setSeatNumber(i+1+"C");							
								return true;
							}
							else if(j == 1)
							{
								p.setSeatNumber(i+1+"B");							
								return true;
							}
						}	
					}
				}					
			}
		}
			
		return false;
	}
	
	/**
	 * Cancels a Passengers reservation from the Plane. It returns 
	 * true if Passenger is successfully removed from the Plane or 
	 * false if Passenger was not removed from the Plane because it belonged to a Group.
	 * Individual Passengers from a Group cannot cancel their reservation.
	 * @param name name of the Passenger to be removed
	 * @return true	if Passenger is successfully removed from the Plane
	 * or
	 * 			false if Passenger was not removed from the Plane, because it belonged to a Group
	 */
	public boolean removePassenger(String name)
	{
		for(int i = 0; i< getSeats().length; i++)
		{
			for (int j =0; j< getSeats()[i].length; j++)
			{
				if(getSeats()[i][j] != null)
				{
					if(getSeats()[i][j].getName().equals(name))
					{
						if(!getSeats()[i][j].belongToGroup())
							getSeats()[i][j] = null;
						else
							return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * This method uses a helper method to find the first row of adjacent seats in a seat row 
	 * that is sufficient to accommodate the group, or if no such seat row exists, 
	 * finds the row with the largest number of adjacent seats in any seat row, 
	 * fills it up with members of the group, and repeats that process 
	 * (finding the row with the largest number of adjacent seats) until all members 
	 * of the group have been seated. If there is insufficient space to seat all members of the group, 
	 * none should be seated.
	 * @param group
	 * @param serviceClass
	 * @return true if Group was successfully added
	 * or 
	 * 			false if Group could not be seated
	 */
	public boolean addGroup(Group group, String serviceClass)
	{
		Boolean found = false;
		Integer[] availableSeats;
		int key = 0;
		
		int fromWhere = 0;//for the array
		int x = 1;//for the seatNumber
		if(serviceClass.equals("Economy"))
		{
			fromWhere = 2;
			x = 8;
		}

		while(!found)
		{
			availableSeats = findMaxAdjSeats(group.getGroupArray(), serviceClass);
			for(int i = 0; i < availableSeats.length; i+=2)
			{
				int j = i+1;
				if(group.getGroupArray().get(key).getSeatNumber() != (null))
					if(key < group.getGroupArray().size()-1)
						key++;
				if(group.getGroupArray().get(key).getSeatNumber() == null)
				{
					getSeats()[availableSeats[i]][availableSeats[j]]= group.getGroupArray().get(key);
					
					if (availableSeats[j]==0)
					{group.getGroupArray().get(key).setSeatNumber((availableSeats[i]+x)+"A");}
		            else if (availableSeats[j]==1)
		            {group.getGroupArray().get(key).setSeatNumber((availableSeats[i]+x)+"B");}	
		            else if (availableSeats[j]==2)
		            {group.getGroupArray().get(key).setSeatNumber((availableSeats[i]+x)+"C");}	
		            else if (availableSeats[j]==3)
		            {group.getGroupArray().get(key).setSeatNumber((availableSeats[i]+x)+"D");}	
		            else if (availableSeats[j]==4)
		            {group.getGroupArray().get(key).setSeatNumber((availableSeats[i]+x)+"E");}	
		            else if (availableSeats[j]==5)
		            {group.getGroupArray().get(key).setSeatNumber((availableSeats[i]+x)+"F");}	
		       }		
				else if(!group.getGroupArray().get(key).getSeatNumber().equals(null))
					return true;				
			}		
		}
		return found;
	}
	
	/**
	 * Cancels a Group reservation removing all the members of the Group from the plane.
	 * @param group_name name of the group to be removed
	 * @return true if Group was successfully removed
	 * or 
	 * 			false if Group could not be removed 
	 */
	public boolean removeGroup(String group_name)
	{
		for(int i = 0; i< getSeats().length; i++)
		{
			for (int j =0; j< getSeats()[i].length; j++)
			{
				if(getSeats()[i][j] != null)
				{
					if(getSeats()[i][j].belongToGroup())
						if(getSeats()[i][j].getGroupName().equals(group_name))
								getSeats()[i][j] = null;
				}
			}
		}
		return true;
	}
	
	/**
	 * Returns a 2-dimensional array containing information Passengers seated on the Plane.
	 * @return a 2-dimensional array containing information Passengers seated on the Plane.
	 */
	public Passenger[][] getSeats()
	{
		return seats;
	}
	
	/**
	 * This is a helper method that finds the first row of adjacent seats in a seat row 
	 * that is sufficient to accommodate the group, or if no such seat row exists, 
	 * finds the row with the largest number of adjacent seats in any seat row.
	 * Return an array containing the maximum amount of adjacent seats.
	 * @param group group to be added to the Plane
	 * @param serviceClass class in which this group is to be added
	 * @return an array containing the maximum amount of adjacent seats
	 */
	private Integer[] findMaxAdjSeats(ArrayList<Passenger> group, String serviceClass)
	{
		ArrayList<Integer> a  = new ArrayList<Integer>();
		ArrayList<Integer> b = new ArrayList<Integer>();
		Integer[] result = new Integer[1];
		
		int service = 0;//determines where to start looking in the array based on the service class
		int stop = 2;//determines when to stop looking
		if(serviceClass.equals("Economy"))
		{
			service = 2;
			stop = getSeats().length;
		}
			
		for(int i = service; i< stop; i++)
		{
			for (int j =0; j< getSeats()[service].length; j++)
			{
				if (getSeats()[i][j]== null)
				{
					b.add(i);
					b.add(j);					
				}
				//if the space is occupied or if it is the last seat
				if (getSeats()[i][j]!= null || j==5)
				{
					if (a.size()<b.size())
						a=b;
					b = new ArrayList<Integer>();
					if ((a.size()/2) >= group.size())
					{
						return a.toArray(result);
					}
				}
			}
		}
				
		if (a.size()<b.size())
		{
			a=b;
			b=new ArrayList<Integer>();
		}			
		return a.toArray(result);
	}
	
	/**
	 * Helps populate the plane from a prior program run.
	 * @param split an array containg information about the Passenger to be added
	 */

	public void populatePlane(String[] split) 
	{
		if(!split[0].equals("\n"))
		{
			int j = 0;
			String[] x = split[0].split("(?<=\\d)(?=\\D)");
			int i = Integer.parseInt(x[0]);
			if(i<3)//first class
				i--;
			else if(i>9)//economy class
				i-=8;
			if(x[1].equals("A")){ j = 0;}
			else if(x[1].equals("B")){ j = 1;}
			else if(x[1].equals("C")){ j = 2;}
			else if(x[1].equals("D")){ j = 3;}
			else if(x[1].equals("E")){ j = 4;}
			else if(x[1].equals("F")){ j = 5;}
			
			Passenger p = null;
			if(split[1].equals("I"))
			{
				p = new Passenger(split[2]);
				p.setSeatNumber(split[0]);				
			}
			else if(split[1].equals("G"))
			{
				p = new Passenger(split[3], split[2]);
				p.setSeatNumber(split[0]);
			}
			getSeats()[i][j] = p;
		}		
	}
	
	/**
	 * Return passenger info requested.
	 * @param ip passenger whose information is requested
	 * @return string containing Passengers info
	 */
	public String getPassengerInfo(Passenger ip) 
	{
		String info = "";
		info+=ip.getSeatNumber();
		if(ip.belongToGroup())
			info+= ", G, " + ip.getGroupName() + ", ";
		else
			info+=", I, ";
		info+=ip.getName() + "\n";
		return info;
	}
}
