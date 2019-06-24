

package StartUp_Manager;
import java.sql.*;
import java.util.Vector;


/** 
 *  This class centralizes mySQL database connections and queries needed to generate reports, 
 *  cancel or change reservations.
 * @author Pia Wetzel
 * @version 1.1
 * @since 06-20-2019
 *  
 */
public class SQLConnectionManager {
	
	//Your user information for DB connection
	String url = "jdbc:mysql://localhost:3306/booked_flights?verifyServerCertificate=false&useSSL=true";
	String user = "root";
	String password = "student";
	
	private static Vector<Customer> customerList;
	private static Vector<Customer> customerResult;
	
	
	 /** 
     * Method connects to database and retrieves 
     * all current FlysRUs passengers and reservations.
     * For each passenger, a customer object is created
     * and stored in a Vector of type customer
     * 
     * @return customerList  A vector of type Customer containing all FlysRUs passengers
     */
	
	public Vector<Customer> connectToDatabase(){
		
	try {
			customerList = new Vector<Customer>();
			
			System.out.println("CustomerList: " + customerList.size());
			
			Connection connection = DriverManager.getConnection(url, user, password);
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("select Reserved.passenger_name, Reserved.destination, Reserved.departureTime, Reserved.arrivalTime,"
					+ "Reserved.confirmation, Reserved.luggage, ConfirmedSeats.seats "
					+ "FROM Reserved "
					+ "INNER JOIN ConfirmedSeats ON Reserved.confirmation = ConfirmedSeats.ConfirmationNum"
					+ " ORDER BY departureTime");
			
			while(result.next()) {
				
				//Data that is retrieved from DB
				String name = result.getString("passenger_name");
				String destination = result.getString("destination");
				String departureTime = result.getString("departureTime");
				String arrivalTime = result.getString("arrivalTime");
				String confirmationNumber = result.getString("confirmation");
				String luggage = result.getString("luggage");
				String seat = result.getString("seats");
				
				//Create new customer for each reservation in DB
				Customer customer = new Customer(name, confirmationNumber, seat, departureTime, arrivalTime, destination, luggage);
				customerList.add(customer);

			}
			
			connection.close();
			
			System.out.println("CustomerList: " + customerList.size());
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
	
	return customerList;
	}
	
	
	
	 /** 
     * Method connects to database and retrieves 
     * all current FlysRUs passengers and reservations with a specific Confirmation Number.
     * 
     * For each passenger, a Customer object is created
     * and stored in a Vector of type Customer
     * 
     * @param someConfirmationNumber A String containing a confirmation Number 
     * @return customerResult  A vector of type Customer containing all FlysRUs passengers with this confirmation number
     */
	public Vector<Customer> searchExistingReservation(String someConfirmationNumber){
		
		try {
			
			customerResult = new Vector<Customer>();
			Connection connection = DriverManager.getConnection(url, user, password);
			PreparedStatement stmt = connection.prepareStatement("select Reserved.passenger_name, Reserved.destination, Reserved.departureTime, Reserved.arrivalTime,"
					+ "Reserved.confirmation, Reserved.luggage, ConfirmedSeats.seats "
					+ "FROM Reserved "
					+ "INNER JOIN ConfirmedSeats ON Reserved.confirmation = ConfirmedSeats.ConfirmationNum "
					+ "WHERE Reserved.confirmation = ? "
					+ " ORDER BY departureTime");
			stmt.setString(1, someConfirmationNumber);
			ResultSet result = stmt.executeQuery();
			
			while(result.next()) {
				
				//Data that is retrieved from DB
				String name = result.getString("passenger_name");
				String destination = result.getString("destination");
				String departureTime = result.getString("departureTime");
				String arrivalTime = result.getString("arrivalTime");
				String confirmationNumber = result.getString("confirmation");
				String luggage = result.getString("luggage");
				String seat = result.getString("seats");
				
				//Create new customer for each reservation in DB
				Customer customer = new Customer(name, confirmationNumber, seat, departureTime, arrivalTime, destination, luggage);
				
				System.out.println(customerResult.contains(customer));
				
				if(!customerResult.contains(customer))
				{
					customerResult.add(customer);
				}
				
				
			System.out.println(confirmationNumber + " Name: " + name);

			}
			
			connection.close();
			
	
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
		return customerResult;
	}
	
	
	 /** 
     * Method connects to database and deletes 
     * all current FlysRUs passengers and reservations with a specific Confirmation Number.
     */
	
	public void deleteReservation(String someConfirmationNumber){
		
		try {
			Connection connection = DriverManager.getConnection(url, user, password);
			PreparedStatement stmt = connection.prepareStatement("DELETE FROM Reserved WHERE reserved.confirmation = ?");
			stmt.setString(1, someConfirmationNumber);
			stmt.executeUpdate();
			
			PreparedStatement stmt2 = connection.prepareStatement("DELETE FROM ConfirmedSeats WHERE ConfirmationNum = ?");
			stmt2.setString(1, someConfirmationNumber);
			stmt2.executeUpdate();
			
			
			connection.close();
			
	
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
		
	}

}
