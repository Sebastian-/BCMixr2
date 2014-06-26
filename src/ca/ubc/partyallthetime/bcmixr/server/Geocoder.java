package ca.ubc.partyallthetime.bcmixr.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Geocoder {

	private double lattitude;
	private double longitude;
	private AddressList address;
	public Geocoder(AddressList alist) {
		lattitude = 0;
		longitude = 0;
this.address = alist;
	}

	public AddressList Geocode() throws IOException, SAXException, ParserConfigurationException {
		
		String baseURL = "http://www.bcliquorstores.com/store/locator/location_data_js/";
		
		// connects to the BC Liquor website and captures the query response as a string.
		 URL bc_liquor;
		 String stores = "stores";
			bc_liquor = new URL(baseURL);

		    URLConnection connection = bc_liquor.openConnection();
		    BufferedReader input = new BufferedReader(
		                            new InputStreamReader(
		                                connection.getInputStream()));
		    StringBuffer stringbuffer = new StringBuffer();
		    String inputLine;
		    while ((inputLine = input.readLine()) != null) 
		        stringbuffer.append(inputLine);
		    input.close();
		  stores = stringbuffer.toString();

		String tlatlng = "";
		for (int i = 0 ; i < this.address.size() ; i++){
			Address a = address.get(i);
			int indexOfAddress = stores.indexOf(a.getName());
			int latIndex = stores.indexOf("latitude", indexOfAddress);
			
			String lat = stores.substring(latIndex + 12, latIndex + 21);
			
			int lngIndex = stores.indexOf("longitude", indexOfAddress);
			
			String lng = stores.substring(lngIndex + 13, lngIndex + 23);
			tlatlng = lng + " % " + lat;
		System.out.println("lnglat: " + tlatlng);
	    if(tlatlng.length()>20 & !tlatlng.equals("invalid address")){
		lattitude = Double.parseDouble(lat);
		longitude = Double.parseDouble(lng);
	    } else {
	    	lattitude = 0;
			longitude = 0;
	    }
	    	a.setTlatlng(tlatlng);
	    
		}
		
		
	    return address;
	  }

	public double getLattitude() {
		return lattitude;
	}

	public void setLattitude(float lattitude) {
		this.lattitude = lattitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	
	}


	