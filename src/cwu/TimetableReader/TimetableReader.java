package cwu.TimetableReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class TimetableReader {
	public static final String SCHOOL_BUS_CODE = "712";
    public static Connection connection = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws ClassNotFoundException {
		//have a set of routes. exclude school routes - they have route_type = 712
		//this is done already in the db.
		
		List<Route> s = new ArrayList<Route>();
		
		// load the sqlite-JDBC driver using the current class loader
	    Class.forName("org.sqlite.JDBC");
	    
	    try
	    {
	      // create a database connection
	      connection = DriverManager.getConnection(
	    		  "jdbc:sqlite:/home/chengzhe/Dropbox/2013/Android/AlightBus/alightbus/sql/gtfs/full.db");
	      Statement statement = connection.createStatement();
	      statement.setQueryTimeout(30);  // set timeout to 30 sec.
	      ResultSet sqlBusRoutes = statement.executeQuery("" +
	      		"select route_id, route_short_name from routes where route_type != " + SCHOOL_BUS_CODE);
	      
	      ArrayList<String> routeIds = new ArrayList<String>();
	      ArrayList<String> routeShortName = new ArrayList<String>();
	      
	      sqlBusRoutes.next(); // skip column title, as that is the first row of sqlBusRoutes.
	      
	      while (sqlBusRoutes.next()){
	    	  routeIds.add(sqlBusRoutes.getString("route_id"));
	    	  routeShortName.add(sqlBusRoutes.getString("route_short_name"));
	      }
	      
	      assert (routeShortName.size() == routeIds.size());
	      for (int i = 0; i < routeShortName.size(); i++){
	    	  //System.out.println("Adding routes..." + i);
	    	  
	    	  String routeId = routeIds.get(i);
	    	  Route route = new Route(routeShortName.get(i), routeId);
	    	  route.addTrip();
	    	  route.printTrips();
	    	  s.add(route);
	    	  
	      }
	      
	      System.out.println("DONE!");
	      
	    }
	    catch (SQLException e)
	    {
	        System.err.println(e.getMessage());
	    }
	    finally
	    {
	      try
	      {
	        if(connection != null)
	          connection.close();
	      }
	      catch(SQLException e)
	      {
	        // connection close failed.
	        System.err.println(e);
	      }
	    }
	}

}
