
// ****************I'm still working on this. I've disabled some code so that it works in a basic way.
// ****************I'm still trying to make it cache the parsing data for 12 hours (that's the complicated part).


package ca.ubc.partyallthetime.bcmixr.server;

import ca.ubc.partyallthetime.bcmixr.client.DisplayNameService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import java.io.IOException;
import java.util.ArrayList;


/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DisplayNameServiceImpl extends RemoteServiceServlet implements
		DisplayNameService {
	
//	static { 
 //    	ObjectifyService.register(AlcoholDisplayNames.class);
//	}
	
	public ArrayList<String> getDisplayNames() throws IllegalArgumentException, IOException {


		//DAO dao = new DAO();

	
		
		System.out.println("in DisplayNameServiceImpl, before getting AlcoholDisplayNames");
		
        Objectify ofyNames = ObjectifyService.begin();
        AlcoholDisplayNames names = ofyNames.get(AlcoholDisplayNames.class, 1);
			
        ArrayList<String> displayNames = names.getDisplayNames();
		
		//Test code:
//		ArrayList<String> displayNames = new ArrayList<String>();
//		displayNames.add("Beer");
//		displayNames.add("MoreBeer");
//		displayNames.add("I loves me some RTC");
		System.out.println("in DisplayNameServiceImpl, before returning displayNames");

      return displayNames;
		
	}
	

	}

	        
		
	




		
		



