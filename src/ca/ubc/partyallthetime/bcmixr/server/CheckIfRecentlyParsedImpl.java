
package ca.ubc.partyallthetime.bcmixr.server;

import ca.ubc.partyallthetime.bcmixr.client.CheckIfRecentlyParsed;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;


import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;


/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class CheckIfRecentlyParsedImpl extends RemoteServiceServlet implements
		CheckIfRecentlyParsed {

	static { 
     	ObjectifyService.register(TimeOfLastParse.class);
	}
	
	public String check() throws IllegalArgumentException, IOException {
		
		
		
	//	DAO dao = new DAO();

    	Objectify ofyTime = ObjectifyService.begin();
    	
    	long CACHE_EXPIRATION_TIME = 0; //863000000; // The cached values expire 12 hours (432000000ms) after parsing. 
		Date currentDate = new Date();
		long currentTime = currentDate.getTime();        
        
        
    	
    	try{
    		// checks if there is any existing record of when the DataBC data was last parsed.
    		@SuppressWarnings("unused")
			long testParse = ofyTime.get(TimeOfLastParse.class, "The time in milliseconds since 1970").getTimeOfLastParse(); 
    	} catch (NotFoundException e) {
    		// if there is no record, stores a dummy time as the most recent parse time.
    		// this time is long ago enough that it will trigger a fresh parse of the data. 
    		TimeOfLastParse parseTime = new TimeOfLastParse();
    		long timeWhenLastParsed = currentDate.getTime() - CACHE_EXPIRATION_TIME;
    		parseTime.setId("The time in milliseconds since 1970");
    		parseTime.setTimeOfLastParse(timeWhenLastParsed);
    		ofyTime.put(parseTime);
    		
    	}
    	
		long lastParse = ofyTime.get(TimeOfLastParse.class, "The time in milliseconds since 1970").getTimeOfLastParse(); 


    	// compares the current time to the most recent parse time. 

    		// return currentTimeString; // 1341711720798
    		//return timeOfLastParseString; // 1341698064769
    		
    	
		    String currentTimeString = String.valueOf(currentTime);
		    String timeOfLastParseString = String.valueOf(lastParse);
    		String CACHE_EXPIRATION_TIME_String = String.valueOf(CACHE_EXPIRATION_TIME);
    		
    		BigInteger bigCACHE_EXPIRATION_TIME = new BigInteger(CACHE_EXPIRATION_TIME_String);
    		BigInteger bigCurrentTime = new BigInteger (currentTimeString);
    		BigInteger bigTimeOfLastParse = new BigInteger (timeOfLastParseString);
    		
    		BigInteger bigDifference = bigCurrentTime.subtract(bigTimeOfLastParse);
    		
    		
    	//	return String.valueOf(bigDifference.compareTo(bigCACHE_EXPIRATION_TIME)); // = -1
    	//	return String.valueOf(bigCACHE_EXPIRATION_TIME); // 43200000
    	//	return String.valueOf(bigDifference); // 13367769
    		
    		if (bigDifference.compareTo(bigCACHE_EXPIRATION_TIME) == 1) // if the cache is expired...
    		{
        		TimeOfLastParse parseTime = ofyTime.get(TimeOfLastParse.class, "The time in milliseconds since 1970");
          		parseTime.setTimeOfLastParse(currentTime);
        		ofyTime.put(parseTime);
    			return "not recently parsed";
    		} else {
    			return "recently parsed";
    		}
    		
    	
	}



    		
}
	        
		
	




		
		



