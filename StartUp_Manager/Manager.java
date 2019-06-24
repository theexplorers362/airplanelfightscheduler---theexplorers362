package StartUp_Manager;

/** 
 *  This class allows for the creation of Manager objects
 *  
 * @author Pia Wetzel
 * @version 1.2
 * @since 06-22-2019
 *  
 */
public class Manager {

	private String name, pin;
	
	
	 /** 
     * This method creates an object of type Manager 
     * 
     * @param name 					The Manager's name
     * @param confirmationNumber	The Manager's pin - needed to log into Manager area		
     * 
     * (The programmers are aware that the pin needs to be stored in a more secure way)				T
     */	
	
	Manager(String name, String pin)
	{
		this.name = name;
		this.pin = pin;
	}
	
	
	
	 /** 
     * This method verifies if a given entered pin matches with the Manager's actual pin 
     * 
     * @param enteredPin 			Pin entered by the manager
     * @return 						Whether the entered pin matches with the stored pin					T
     */	
	
	public boolean verifyPin(String enteredPin) 
	{
		return pin.equals(enteredPin);
	}
	
	//Getter
	
	public String getName()
	{
		return name;
	}

}
