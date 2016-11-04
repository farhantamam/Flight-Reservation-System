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
 * Represents the Airline Reservation System that takes user input to add and remove 
 * individual passengers with a seat preference and Groups without a seat preference.
 * It can also generate seat Availability list of the plane and Manifest list of Passengers already seated.
 * It reads input from a file containing reservations from earlier program run 
 * and updates the file with the latest reservations when the program is quit.
 * 
 */
public class ReservationSystem {

	/**
	 * Runs the main method of the program taking input from the user and populating the plane with 
	 * earlier reservations if they exist.
	 * @param args
	 */
	public static void main(String[] args) {
		Plane plane = new Plane();
		Passenger passenger;
		Group group;
		String firstLine = "First 1-2, Left: A-B, Right: C-D; Economy 10-29, Left: A-C, Right: D-F\n";
		Scanner scan = new Scanner(System.in);
		
		File file = null;		
		Scanner fileScanner;
		try {
			file = new File(args[0]);;
			fileScanner = new Scanner(file);
			if(fileScanner.hasNextLine())//throws out the first line containing info about the plane size
			{	firstLine = fileScanner.nextLine() + "\n";
			}
			int i = 1;
			while(fileScanner.hasNextLine())
				plane.populatePlane(fileScanner.nextLine().split("\\s*,\\s*"));
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			try {
			    // creates the empty file
			    file.createNewFile();
			}  catch (Exception x) {
			    // Some other sort of failure, such as permissions.
				x.printStackTrace();
				System.err.format("createFile error: %s%n", x);
			}
		}

		String typed = "";
		
		while(scan.hasNext())
		{
			println( "Add [P]assenger, Add [G]roup, [C]ancel Reservations, "
					+ "Print Seating [A]vailability Chart, Print [M]anifest, [Q]uit" );
			
			typed = scan.nextLine();
			
			if(typed.equals("P"))
			{
				print("Name: ");
				String name = scan.nextLine();
				print("Service Class: ");
				String serviceClass = scan.nextLine();
				print("Seat preference: ");
				String seatPref = scan.nextLine();
				
				passenger = new Passenger(name);
				Boolean addSuccess = plane.addPassenger(passenger, seatPref, serviceClass);
				
				if(!addSuccess)//if passenger was not seated, this method takes care of unavailable seat due to unavailable seat preference
					addPassengerSuccess(plane, passenger, addSuccess, seatPref, serviceClass, scan);
				
				println(passenger.getSeatNumber());
			}
			
			else if(typed.equals("G"))
			{
				print("Group name: ");
				String groupName = scan.nextLine();
				print("Names: ");
				String names = scan.nextLine();
				print("Service Class: ");
				String serviceClass = scan.nextLine();
				
				group = new Group(groupName, names.split("\\s*,\\s*"));
				Boolean addGroupSuccess = plane.addGroup(group, serviceClass);
				if(addGroupSuccess)
					println(group.getSeatNumber());
				else
				{
					println("Request failed: Insufficient Space!");					
				}
			}
			
			else if(typed.equals("C"))
			{
				println("Cancel [I]ndividual or [G]roup?");
				String response = scan.nextLine();
				
				if(response.equals("I"))
				{
					print("Names: ");
					String[] names = scan.nextLine().split("\\s*,\\s*");
					
					for(String n: names)
					{
						if(!plane.removePassenger(n))
						{
							println("Error: Individual Group member ("+ n 
									+") cannot cancel his/her reservation only!");
						}
					}
				}		
				else
				{
					print("Group name: ");
					String groupName = scan.nextLine();
					
					if(!plane.removeGroup(groupName))
					{
						println("Error: Group with this name Does Not Exist!");
					}
				}
			}
			
			else if(typed.equals("A"))
			{
				print("Service Class: ");
				String serviceClass = scan.nextLine();
				
				String column = "";
				String emptySeats = "";
				
				if(serviceClass.equals("Economy"))
				{
					println("Economy");
					for (int i = 2; i < plane.getSeats().length; i++) 
					{
						print(i+8 + ": ");
						for (int j=0; j<plane.getSeats()[i].length;j++)
						{
							if (plane.getSeats()[i][j] == null)				
							{
								if(j==0)
									emptySeats += "A,";
								else if(j==1)
									emptySeats += "B,";
								else if(j==2)
									emptySeats += "C,";
								else if(j==3)
									emptySeats += "D,";
								else if(j==4)
									emptySeats += "E,";
								else if(j==5)
									emptySeats += "F,";
							}								
						}
						if(emptySeats.length() != 0)
							print(emptySeats.substring(0, emptySeats.lastIndexOf(',')));
						print("\n");
						emptySeats = "";
					}					
				}
				else if(serviceClass.equals("First"))
				{
					println("First");
					for (int i = 0; i < 2; i++) 
					{
						print(i+1 + ": ");
						for (int j=0; j<plane.getSeats()[i].length;j++)
						{
							if (plane.getSeats()[i][j] == null)				
							{
								if(j==0)
									emptySeats += "A,";
								else if(j==1)
									emptySeats += "B,";
								else if(j==2)
									emptySeats += "C,";
								else if(j==3)
									emptySeats += "D,";
							}								
						}
						println(emptySeats.substring(0, emptySeats.lastIndexOf(',')));
						emptySeats = "";
					}					
				}
			}
			
			else if(typed.equals("M"))
			{
				print("Service Class: ");
				String serviceClass = scan.nextLine();
				
				if(serviceClass.equals("Economy"))
				{
					println("Economy\n");
					for (int i = 2; i < plane.getSeats().length; i++) 
					{
						for (int j=0; j<plane.getSeats()[i].length;j++)
						{
							if (plane.getSeats()[i][j]!= null)				
								println(plane.getSeats()[i][j].getSeatNumber() +": " 
										+ plane.getSeats()[i][j].getName());
						}
					}
					
				}
				else if(serviceClass.equals("First"))
				{
					println("First\n");
					for (int i = 0; i < 2; i++) 
					{
						for (int j=0; j<plane.getSeats()[i].length;j++)
						{
							if (plane.getSeats()[i][j]!= null)				
								println(plane.getSeats()[i][j].getSeatNumber() +": " 
										+ plane.getSeats()[i][j].getName());
						}
					}
				}
				
				//prints the entire array
				//for debugging 
				/*int y = 1;
				System.out.println("\tA\tB\tC\tD\tE\tF\n");
				for (Passenger[] a : plane.getSeats()) 
				{
					if(y==3) { y=10; System.out.println();}
					System.out.print(y++ + " |\t");
					for (Passenger ip : a) 
					{
		                if(ip != null)
		                	System.out.print(ip.getName() + "\t"); 
		                else
		                	System.out.print("empty" + "\t");
					}
		            System.out.println("\n");
				}*/
			}
			
			else if(typed.equals("Q"))
			{
				scan.close();
				FileWriter fw;
				BufferedWriter bw;
				try {

					fw = new FileWriter(file.getAbsoluteFile());
					fw.close();//deletes the content of the file
					fw = new FileWriter(file.getAbsoluteFile());//start from an empty file
					bw = new BufferedWriter(fw);
					
					bw.write(firstLine);//writes the line about flight size info
					for (Passenger[] a : plane.getSeats()) 
					{
						for (Passenger ip : a) 
						{
							if(ip != null)
								bw.write(plane.getPassengerInfo(ip));	
						}}
					bw.close();
					fw.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}			
			}					
		}	
	}
	
	//this method takes care of unavailable seat due to unavailable seat preference
	/**
	 * This method is called when the Passenger is not seated due to unavailability of seat matching
	 * their seat preference. User is prompted to enter a new Seat Preference until the Passenger is 
	 * seated on the Plane. It is a recursive method, could go on infinitely if bad data is entered.
	 * @param plane plane which holds the data structure to store the Passenger reservations
	 * @param p passenger to be added to the Plane
	 * @param addSuccess weather or not passenger was seated on the plane 
	 * @param seatPref seat preference of the Passenger
	 * @param serviceClass class of service that Passenger requested, either First or Economy
	 * @param scan scanner that takes the user input
	 * postcondition: passenger is seated on the plane
	 */
	private static void addPassengerSuccess(Plane plane, Passenger p, 
			Boolean addSuccess, String seatPref, String serviceClass, Scanner scan) 
	{
		if(addSuccess)
			println(p.getSeatNumber());
		else
		{
			if(seatPref.equals("W")) seatPref = "Window";
			else if(seatPref.equals("C")) seatPref = "Center";
			else if(seatPref.equals("A")) seatPref = "Aisle";
			println("Request failed: no " + seatPref + " seat is available!");
			
			print("Please enter another Seat Preference: ");
			seatPref = scan.nextLine();
			
			addSuccess = plane.addPassenger(p, seatPref, serviceClass);
			addPassengerSuccess(plane, p, addSuccess, seatPref, serviceClass, scan);

		}
	}

	/**
	 * A helper method to print to console on a new line.
	 * @param o object to be printed
	 */
	private static void println(Object o)
	{
		System.out.println(o);
	}
	/**
	 * A helper method to print to console on the same line.
	 * @param o object to be printed
	 */
	private static void print(Object o)
	{
		System.out.print(o);
	}
	
}
