package StartUp_Manager;
/** 
 *  This class allows for the creation of Customer objects
 *  
 * @author Pia Wetzel
 * @version 1.2
 * @since 06-22-2019
 *  
 */

public class Customer {

	private String name, confirmationNumber, time_departure, time_arrival, destination, seat, luggage;

	 /** 
     * This method creates an object of type Customer 
     * 
     * @param name 					The customer's name
     * @param confirmationNumber	The customer's confirmation number
     * @param time_departure		The customer's departure time
     * @param time_arrival			The customer's arrival time
     * @param destination			The customer's destination
     * @param seat					The customer's seat number
     * @param luggage				Does the customer travel with luggage?						T
     */	
	
	public Customer(String name,String confirmationNumber, String seat, 
			String time_departure, String time_arrival, String destination, String givenLuggage)
	{
		this.name = name;
		this.confirmationNumber = confirmationNumber;
		this.seat = seat;
		this.time_departure = time_departure;
		this.time_arrival = time_arrival;
		this.destination = destination;
		this.luggage = "Yes";
		
		if(givenLuggage.equals("0"))
		{
			luggage = "No";
		}
		
	}
	
	//Getters

	public String getName()
	{
		return name;
	}
	
	public String getConfirmationNumber()
	{
		return confirmationNumber;
	}

	
	public String getDepartureTime()
	{
		return time_departure;
	}
	
	public String getArrivalTime()
	{
		return time_arrival;
	}
	
	public String getDestination()
	{
		return destination;
	}
	
	public String getSeat()
	{
		return seat;
	}
	
	public String getLuggage() {
		return luggage;
	}
	

}
