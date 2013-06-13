package cwu.TimetableReader;

import java.util.ArrayList;
import java.util.List;

public class Trip {
	private String tripId;
	private String destination;
	private List<Integer> stops;
	
	//trips has list of .... stop sequences
	
	public Trip (String tripId, String destination){
		this.tripId = tripId;
		this.destination = destination;
		stops = new ArrayList<Integer>();
	}
	
	public void setStops (List<Integer> stops){
		this.stops = stops;
	}
	
	public List<Integer> getStops(){
		return this.stops;
	}
	
	public void printTrip(){
		System.out.println(tripId + " " + destination);
		for (Integer stop : stops){
			System.out.print(stop + " ");
		}
		System.out.println();
	}
	
	@Override
	public int hashCode(){
		return stops.size();
	}
	
	@Override
	public boolean equals(Object o){
		Trip route = (Trip)o;
		boolean retVal = route.getStops().equals(this.stops);
		return retVal;
	}
	
}
