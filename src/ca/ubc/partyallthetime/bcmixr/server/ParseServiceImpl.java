
// ****************I'm still working on this. I've disabled some code so that it works in a basic way.
// ****************I'm still trying to make it cache the parsing data for 12 hours (that's the complicated part).


package ca.ubc.partyallthetime.bcmixr.server;

import ca.ubc.partyallthetime.bcmixr.client.ParseService;
import ca.ubc.partyallthetime.bcmixr.shared.Alcohol;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;


/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ParseServiceImpl extends RemoteServiceServlet implements
		ParseService {
	
//	static {
//	ObjectifyService.register(TimeOfLastParse.class);
//	}
	
	
	AlcoholDisplayNames displayNames = new AlcoholDisplayNames();

	public String getParse() throws IllegalArgumentException, IOException {
		
		@SuppressWarnings("unused")
		DAO dao = new DAO();		
 
        Objectify ofy = ObjectifyService.begin();        

	        URL liquor_data = new URL("http://pub.data.gov.bc.ca/datasets/176284/BC_Liquor_Store_Product_Price_List.csv");
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(liquor_data.openStream()));
	        CSVReader reader = new CSVReader(in);
	        String [] nextLine; 
	        ArrayList<Alcohol> alArray = new ArrayList<Alcohol>();
	        
	        
	        // skip the first line of the CSV
	        reader.readNext();
	        
	        //TODO counter to limit data input
	        int counter = 0;
	        
	        // iterate through the CSV file and grab all necessary information to fill the Alcohol Object
	        while ((nextLine = reader.readNext()) != null && counter < 25) {
	        	// nextLine[] is an array of values from the line
	        	counter++;
	        	
	        	
	        	
	        	String className = nextLine[1];
	            String subClassName = nextLine[2];
	            String minorClassName = nextLine[3];
	       //     String origin = nextLine[4];
	            String skuNumber = nextLine[5];
	            String name = nextLine[6];
	            String litresPerContainer = nextLine[8];
	            String containersPerSellUnit = nextLine[9];
	         //   String alcoholPercent = nextLine[10];
	            String price = nextLine[11];
	            

	        	
	            Alcohol alTemp = new Alcohol();
	            
	            alTemp.setClassName(className);
	            alTemp.setSubclassName(subClassName);
	            alTemp.setMinorclassName(minorClassName);
	          //  alTemp.setOrigin(origin);
	            alTemp.setSkuNumber(skuNumber);
	            alTemp.setName(name);
	            alTemp.setLitresPerContainer(litresPerContainer);
	            alTemp.setContainersPerSellUnit(containersPerSellUnit);
	          //  alTemp.setAlcoholPercent(alcoholPercent);
	            alTemp.setPrice(price);
/*
	            alTemp.setupAlcohol(nextLine[1], nextLine[2], nextLine[3], nextLine[5], nextLine[6], nextLine[8],
	            		nextLine[9], nextLine[11]);
	            */
	           
	            
	            // displayNames.addDisplayName(alTemp.getDisplayName());
	            displayNames.addDisplayName(name +" -- " + containersPerSellUnit + "x " + litresPerContainer + "L" + " - (" + skuNumber + ")");
	            
	            
	            
	            // Generating Suggestion Box Oracle

	        	
	            
	           
	        
	            
	            
	       
	       
	          //  ofy.put(alTemp);
	                    	       
	            alArray.add(alTemp);
	            
	        }
	        
	        ofy.put(alArray);
	        
    		displayNames.setId(1);
    		
	        ofy.put(displayNames);
	        
	        
	        
            return "Data would've been successfully parsed.";

	}
}
	        
		
	




		
		



