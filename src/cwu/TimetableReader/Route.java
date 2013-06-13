package cwu.TimetableReader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Route {
	private String route;
	private String routeId;
	private Set<Trip> trips;
	
	public Route (String routeNum, String routeId) {
		this.route = routeNum;
		this.routeId = routeId;
		trips = new HashSet<Trip>();
	}
	
	public void addTrip () throws SQLException{
//		Trip newTrip = new Trip(destination);
//		newTrip.addStopSequence(stops);
//		trips.add(newTrip);
		
		/*
  	   * obtain all trips associated with this route from the database.
  	   * this is stored in the 'trips' table
  	   *
  	   */
		Statement statement = TimetableReader.connection.createStatement();
		ResultSet tripTableQueryResult = statement.executeQuery("SELECT trip_id, trip_headsign from trips WHERE route_id=\"" + routeId + "\"");
		System.out.println("Query: SELECT trip_id, trip_headsign from trips WHERE route_id=\"" + routeId + "\"");
	
		HashMap<String, String> tripHeadsigns = new HashMap<String, String>();
	  
		tripTableQueryResult.next(); // skip the column titles, located in the first row
		System.out.println("Getting all trips for route " + routeId + " ....");
		while (tripTableQueryResult.next()){
			tripHeadsigns.put(tripTableQueryResult.getString("trip_id"), tripTableQueryResult.getString("trip_headsign"));
		}
	  	System.out.println("done!");
	  	/*
	  	 * Now get all stops for each trip
	  	 */
	  
	  	ResultSet stopTimesQueryResult = statement.executeQuery("SELECT trip_id, stop_id FROM stop_times");
	  	//multimap, with key as trip and value as string
		HashMap<String, List<Integer>> stopsMap = new HashMap<String, List<Integer>>();
		  
		stopTimesQueryResult.next(); // skip the column titles, located in the first row
		  
		System.out.println("Adding stops into hash...");
		while (stopTimesQueryResult.next()){
			String tripId = stopTimesQueryResult.getString(1);
			int stopId = stopTimesQueryResult.getInt(2);
			List<Integer> list = stopsMap.get(tripId);
			if (list == null){
				list = new ArrayList<Integer>();
				stopsMap.put(tripId, list);
			}
			list.add(stopId);
		} 
		System.out.println("Done!");
	  
		  
		for (Map.Entry<String, String> entry : tripHeadsigns.entrySet()){
		    String tripId = entry.getKey();
			String tripHeadsign = entry.getValue();
			List<Integer> stopsForThisTrip = stopsMap.get(tripId);
			//route.addTrip(tripHeadsign, stopsForThisTrip);
			Trip newTrip = new Trip(tripId, tripHeadsign);
			newTrip.setStops(stopsForThisTrip);
			trips.add(newTrip);
		}
	}
	
	public String getRouteId(){
		return routeId;
	}
	
	public void printTrips(){
		System.out.println("Route: " + route + " Has " + trips.size() + " number of trips");
		
		for (Trip t : trips){
			t.printTrip();
		}
		
	}
	

}
