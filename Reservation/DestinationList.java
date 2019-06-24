package Reservation;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


@SuppressWarnings("rawtypes")
public class DestinationList {
	private static ArrayList<airports> airportList = new ArrayList<airports>();
	
	public static void dest() {
		//Change this string to read the file from the proper location
		String File = "C:\\Users\\Zarco\\eclipse-workspace\\AirplaneFlightScheduler\\src\\Reservation\\newdoc.json";
        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader(File)) {
        	ArrayList<airports> airportTempList = new ArrayList<airports>();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONArray destinations = (JSONArray) jsonObject.get("airports");
            
            Iterator it = destinations.iterator();
            
            JSONObject jo = new JSONObject();
            
            System.out.println();
            while(it.hasNext()) {
            	airports airport = new airports();
            	jo = (JSONObject) it.next();
            	String iata = (String) jo.get("iata");
                airport.setIata(iata);

                String name = (String) jo.get("name");
                airport.setName(name);
                
                String lat = (String) jo.get("lat");
                double la = Double.parseDouble(lat);
                airport.setLatitude(la);
                String lon = (String) jo.get("lon");
                
                double lo = Double.parseDouble(lon);
                airport.setLongitude(lo);
                airportTempList.add(airport);

            }
            sortAirports(airportTempList);
            setAirportList(airportTempList);
            String[] d = new String[airportTempList.size()];
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

	public static void sortAirports(ArrayList<airports> alist) {
		Collections.sort(alist);
	}

	public static ArrayList<airports> getAirportList() {
		return airportList;
	}

	public static void setAirportList(ArrayList<airports> airportList) {
		DestinationList.airportList = airportList;
	}
	//getters for airport object array individual values
	public static String getAirportListName(ArrayList<airports> airportList, int i) {
		return airportList.get(i).getName();
	}
	
	public static String getAirportListIata(ArrayList<airports> airportList, int i) {
		return airportList.get(i).getIata();
	}
	public static double getAirportListlongitude(ArrayList<airports> airportList, int i) {
		return airportList.get(i).getLongitude();
	}
	
	public static double getAirportListlatitude(ArrayList<airports> airportList, int i) {
		return airportList.get(i).getLatitude();
	}
	
	//String Array for JCombo drop down menu
	public static List<String> destList(ArrayList<airports> airportList) {
		List<String> iata = new ArrayList<String>();
		for(int i = 0; i < airportList.size(); i++)
		{
			iata.add(airportList.get(i).getIata());
		}
		return iata;
	}
	//Calculate the distance between two points given destination codes
	//TODO create a binary search for this function
	public static double distance(String coor2, ArrayList<airports>port)
	{
		//Lat give is for lax
		//https://www.flightdeckfriend.com/how-fast-do-commercial-aeroplanes-fly
		String timeStr = "";
		//400-500knots around 740-930kph average = 835kph
		double rate = 835;
		double time = 0;
		double long1 = -118.40828;
		double long2 = 0;
		double lat1 = 33.943398;
		double lat2 = 0;
		double earthrad = 6371;
		for(int i = 0; i < port.size(); i++ )
		{
			int found = 0;
			//This is to search for 2 airports will require two coor strings
			/*
			if(port.get(i).getIata().equals(coor1))
			{
				long1 = port.get(i).getLongitude();
				lat1 = port.get(i).getLatitude();
				found = found + 1;
			}
			else */
			if(port.get(i).getIata().equals(coor2))
			{
				long2 = port.get(i).getLongitude();
				lat2 = port.get(i).getLatitude();
				found = found + 1;
			}
			else if(found == 1)
			{
				break;
			}
		}
		double dlat = Math.toRadians(lat2-lat1);
		double dlong = Math.toRadians(long2-long1);
		double a = Math.sin(dlat/2) * Math.sin(dlat/2)
				+ Math.cos(Math.toRadians(lat1)) *Math.cos(Math.toRadians(lat2))
				* Math.sin(dlong/2)*Math.sin(dlong/2);
		double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double distance = earthrad * c * 1000; //meters
		distance = Math.pow(distance, 2);
		distance = Math.sqrt(distance);
		distance = distance/1000; //convert meters to km
		//r = d/t
		//tr = d
		//t = d/r


		time = distance/rate;
	
		//timeStr = Double.toString(time);
		return time;
	}
	
	public static String[] getAGoTime()
	{
		String[] times = new String[5];
		times[0] = "14:00";
		times[1] = "15:00";
		times[2] = "16:00";
		times[3] = "17:00";
		times[4] = "18:00";

		return times;
	}
	
	public static String[] getAByeTime(String[] times, double time)
	{
		int hour = (int) Math.round(time);
		double min = time - hour;  //fraction only
		min = min*60; // converts it to 'minutes'
		String[] byeTime = new String[5];
		int tempHour = 0;
		int tempMin = (int) min;
		String m = Integer.toString(tempMin);
		if (tempMin < 10)
		{
			m = "0" + m;
		}
		
		String h = "";
		for(int i = 0; i < 5; i++)
		{
			tempHour = i+14 + hour;
			while(tempHour > 23)
			{
				tempHour = tempHour - 24;
			}
			
			h = Integer.toString(tempHour);
			if(tempHour < 10)
			{
				h = "0"+h;
			}
			byeTime[i] = h + ":" + m;	
		}
		return byeTime;
	}
	//For Testing purposes
	public static void display(String[] li) {
		for(int i = 0; i < airportList.size(); i++)
		{
			System.out.println(li[i]);
		}
	}
	
	
	


}